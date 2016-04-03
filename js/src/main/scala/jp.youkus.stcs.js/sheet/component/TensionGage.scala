package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.js.{model => M}

object TensionGage {
  case class Prop(
    tensions: Map[Int, M.Tension]
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, M.Sheet]) {
    def onClick(index: Int, tension: M.Tension): Callback = {
      pScope.modState(s =>
        s.copy(
          tensions = s.tensions.get(index) match {
            case Some(t) => s.tensions + (index -> t.copy(status = if (t.status == 2) 0 else t.status + 1))
            case None => s.tensions
          }
        )
      )
    }
    def render(p: Prop) = {
      <.div(
        ^.classSet("box" -> true),
        <.h2("テンションゲージ"),
        <.div(
          <.div(
            <.label(
              "蓄積",
              <.input(
                ^.readOnly := true,
                ^.size := 4,
                ^.value := p.tensions.values.count(_.status > 0)
              )
            )
          ),
          <.div(
            p.tensions.toList.sortBy(_._1).map{ case (index, tension) =>
              <.div(
                ^.classSet("tension" -> true, "checked" -> (tension.status == 1), "used" -> (tension.status == 2)),
                ^.onClick --> onClick(index, tension),
                tension.number
              )
            }
          ),
          <.p("テンションをクリックすると、テンションが蓄えられます(黄色)。"),
          <.p("蓄えられたテンションをクリックすると、テンションが閉じられます(灰色)。"),
          <.p("閉じられたテンションをクリックすると、テンションが消去されます。")
        )
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.Sheet]) = {
    ReactComponentB[Prop]("TensionGage")
      .stateless
      .backend(scope => new TensionGage.Backend(scope, pScope))
      .renderBackend
      .build
  }
}
