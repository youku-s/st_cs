package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object Footer {
  class Backend(scope: BackendScope[Unit, Unit]) {
    def render = {
      <.div(
        ^.classSet("footer" -> true),
        "(c) 2016 よーく"
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
