package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import jp.youkus.stcs.js.{model => M}

object BaseTable {
  case class Prop(
    name: String,
    csClass: Option[Int],
    csType: Option[Int],
    csEaude: Option[Int]
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, M.Sheet]) {
    def toIntOpt(x: String): Option[Int] = try {
      Some(x.toInt)
    } catch {
      case e: Exception => None
    }
    def onNameChange(e: ReactEventI): Callback = {
      pScope.modState(_.copy(name = e.target.value))
    }
    def onClassChange(e: ReactEventI): Callback = {
      pScope.modState(_.copy(csClass = toIntOpt(e.target.value)))
    }
    def onTypeChange(e: ReactEventI): Callback = {
      pScope.modState(_.copy(csType = toIntOpt(e.target.value)))
    }
    def onEaudeChange(e: ReactEventI): Callback = {
      pScope.modState(_.copy(csEaude = toIntOpt(e.target.value)))
    }
    def render(p: Prop) = {
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
                    ^.value := p.name,
                    ^.onChange ==> onNameChange
                  )
                )
              ),
              <.tr(
                <.th("クラス"),
                <.td(
                  <.select(
                    ^.value := p.csClass.getOrElse(-1),
                    ^.onChange ==> onClassChange,
                    <.option(^.value := "-1", "-"),
                    <.option(^.value := "0", "エンプレス"),
                    <.option(^.value := "1", "プリンセス"),
                    <.option(^.value := "2", "コート"),
                    <.option(^.value := "3", "デイム"),
                    <.option(^.value := "4", "ハイブロウ"),
                    <.option(^.value := "5", "メイド"),
                    <.option(^.value := "6", "コモン"),
                    <.option(^.value := "7", "ペット"),
                    <.option(^.value := "8", "ゲイム"),
                    <.option(^.value := "9", "フェアリー"),
                    <.option(^.value := "10", "ボギー")
                  )
                ),
                <.th("タイプ"),
                <.td(
                  <.select(
                    ^.value := p.csType.getOrElse(-1),
                    ^.onChange ==> onTypeChange,
                    <.option(^.value := "-1", "-"),
                    <.option(^.value := "0", "アリス"),
                    <.option(^.value := "1", "ドロシー"),
                    <.option(^.value := "2", "グレーテル"),
                    <.option(^.value := "3", "シンデレラ"),
                    <.option(^.value := "4", "なよ竹"),
                    <.option(^.value := "5", "赤ずきん"),
                    <.option(^.value := "6", "人魚姫"),
                    <.option(^.value := "7", "ウェンディ")
                  )
                ),
                <.th("オーデ"),
                <.td(
                  <.select(
                    ^.value := p.csEaude.getOrElse(-1),
                    ^.onChange ==> onEaudeChange,
                    <.option(^.value := "-1", "-"),
                    <.option(^.value := "0", "ハートフル"),
                    <.option(^.value := "1", "ロマンティック"),
                    <.option(^.value := "2", "ルナティック")
                  )
                )
              )
            )
          )
        )
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.Sheet]) = {
    ReactComponentB[Prop]("BaseTable")
      .stateless
      .backend(scope => new BaseTable.Backend(scope, pScope))
      .renderBackend
      .build
  }
}

