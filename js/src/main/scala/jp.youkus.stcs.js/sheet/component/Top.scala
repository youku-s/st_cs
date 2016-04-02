package jp.youkus.stcs.js.sheet.component

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.shared.json
import jp.youkus.stcs.js.sheet.{model => M}

object Top {
  class Backend(scope: BackendScope[Unit, M.App]) {
    val header = Header.component()
    val baseTable = BaseTable.component(scope)
    val talentTable = TalentTable.component(scope)
    val tensionGage = TensionGage.component(scope)
    val itemTable = ItemTable.component(scope)
    val skillTable = SkillTable.component(scope)
    val relationTable = RelationTable.component(scope)
    val password = Password.component(scope)
    val tag = Tag.component(scope)
    val footer = Footer.component()
    val memo = Memo.component(scope)
    def render(s: M.App): ReactElement = {
      <.div(
        header(),
        <.div(
          ^.classSet("left" -> true),
          baseTable(BaseTable.Prop(s.name, s.csClass, s.csType, s.csEaude)),
          talentTable(TalentTable.Prop(s.csClass, s.csType, s.relations, s.parts)),
          tensionGage(TensionGage.Prop(s.tensions)),
          itemTable(ItemTable.Prop(s.items)),
          skillTable(SkillTable.Prop(s.skills)),
          relationTable(RelationTable.Prop(s.relations)),
          memo(Memo.Prop(s.memo))
        ),
        <.div(
          ^.classSet("right" -> true),
          password(Password.Prop(s.password, s.display)),
          tag(Tag.Prop(s.tags))
        ),
        footer()
      )
    }
  }
  def component(sheet: Option[json.Sheet]) = {
    ReactComponentB[Unit]("Top")
      .initialState(M.App.initialValue)
      .renderBackend[Top.Backend]
      .buildU
  }
}
