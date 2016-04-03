package jp.youkus.stcs.js.sheet.component

import scala.scalajs.js
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData._

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import upickle.default.write

import jp.youkus.stcs.shared.json
import jp.youkus.stcs.js.{model => M}

object Top {
  class Backend(scope: BackendScope[Unit, M.Sheet]) {
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
    def onSave(s: M.Sheet): Callback = Callback {
      s.id match {
        case Some(id) => {
          val f = Ajax.post(s"/api/sheet/${id}", write(M.Sheet.toJson(s)))
          f.onSuccess { case xhr =>
            println("save success")
          }
          f.onFailure { case _ =>
            println("save fail")
          }
        }
        case None => // do nothing
      }
    }
    def onDelete(s: M.Sheet): Callback = Callback {
      s.id match {
        case Some(id) => {
          val f = Ajax.delete(s"/api/sheet/${id}", write(json.Password(s.password)))
          f.onSuccess { case xhr =>
            println("delete success")
          }
          f.onFailure { case _ =>
            println("delete fail")
          }
        }
        case None => // do nothing
      }
    }
    def onCreate(s: M.Sheet): Callback = Callback {
      val f = Ajax.post("/api/sheet", write(M.Sheet.toJson(s)))
      f.onSuccess { case xhr =>
        println("create success")
      }
      f.onFailure { case _ =>
        println("create fail")
      }
    }
    def render(s: M.Sheet): ReactElement = {
      <.div(
        header(),
        <.div(
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
            password(Password.Prop(onSave(s), onDelete(s), onCreate(s), s.id, s.display)),
            tag(Tag.Prop(s.tags))
          )
        ),
        footer()
      )
    }
  }
  def component(sheet: Option[json.Sheet]) = {
    ReactComponentB[Unit]("Top")
      .initialState(sheet.map(M.Sheet.fromJson).getOrElse(M.Sheet.initialValue))
      .renderBackend[Top.Backend]
      .buildU
  }
}
