package jp.youku_s.st_cs.js.sheet

import org.scalajs.dom.document

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import japgolly.scalajs.react.{BackendScope, ReactComponentB, ReactDOM, ReactElement}
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

case class BaseState(name: String, csClass: Option[Int], csType: Option[Int], csEaude: Option[Int])

class BaseBackend($: BackendScope[Unit, BaseState]) {
  def render(s: BaseState): ReactElement = {
    <.div(
      ^.classSet("box" -> true),
      <.h2("基本"),
      <.div(
        <.table(
          <.tbody(
            <.tr(
              <.th("呼び名"),
              <.td(
                ^.colSpan := "5",
                <.input(
                  ^.`type` := "text",
                  ^.value := s.name
                )
              )
            )
          )
        )
      )
    )
  }
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

class TopBackend($: BackendScope[Unit, State]) {
  val base = ReactComponentB[Unit]("Base")
    .initialState(BaseState("", None, None, None))
    .renderBackend[BaseBackend]
    .build
  def render(s: State): ReactElement = {
    <.div(
      ^.style := js.Dictionary("width" -> "80%"),
      <.h1("少女展爛会キャラクターシート"),
      base(())
    )
  }
}

@JSExport
object Main extends JSApp {
  val top = ReactComponentB[Unit]("Top")
    .initialState(State.initialValue)
    .renderBackend[TopBackend]
    .build
  @JSExport
  override def main(): Unit = {
    ReactDOM.render(top(()), document.getElementById("main"))
  }
}
