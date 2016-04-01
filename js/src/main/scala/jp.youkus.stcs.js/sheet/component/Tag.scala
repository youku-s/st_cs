package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI, ReactKeyboardEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.js.sheet.{model => M}

object Tag {
  case class Prop(
    tags: Map[Int, String]
  )
  class Backend(scope: BackendScope[Prop, String], pScope: BackendScope[Unit, M.App]) {
    def onClick(index: Int): Callback = {
      pScope.modState(s =>
        s.copy(
          tags = s.tags.get(index) match {
            case Some(p) => s.tags - index
            case None => s.tags
          }
        )
      )
    }
    def onChange(e: ReactEventI): Callback = {
      scope.setState(e.target.value)
    }
    def onKeyDown(tag: String)(e: ReactKeyboardEventI): Callback = {
      if (e.keyCode == 13) {
        pScope.modState{ s =>
          val next = if (s.tags.isEmpty) 0 else s.tags.keys.max + 1
          if (!s.tags.values.toSeq.contains(tag)) {
            s.copy(
              tags = s.tags + (next -> tag)
            )
          } else {
            s
          }
        }.runNow
        scope.setState("")
      } else {
        Callback.empty
      }
    }
    def render(p: Prop, s: String) = {
      <.div(
        ^.classSet("box" -> true),
        <.h2("タグ"),
        <.div(
          <.div(
            "入力してEnterを押すとタグが作成されます",
            <.input(
              ^.`type` := "text",
              ^.value := s,
              ^.onChange ==> onChange,
              ^.onKeyDown ==> onKeyDown(s)_
            )
          ),
          <.div(
            ^.classSet("tags" -> true),
            p.tags.toList.sortBy(_._1).map{ case (index, tag) =>
              <.span(
                ^.classSet("tag" -> true),
                tag + " ",
                <.span(
                  ^.classSet("ion-close-circled" -> true),
                  ^.onClick --> onClick(index)
                )
              )
            }
          )
        )
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.App]) = {
    ReactComponentB[Prop]("Tag")
      .initialState("")
      .backend(scope => new Tag.Backend(scope, pScope))
      .renderBackend
      .build
  }
}
