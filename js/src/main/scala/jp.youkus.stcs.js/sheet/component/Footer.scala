package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object Footer {
  class Backend(scope: BackendScope[Unit, Unit]) {
    def render = {
      <.div(
        ^.classSet("box" -> true),
        "「",
        <.a(
          ^.href := "http://www6.plala.or.jp/scrafts/glexb/",
          ^.target := "_blank",
          "少女展爛会"
        ),
        "」はSimple Crafts様の著作物です。"
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
