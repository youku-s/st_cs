package jp.youkus.stcs.js.sheet

import japgolly.scalajs.react.ReactDOM
import jp.youkus.stcs.js.sheet.{component => C}
import jp.youkus.stcs.shared.json
import org.scalajs.dom.document
import org.scalajs.dom.ext.Ajax
import upickle.default.read

object Main {
  def render(id: String): Unit = {
    if (id.isEmpty) {
      val top = C.Top.component(None)
      ReactDOM.render(top(), document.getElementById("main"))
    } else {
      import scala.concurrent.ExecutionContext.Implicits.global
      val f = Ajax.get(s"/api/sheet/${id}")
      f.onSuccess { case xhr =>
        val top = C.Top.component(Some(read[json.response.Sheet](xhr.responseText)))
        ReactDOM.render(top(), document.getElementById("main"))
      }
      f.onFailure { case _ =>
        val top = C.Top.component(None)
        ReactDOM.render(top(), document.getElementById("main"))
      }
    }
  }
}
