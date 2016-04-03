package jp.youkus.stcs.js.list.component

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.shared.json
import jp.youkus.stcs.js.{model => M}

object SheetTable {
  case class Prop(
    sheets: Seq[M.Sheet]
  )
  class Backend(scope: BackendScope[Prop, Unit]) {
    val classTable = Map(
      0 -> "エンプレス",
      1 -> "プリンセス",
      2 -> "コート",
      3 -> "デイム",
      4 -> "ハイブロウ",
      5 -> "メイド",
      6 -> "コモン",
      7 -> "ペット",
      8 -> "ゲイム",
      9 -> "フェアリー",
      10 -> "ボギー"
    )
    val typeTable = Map(
      0 -> "アリス",
      1 -> "ドロシー",
      2 -> "グレーテル",
      3 -> "シンデレラ",
      4 -> "なよ竹",
      5 -> "赤ずきん",
      6 -> "人魚姫",
      7 -> "ウェンディ"
    )
    val eaudeTable = Map(
      0 -> "ハートフル",
      1 -> "ロマンティック",
      2 -> "ルナティック"
    )
    def toClassName(csClass: Option[Int]): String = {
      csClass.flatMap(classTable.get).getOrElse("-")
    }
    def toTypeName(csType: Option[Int]): String = {
      csType.flatMap(typeTable.get).getOrElse("-")
    }
    def toEaudeName(csEaude: Option[Int]): String = {
      csEaude.flatMap(eaudeTable.get).getOrElse("-")
    }
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
                ^.style := js.Dictionary("width" -> "15%"),
                "クラス"
              ),
              <.th(
                ^.style := js.Dictionary("width" -> "15%"),
                "タイプ"
              ),
              <.th(
                ^.style := js.Dictionary("width" -> "15%"),
                "オーデ"
              ),
              <.th(
                ^.style := js.Dictionary("width" -> "20%"),
                "タグ"
              ),
              <.th(
                ^.style := js.Dictionary("width" -> "15%"),
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
                  toClassName(sheet.csClass)
                ),
                <.td(
                  toTypeName(sheet.csType)
                ),
                <.td(
                  toEaudeName(sheet.csEaude)
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
