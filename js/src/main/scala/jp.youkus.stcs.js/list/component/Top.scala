package jp.youkus.stcs.js.list.component

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.shared.json

object Top {
  class Backend(scope: BackendScope[Unit, Unit]) {
    val header = Header.component()
    val footer = Footer.component()
    def render: ReactElement = {
      <.div(
        header(),
        <.div(
        ),
        footer()
      )
    }
  }
  def component() = {
    ReactComponentB[Unit]("Top")
      .stateless
      .renderBackend[Top.Backend]
      .buildU
  }
}
