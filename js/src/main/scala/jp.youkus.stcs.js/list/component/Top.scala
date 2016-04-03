package jp.youkus.stcs.js.list.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import jp.youkus.stcs.shared.json
import jp.youkus.stcs.js.{model => M}
import org.scalajs.dom.ext.Ajax
import org.scalajs.dom.ext.Ajax.InputData._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.util.{Failure, Success}
import upickle.default.{read, write}

object Top {
  case class State(
    offset: Int,
    max: Int,
    sheets: Seq[M.Sheet]
  )
  def getSheets(offset: Int, tags: Option[Seq[String]] = None): Future[State] = {
    val jd = write(json.Search(20, offset, tags))
    Ajax.get(s"/api/lists?q=${jd}")
      .map { xhr =>
        val result = read[json.SearchResult[json.response.Sheet]](xhr.responseText)
        State(offset, result.count, result.result.map(M.Sheet.fromJson))
      }
  }
  class Backend(scope: BackendScope[Unit, State]) {
    val header = Header.component()
    val search = Search.component(scope)
    val sheetTable = SheetTable.component()
    val paging = Paging.component(scope)
    val footer = Footer.component()
    def render(s: State): ReactElement = {
      <.div(
        header(),
        <.div(
          ^.classSet("lists" -> true),
          search(),
          sheetTable(SheetTable.Prop(s.sheets)),
          paging(Paging.Prop(s.offset, s.max))
        ),
        footer()
      )
    }
  }
  def component() = {
    ReactComponentB[Unit]("Top")
      .initialState(State(0, 0, Seq.empty))
      .renderBackend[Top.Backend]
      .componentWillMount{ x => Callback {
          Top.getSheets(0).onSuccess { case state => 
            x.setState(state).runNow 
          }
        }
      }
      .buildU
  }
}
