package jp.youkus.stcs.js.list.component

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI, ReactKeyboardEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.shared.json
import jp.youkus.stcs.js.{model => M}

object Paging {
  val pageUnit = 2
  case class Prop(
    offset: Int,
    max: Int
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, Top.State]) {
    def onPageChange(offset: Int): Callback = {
      pScope.modState(s => s.copy(offset = offset))
    }
    def render(p: Prop): ReactElement = {
      val indexes = Seq.iterate(0, (p.max / pageUnit) + 1)(_ + 1).map{ x => (x + 1, x * pageUnit) }
      <.div(
        ^.classSet("pagination" -> true),
        <.span(
          "<",
          ^.onClick --> onPageChange(p.offset - pageUnit)
        ),
        indexes.map { case (index, offset) =>
          println(offset)
          println(p.offset)
          <.span(
            ^.classSet("current" -> (offset == p.offset)),
            index,
            ^.onClick --> onPageChange(offset)
          )
        },
        <.span(
          ">",
          ^.onClick --> onPageChange(p.offset + pageUnit)
        )
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
