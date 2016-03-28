package jp.youku_s.st_cs.js.sheet

import org.scalajs.dom.document

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import japgolly.scalajs.react.{ReactComponentB, ReactDOM}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

@JSExport
object Main extends JSApp {
  val NoArgs = ReactComponentB[Unit]("No args")
      .render(_ => <.div("Hello!"))
      .build
  @JSExport
  override def main(): Unit = {
    ReactDOM.render(NoArgs(()), document.getElementById("main"))
  }
}
