package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.js.sheet.{model => M}

object Password {
  case class Prop(
    password: Option[String]
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, M.App]) {
    /*def onChange(e: ReactEventI): Callback = {
      pScope.modState(s => s.copy(memo = e.target.value))
    }
    */
    def render(p: Prop) = {
      <.div(
        ^.classSet("box" -> true)
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.App]) = {
    ReactComponentB[Prop]("Password")
      .stateless
      .backend(scope => new Password.Backend(scope, pScope))
      .renderBackend
      .build
  }
}
