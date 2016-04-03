package jp.youkus.stcs.js.list.component

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI, ReactKeyboardEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.shared.json
import jp.youkus.stcs.js.{model => M}

object Paging {
  case class Prop(
    offset: Int,
    max: Int
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, Top.State]) {
    def render(p: Prop): ReactElement = {
      <.div(
      )
    }
  }
  def component(pScope: BackendScope[Unit, Top.State]) = {
    ReactComponentB[Paging.Prop]("Paging")
      .stateless
      .backend(scope => new Paging.Backend(scope, pScope))
      .renderBackend
      .build
  }
}
