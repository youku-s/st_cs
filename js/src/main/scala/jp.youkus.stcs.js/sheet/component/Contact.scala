package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI, ReactKeyboardEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object Contact {
  class Backend(scope: BackendScope[Unit, Unit]) {
    def render() = {
      <.div(
        ^.classSet("box" -> true),
        <.h2("管理者へ連絡"),
        <.div(
          <.a(
            ^.href := "https://twitter.com/intent/tweet?screen_name=youku_s",
            "ツイッターで連絡する",
            ^.target := "_blank"
          )
        )
      )
    }
  }
  def component() = {
    ReactComponentB[Unit]("Contact")
      .stateless
      .renderBackend[Contact.Backend]
      .build
  }
}
