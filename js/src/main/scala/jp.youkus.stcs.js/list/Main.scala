package jp.youkus.stcs.js.list

import org.scalajs.dom.document

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import japgolly.scalajs.react.ReactDOM

import jp.youkus.stcs.js.list.{component => C}

@JSExport
object Main extends JSApp {
  val top = C.Top.component()
  @JSExport
  def main(): Unit = {
    ReactDOM.render(top(), document.getElementById("main"))
  }
}
