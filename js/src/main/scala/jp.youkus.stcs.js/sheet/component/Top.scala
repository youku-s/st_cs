package jp.youkus.stcs.js.sheet.component

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.js.sheet.{model => M}

object Top {
  class Backend(scope: BackendScope[Unit, M.App]) {
    val baseTable = BaseTable.component(scope)
    val talentTable = TalentTable.component()
    def render(s: M.App): ReactElement = {
      <.div(
        ^.style := js.Dictionary("width" -> "80%"),
        <.h1("少女展爛会キャラクターシート"),
        baseTable(BaseTable.Prop(s.name, s.csClass, s.csType, s.csEaude)),
        talentTable(TalentTable.Prop(s.csClass, s.csType, s.relations, s.parts))
      )
    }
  }
  def component() = {
    ReactComponentB[Unit]("Top")
      .initialState(M.App.initialValue)
      .renderBackend[Top.Backend]
      .buildU
  }
}
