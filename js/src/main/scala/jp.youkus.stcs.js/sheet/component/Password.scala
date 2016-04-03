package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.EmptyTag
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.js.{model => M}

object Password {
  case class Prop(
    onSave: Callback,
    onDelete: Callback,
    onCreate: Callback,
    id: Option[String],
    display: Boolean
  )
  class Backend(scope: BackendScope[Prop, Boolean], pScope: BackendScope[Unit, M.Sheet]) {
    def onPasswordChange(e: ReactEventI): Callback = {
      pScope.modState(s => s.copy(password = Some(e.target.value)))
    }
    def onCheck(display: Boolean): Callback = {
      pScope.modState(s => s.copy(display = !display))
    }
    def onClick(use: Boolean): Callback = {
      scope.setState(use)
    }
    def render(p: Prop, s: Boolean) = {
      <.div(
        ^.classSet("box" -> true),
        <.div(
          ^.classSet("row" -> true),
          <.div(
            <.input(
              ^.`type` := "radio",
              ^.name := "usePassword",
              ^.onChange --> onClick(false),
              ^.checked := !s
            ),
            "パスワードを使用しない "
          ),
          <.div(
            <.input(
              ^.`type` := "radio",
              ^.name := "usePassword",
              ^.onChange --> onClick(true),
              ^.checked := s
            ),
            "パスワードを使用する"
          )
        ),
        if (s) {
          <.div(
            ^.classSet("row" -> true),
            "パスワード",
            <.input(
              ^.`type` := "password",
              ^.onChange ==> onPasswordChange
            )
          )
        } else {
          EmptyTag
        },
        <.div(
          ^.classSet("row" -> true),
          <.input(
            ^.`type` := "checkbox",
            ^.onChange --> onCheck(p.display),
            ^.checked := !p.display
          ),
          "リストに載せない"
        ),
        if (p.id.isDefined) {
          <.div(
            ^.classSet("row" -> true),
            <.button(
              "保存する",
              ^.onClick --> p.onSave
            ),
            <.button(
              "削除する",
              ^.onClick --> p.onDelete
            )
          )
        } else {
          <.div(
            ^.classSet("row" -> true),
            <.button(
              "保存する",
              ^.onClick --> p.onCreate
            )
          )
        }
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.Sheet]) = {
    ReactComponentB[Prop]("Password")
      .initialState(false)
      .backend(scope => new Password.Backend(scope, pScope))
      .renderBackend
      .build
  }
}
