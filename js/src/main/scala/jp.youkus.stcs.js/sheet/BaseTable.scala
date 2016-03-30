package jp.youkus.stcs.js.sheet

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object BaseTable {
  case class Prop(
    onNameChange: ReactEventI => Callback,
    onClassChange: ReactEventI => Callback,
    onTypeChange: ReactEventI => Callback,
    onEaudeChange: ReactEventI => Callback,
    name: String,
    csClass: Option[Int],
    csType: Option[Int],
    csEaude: Option[Int]
  )
  class Backend(scope: BackendScope[Prop, Unit]) {
    def render(p: Prop): ReactElement = {
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
                    ^.onChange ==> p.onNameChange
                  )
                )
              ),
              <.tr(
                <.th("クラス"),
                <.td(
                  <.select(
                    ^.value := p.csClass.getOrElse(-1),
                    ^.onChange ==> p.onClassChange,
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
                    ^.onChange ==> p.onTypeChange,
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
                    ^.onChange ==> p.onEaudeChange,
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
  def component(p: Prop) = {
    ReactComponentB[Prop]("BaseTable")
      .stateless
      .renderBackend[Backend]
      .build(p)
  }
}

