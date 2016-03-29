package jp.youkus.stcs.js.sheet

import org.scalajs.dom.document

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactDOM, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

case class State(
  name: String,
  playerName: String,
  csClass: Option[Int],
  csType: Option[Int],
  csEaude: Option[Int],
  parts: Seq[Part],
  items: Seq[Item],
  skills: Seq[Skill],
  relations: Seq[Relation],
  tensions: Seq[Tension],
  memo: String
)

case class Part(
  name: String,
  talent: Talent
)
case class Talent(
  shihai: Int,
  jyujyun: Int,
  dasan: Int,
  jyunshin: Int,
  oshi: Int,
  sasshi: Int,
  koui: Int,
  akui: Int
)
case class Item(
  name: String,
  main: Int,
  sub: Int
)
case class Skill(
  name: String,
  timing: String,
  cost: Int,
  detail: String
)
case class Relation(
  to: String,
  name: String,
  ueshita: Int,
  semeuke: Int
)
case class Tension(
  number: Option[Int],
  ckecked: Boolean,
  used: Boolean
)

object Tension {
  def initial = Seq(
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(Some(5), false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(Some(10), false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(Some(15), false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(None, false, false),
    Tension(Some(20), false, false)
  )
}

object State {
  def initialValue: State = State(
    name = "",
    playerName = "",
    csClass = None,
    csType = None,
    csEaude = None,
    parts = Seq.empty,
    items = Seq.empty,
    skills = Seq.empty,
    relations = Seq.empty,
    tensions = Tension.initial,
    memo = ""
  )
}

object TalentTable {
  case class Prop(
    csClass: Option[Int],
    csType: Option[Int],
    relations: Seq[Relation]
  )
  class Backend(scope: BackendScope[Prop, Unit]) {
    def render(p: Prop): ReactElement = {
      <.div("hoge")
    }
  }
}

object Top {
  class Backend(scope: BackendScope[Unit, State]) {
    val classTallentMap = Map(
      0 -> Talent(3, 1, 1, 1, 1, 1, 1, 1),
      1 -> Talent(3, 2, 2, 2, 1, 0, 0, 0),
      2 -> Talent(2, 1, 3, 1, 0, 1, 1, 1),
      3 -> Talent(2, 2, 1, 2, 0, 1, 1, 1),
      4 -> Talent(1, 2, 1, 3, 1, 1, 1, 0),
      5 -> Talent(1, 3, 2, 1, 0, 1, 1, 1),
      6 -> Talent(1, 2, 2, 2, 0, 1, 1, 1),
      7 -> Talent(1, 3, 2, 1, 1, 1, 0, 1),
      8 -> Talent(3, 1, 1, 2, 1, 0, 1, 1),
      9 -> Talent(1, 2, 2, 3, 1, 0, 1, 0),
      10 -> Talent(2, 1, 3, 2, 1, 0, 0, 1)
    )
    val typeTallentMap = Map(
      0 -> Talent(1, 0, 2, 0, 2, 1, 2, 1),
      1 -> Talent(1, 1, 1, 1, 1, 2, 1, 1),
      2 -> Talent(2, 0, 1, 0, 2, 1, 1, 2),
      3 -> Talent(0, 2, 0, 1, 1, 1, 2, 2),
      4 -> Talent(1, 1, 0, 0, 1, 2, 2, 2),
      5 -> Talent(0, 1, 1, 1, 2, 2, 1, 1),
      6 -> Talent(0, 1, 0, 2, 2, 1, 1, 2),
      7 -> Talent(1, 0, 1, 1, 1, 2, 2, 1)
    )
    val talentTable = ReactComponentB[TalentTable.Prop]("TalentTable")
      .stateless
      .renderBackend[TalentTable.Backend]
      .build
    def toIntOpt(x: String): Option[Int] = try {
      Some(x.toInt)
    } catch {
      case e: Exception => None
    }
    def onNameChange(e: ReactEventI): Callback = {
      scope.modState(_.copy(name = e.target.value))
    }
    def onClassChange(e: ReactEventI): Callback = {
      scope.modState(_.copy(csClass = toIntOpt(e.target.value)))
    }
    def onTypeChange(e: ReactEventI): Callback = {
      scope.modState(_.copy(csType = toIntOpt(e.target.value)))
    }
    def onEaudeChange(e: ReactEventI): Callback = {
      scope.modState(_.copy(csEaude = toIntOpt(e.target.value)))
    }
    def render(s: State): ReactElement = {
      <.div(
        ^.style := js.Dictionary("width" -> "80%"),
        <.h1("少女展爛会キャラクターシート"),
        BaseTable.component()(BaseTable.Prop(onNameChange, onClassChange, onTypeChange, onEaudeChange, s.name, s.csClass, s.csType, s.csEaude)),
        talentTable(TalentTable.Prop(s.csClass, s.csType, s.relations))
      )
    }
  }
}

@JSExport
object Main extends JSApp {
  val top = ReactComponentB[Unit]("Top")
    .initialState(State.initialValue)
    .renderBackend[Top.Backend]
    .build
  @JSExport
  override def main(): Unit = {
    ReactDOM.render(top(()), document.getElementById("main"))
  }
}
