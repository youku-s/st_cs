package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI, ReactKeyboardEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object Contact {
  class Backend(scope: BackendScope[Unit, Unit]) {
    def render() = {
      <.div(
        ^.classSet("box" -> true),
        <.h2("タグ"),
        <.div(
          <.div(
            "入力してEnterを押すとタグが作成されます"
          )
        )
      )
    }
  }
  def component() = {
    ReactComponentB[Unit]("Contact")
      .stateless
      .renderBackend[Contact.Backend]
      .buildU
  }
}
