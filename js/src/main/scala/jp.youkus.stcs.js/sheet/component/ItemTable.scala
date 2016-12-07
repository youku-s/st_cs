package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import jp.youkus.stcs.js.sheet.util
import jp.youkus.stcs.js.{model => M}
import scala.scalajs.js

object ItemTable {
  case class Prop(
    items: Map[Int, M.Item]
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, M.Sheet]) {
    def onChange(index: Int, f: (M.Item, String) => M.Item)(e: ReactEventI): Callback = {
      val value = e.target.value
      pScope.modState(s => 
        s.copy(
          items = s.items.get(index) match {
            case Some(p) => s.items + (index -> f(p, value))
            case None => s.items
          }
        )
      )
    }
    def deleteRow(index: Int): Callback = {
      pScope.modState(s =>
        s.copy(
          items = s.items.get(index) match {
            case Some(p) => s.items - index
            case None => s.items
          }
        )
      )
    }
    def addRow(): Callback = {
      pScope.modState(s =>
        s.copy(
          items = {
            val next = if (s.items.isEmpty) 0 else s.items.keys.max + 1
            s.items + (next -> M.Item("", None, None))
          }
        )
      )
    }
    def render(p: Prop): ReactElement = {
      <.div(
        ^.classSet("box" -> true),
        <.h2("アイテム"),
        <.div(
          <.table(
            ^.classSet("item" -> true),
            <.thead(
              <.tr(
                <.th(
                  ^.style := js.Dictionary("width" -> "60%"),
                  "名前"
                ),
                <.th(
                  ^.style := js.Dictionary("width" -> "10%"),
                  "主"
                ),
                <.th(
                  ^.style := js.Dictionary("width" -> "10%"),
                  "副"
                ),
                <.th(
                  ^.classSet("noborder" -> true),
                  ^.style := js.Dictionary("width" -> "20%")
                )
              )
            ),
            <.tbody(
              p.items.toList.sortBy(_._1).map{ case (index, item) =>
                <.tr(
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.value := item.name,
                      ^.onChange ==> onChange(index, (p, v) => p.copy(name = v))_
                    )
                  ),
                  <.td(
                    <.select(
                      ^.value := item.main.getOrElse(-1),
                      ^.onChange ==> onChange(index, (i, v) => i.copy(main = util.toIntOpt(v)))_,
                      <.option(^.value := "-1", "-"),
                      <.option(^.value := "0", "支"),
                      <.option(^.value := "1", "従"),
                      <.option(^.value := "2", "打"),
                      <.option(^.value := "3", "純")
                    )
                  ),
                  <.td(
                    <.select(
                      ^.value := item.sub.getOrElse(-1),
                      ^.onChange ==> onChange(index, (i, v) => i.copy(sub = util.toIntOpt(v)))_,
                      <.option(^.value := "-1", "-"),
                      <.option(^.value := "0", "支"),
                      <.option(^.value := "1", "従"),
                      <.option(^.value := "2", "打"),
                      <.option(^.value := "3", "純"),
                      <.option(^.value := "4", "押"),
                      <.option(^.value := "5", "察")
                    )
                  ),
                  <.td(
                    ^.classSet("noborder" -> true),
                    <.button(
                      ^.onClick --> deleteRow(index),
                      "削除"
                    )
                  )
                )
              }
            )
          ),
          <.button(
            ^.onClick --> addRow,
            "行追加"
          )
        )
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.Sheet]) = {
    ReactComponentB[ItemTable.Prop]("ItemTable")
      .stateless
      .backend(scope => new ItemTable.Backend(scope, pScope))
      .renderBackend
      .build
  }
}

