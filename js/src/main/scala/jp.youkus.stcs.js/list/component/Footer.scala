package jp.youkus.stcs.js.list.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object Footer {
  class Backend(scope: BackendScope[Unit, Unit]) {
    def render = {
      <.div(
        ^.classSet("footer" -> true)
      )
    }
  }
  def component() = {
    ReactComponentB[Unit]("Footer")
      .stateless
      .renderBackend[Footer.Backend]
      .buildU
  }
}
