package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.js.sheet.{model => M}

object Memo {
  case class Prop(
    memo: String
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, M.App]) {
    def onChange(e: ReactEventI): Callback = {
      pScope.modState(s => s.copy(memo = e.target.value))
    }
    def render(p: Prop) = {
      <.div(
        ^.classSet("box" -> true),
        <.h2("メモ"),
        <.div(
          <.textarea(
            ^.rows := "5",
            ^.value := p.memo,
            ^.onChange ==> onChange
          )
        )
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.App]) = {
    ReactComponentB[Prop]("Memo")
      .stateless
      .backend(scope => new Memo.Backend(scope, pScope))
      .renderBackend
      .build
  }
}
