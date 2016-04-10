package jp.youkus.stcs.js.list.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import jp.youkus.stcs.js.{model => M}
import jp.youkus.stcs.shared.{json, util}
import scala.scalajs.js

object SheetTable {
  case class Prop(
    sheets: Seq[M.Sheet]
  )
  class Backend(scope: BackendScope[Prop, Unit]) {
    def render(p: Prop): ReactElement = {
      <.div(
        <.table(
          ^.classSet("list" -> true),
          <.thead(
            <.tr(
              <.th(
                ^.style := js.Dictionary("width" -> "20%"),
                "呼び名"
              ),
              <.th(
                ^.style := js.Dictionary("width" -> "12%"),
                "クラス"
              ),
              <.th(
                ^.style := js.Dictionary("width" -> "12%"),
                "タイプ"
              ),
              <.th(
                ^.style := js.Dictionary("width" -> "12%"),
                "オーデ"
              ),
              <.th(
                ^.style := js.Dictionary("width" -> "26%"),
                "タグ"
              ),
              <.th(
                ^.style := js.Dictionary("width" -> "18%"),
                "最終更新"
              )
            )
          ),
          <.tbody(
            p.sheets.map { sheet =>
              <.tr(
                <.td(
                  <.a(
                    ^.href := s"/sheet${sheet.id.map("/" + _).getOrElse("")}",
                    sheet.name
                  )
                ),
                <.td(
                  util.toClassName(sheet.csClass)
                ),
                <.td(
                  util.toTypeName(sheet.csType)
                ),
                <.td(
                  util.toEaudeName(sheet.csEaude)
                ),
                <.td(
                  sheet.tags.values.mkString(",")
                ),
                <.td(
                  sheet.updateDate
                )
              )
            }
          )
        )
      )
    }
  }
  def component() = {
    ReactComponentB[Prop]("SheetTable")
      .stateless
      .renderBackend[SheetTable.Backend]
      .build
  }
}
