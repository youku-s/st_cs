package jp.youkus.stcs.js.sheet

import org.scalajs.dom.document
import org.scalajs.dom.ext.Ajax

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import japgolly.scalajs.react.ReactDOM
import upickle.default.read

import jp.youkus.stcs.js.sheet.{component => C}

@JSExport
object Main extends JSApp {
  def main(): Unit = {
  }
  @JSExport
  def render(id: String): Unit = {
    if (id.isEmpty) {
      val top = C.Top.component(None)
      ReactDOM.render(top(), document.getElementById("main"))
    } else {
      import scala.concurrent.ExecutionContext.Implicits.global
      Ajax.get(s"/api/sheet/${id}")
        .onSuccess { case xhr =>
          val top = C.Top.component(Some(read(xhr.responseText)))
          ReactDOM.render(top(), document.getElementById("main"))
        }
        .onFailure { case _ =>
          val top = C.Top.component(None)
          ReactDOM.render(top(), document.getElementById("main"))
        }
    }
  }
}
