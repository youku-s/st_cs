package jp.youkus.stcs.js.list.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object Footer {
  class Backend(scope: BackendScope[Unit, Unit]) {
    def render = {
      <.div(
        ^.classSet("footer" -> true),
        "(c) 2016 よーく",
        " ",
        <.a(
          ^.href := "https://twitter.com/intent/tweet?screen_name=youku_s",
          "Twitter(@youku_s)",
          ^.target := "_blank"
        )
      )
    }
  }
  def component() = {
    ReactComponentB[Unit]("Footer")
      .stateless
      .renderBackend[Footer.Backend]
      .build
  }
}
