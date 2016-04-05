package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI, ReactKeyboardEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import jp.youkus.stcs.js.{model => M}
import scala.scalajs.js
import upickle.default.write

object Output {
  case class Prop(
    sheet: M.Sheet
  )
  def onClick(p: Prop): Callback = Callback {
    val data = write(M.Sheet.toJson(p.sheet))
    js.Dynamic.global.window.open(s"/api/sheet/text?q=${data}")
  }
  class Backend(scope: BackendScope[Prop, Unit]) {
    def render(p: Prop) = {
      <.div(
        ^.classSet("box" -> true),
        <.h2("データ出力"),
        <.div(
          <.div(
            <.button(
              ^.onClick --> onClick(p),
              "テキスト出力"
            )
          )
        )
      )
    }
  }
  def component() = {
    ReactComponentB[Prop]("Output")
      .stateless
      .renderBackend[Output.Backend]
      .build
  }
}
