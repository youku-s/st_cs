package jp.youkus.stcs.js.list.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI, ReactKeyboardEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import jp.youkus.stcs.shared.json
import jp.youkus.stcs.js.{model => M}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

object Search {
  class Backend(scope: BackendScope[Unit, String], pScope: BackendScope[Unit, Top.State]) {
    def onChange(e: ReactEventI): Callback = {
      scope.setState(e.target.value)
    }
    def onKeyDown(text: String)(e: ReactKeyboardEventI): Callback = {
      if (e.keyCode == 13) {
        search(text)
      } else {
        Callback.empty
      }
    }
    def search(text: String): Callback = {
      val tags = if (text.isEmpty) { None } else { Some(text.split("\\s").filter(!_.isEmpty).toSeq) }
      Top.getSheets(0, tags)
        .onSuccess { case state =>
          pScope.setState(state).runNow
        }
      scope.setState("")
    }
    def render(s: String): ReactElement = {
      <.div(
        ^.classSet("search" -> true, "row" -> true),
        <.input(
          ^.`type` := "text",
          ^.value:= s,
          ^.onChange ==> onChange,
          ^.onKeyDown ==> onKeyDown(s)
        ),
        " ",
        <.button(
          "タグ検索",
          ^.onClick --> search(s)
        )
      )
    }
  }
  def component(pScope: BackendScope[Unit, Top.State]) = {
    ReactComponentB[Unit]("Search")
      .initialState("")
      .backend(scope => new Search.Backend(scope, pScope))
      .renderBackend
      .build
  }
}
