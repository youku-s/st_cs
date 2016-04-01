package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object Header {
  class Backend(scope: BackendScope[Unit, Unit]) {
    def render = {
      <.h1("少女展爛会キャラクターシート")
    }
  }
  def component() = {
    ReactComponentB[Unit]("Header")
      .stateless
      .renderBackend[Header.Backend]
      .buildU
  }
}
