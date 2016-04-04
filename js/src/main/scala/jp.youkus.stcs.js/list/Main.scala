package jp.youkus.stcs.js.list

import japgolly.scalajs.react.ReactDOM
import jp.youkus.stcs.js.list.{component => C}
import org.scalajs.dom.document
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport
object Main extends JSApp {
  @JSExport
  def main(): Unit = {
    C.Top.getSheets(0).onSuccess { case state => 
      val top = C.Top.component(state)
      ReactDOM.render(top(), document.getElementById("main"))
    }
  }
}
