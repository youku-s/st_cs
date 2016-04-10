package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object Header {
  class Backend(scope: BackendScope[Unit, Unit]) {
    def render = {
      <.div(
        ^.classSet("header" -> true),
        <.div(
          <.h1("少女展爛会キャラクターシート")
        ),
        <.div(
          ^.classSet("copyright" -> true),
          "「",
          <.a(
            ^.href := "http://www6.plala.or.jp/scrafts/glexb/",
            ^.target := "_blank",
            "少女展爛会"
          ),
          "」は",
          <.a(
            ^.href := "http://www6.plala.or.jp/scrafts/",
            ^.target := "_blank",
            "Simple Crafts"
          ),
          "様の著作物です。"
        )
      )
    }
  }
  def component() = {
    ReactComponentB[Unit]("Header")
      .stateless
      .renderBackend[Header.Backend]
      .buildU
  }
}
