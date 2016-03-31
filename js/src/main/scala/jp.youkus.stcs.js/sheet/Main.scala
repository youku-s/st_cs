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
  shihai: Option[Int],
  jyujyun: Option[Int],
  dasan: Option[Int],
  jyunshin: Option[Int],
  oshi: Option[Int],
  sasshi: Option[Int],
  koui: Option[Int],
  akui: Option[Int]
)
object Talent {
  def default: Talent = Talent(None, None, None, None, None, None, None, None)
  def preset(shihai: Int, jyujyun: Int, dasan: Int, jyunshin: Int, oshi: Int, sasshi: Int, koui: Int, akui: Int): Talent = Talent(
    Option(shihai),
    Option(jyujyun),
    Option(dasan),
    Option(jyunshin),
    Option(oshi),
    Option(sasshi),
    Option(koui),
    Option(akui)
  )
}
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

object Top {
  class Backend(scope: BackendScope[Unit, State]) {
    val baseTable = BaseTable.component(scope)
    val talentTable = TalentTable.component()
    def render(s: State): ReactElement = {
      <.div(
        ^.style := js.Dictionary("width" -> "80%"),
        <.h1("少女展爛会キャラクターシート"),
        baseTable(BaseTable.Prop(s.name, s.csClass, s.csType, s.csEaude)),
        talentTable(TalentTable.Prop(s.csClass, s.csType, s.relations, s.parts))
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
