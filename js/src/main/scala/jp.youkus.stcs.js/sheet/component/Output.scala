package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI, ReactKeyboardEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import scala.scalajs.js

object Output {
  case class Prop(id: Option[String])
  def onClick(id: String): Callback = Callback {
    js.Dynamic.global.window.open(s"/api/sheet/${id}/text")
  }
  class Backend(scope: BackendScope[Prop, Unit]) {
    def render(p: Prop) = {
      if (p.id.isDefined) {
        <.div(
          ^.classSet("box" -> true),
          <.h2("データ出力"),
          <.div(
            <.div(
              <.button(
                ^.onClick --> onClick(p.id.getOrElse("")),
                "テキスト出力"
              )
            )
          )
        )
      } else {
        <.div()
      }
    }
  }
  def component() = {
    ReactComponentB[Prop]("Output")
      .stateless
      .renderBackend[Output.Backend]
      .build
  }
}
