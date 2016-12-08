package jp.youkus.stcs.js.list.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI, ReactKeyboardEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import jp.youkus.stcs.shared.json
import jp.youkus.stcs.js.{model => M}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

object Paging {
  val pageUnit = 10
  case class Prop(
    offset: Int,
    max: Int,
    count: Int
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, Top.State]) {
    def onPageChange(offset: Int, p: Prop): Callback = Callback {
      if (offset >= 0 && offset < p.max && offset != p.offset) {
        Top.getSheets(offset).onSuccess { case state => 
          pScope.setState(state).runNow 
        }
      }
    }
    def lastOffset(max: Int): Int = {
      if (max % pageUnit == 0) { ((max / pageUnit) - 1) * pageUnit } else { (max / pageUnit) * pageUnit }
    }
    def render(p: Prop): ReactElement = {
      val current = p.offset / pageUnit + 1
      val maxPage = ((p.max - 1) / pageUnit) + 1
      val head = if (current <= 1) { 1 } else if(current + 2 > maxPage) { maxPage - 4 } else { current - 2 }
      val indexes = Seq.iterate(0, ((p.max - 1) / pageUnit) + 1)(_ + 1).map{ x => (x + 1, x * pageUnit) }
        .dropWhile{ case (i, _) => i < head }.take(5)
      val from = if (p.max == 0) 0 else (current - 1) * pageUnit + 1
      <.div(
        <.div(
          s"全${p.max}件中、${from}件目から${p.count}件を表示中"
        ),
        <.div(
          ^.classSet("pagination" -> true),
          <.span(
            "first",
            ^.onClick --> onPageChange(0, p)
          ),
          <.span(
            "<",
            ^.onClick --> onPageChange(p.offset - pageUnit, p)
          ),
          indexes.map { case (index, offset) =>
            <.span(
              ^.classSet("current" -> (offset == p.offset)),
              index,
              ^.onClick --> onPageChange(offset, p)
            )
          },
          <.span(
            ">",
            ^.onClick --> onPageChange(p.offset + pageUnit, p)
          ),
          <.span(
            "last",
            ^.onClick --> onPageChange(lastOffset(p.max), p)
          )
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
