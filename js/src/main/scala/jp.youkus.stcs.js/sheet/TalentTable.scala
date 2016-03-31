package jp.youkus.stcs.js.sheet

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

object TalentTable {
  case class Prop(
    csClass: Option[Int],
    csType: Option[Int],
    relations: Seq[Relation],
    parts: Seq[Part]
  )
  class Backend(scope: BackendScope[Prop, Unit]) {
    val classTalentMap = Map(
      0 -> Talent.preset(3, 1, 1, 1, 1, 1, 1, 1),
      1 -> Talent.preset(3, 2, 2, 2, 1, 0, 0, 0),
      2 -> Talent.preset(2, 1, 3, 1, 0, 1, 1, 1),
      3 -> Talent.preset(2, 2, 1, 2, 0, 1, 1, 1),
      4 -> Talent.preset(1, 2, 1, 3, 1, 1, 1, 0),
      5 -> Talent.preset(1, 3, 2, 1, 0, 1, 1, 1),
      6 -> Talent.preset(1, 2, 2, 2, 0, 1, 1, 1),
      7 -> Talent.preset(1, 3, 2, 1, 1, 1, 0, 1),
      8 -> Talent.preset(3, 1, 1, 2, 1, 0, 1, 1),
      9 -> Talent.preset(1, 2, 2, 3, 1, 0, 1, 0),
      10 -> Talent.preset(2, 1, 3, 2, 1, 0, 0, 1)
    )
    val typeTalentMap = Map(
      0 -> Talent.preset(1, 0, 2, 0, 2, 1, 2, 1),
      1 -> Talent.preset(1, 1, 1, 1, 1, 2, 1, 1),
      2 -> Talent.preset(2, 0, 1, 0, 2, 1, 1, 2),
      3 -> Talent.preset(0, 2, 0, 1, 1, 1, 2, 2),
      4 -> Talent.preset(1, 1, 0, 0, 1, 2, 2, 2),
      5 -> Talent.preset(0, 1, 1, 1, 2, 2, 1, 1),
      6 -> Talent.preset(0, 1, 0, 2, 2, 1, 1, 2),
      7 -> Talent.preset(1, 0, 1, 1, 1, 2, 2, 1)
    )
    def render(p: Prop): ReactElement = {
      val classTalent = p.csClass.flatMap(c => classTalentMap.get(c))
      val typeTalent = p.csType.flatMap(c => typeTalentMap.get(c))
      <.div(
        ^.classSet("box" -> true),
        <.h2("資質・傾き・慎み"),
        <.div(
          <.table(
            ^.classSet("part" -> true),
            <.thead(
              <.tr(
                <.th(
                  ^.rowSpan := "2",
                  ^.style := js.Dictionary("width" -> "30%")
                ),
                <.th(
                  ^.colSpan := "4",
                  ^.style := js.Dictionary("width" -> "30%"),
                  "資質"
                ),
                <.th(
                  ^.colSpan := "2",
                  ^.style := js.Dictionary("width" -> "15%"),
                  "傾き"
                ),
                <.th(
                  ^.colSpan := "2",
                  ^.style := js.Dictionary("width" -> "15%"),
                  "慎み"
                ),
                <.th(
                  ^.classSet("noborder" -> true),
                  ^.style := js.Dictionary("width" -> "10%")
                )
              ),
              <.tr(
                <.th("支配"),
                <.th("従順"),
                <.th("打算"),
                <.th("純真"),
                <.th("押し"),
                <.th("察し"),
                <.th("好意"),
                <.th("悪意"),
                <.th(
                  ^.classSet("noborder" -> true)
                )
              )
            ),
            <.tbody(
              <.tr(
                <.th("クラス："),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("shihai" -> true),
                    ^.value := classTalent.flatMap(_.shihai).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyujyun" -> true),
                    ^.value := classTalent.flatMap(_.jyujyun).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("dasan" -> true),
                    ^.value := classTalent.flatMap(_.dasan).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyunshin" -> true),
                    ^.value := classTalent.flatMap(_.jyunshin).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("oshi" -> true),
                    ^.value := classTalent.flatMap(_.oshi).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("sasshi" -> true),
                    ^.value := classTalent.flatMap(_.sasshi).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("koui" -> true),
                    ^.value := classTalent.flatMap(_.koui).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("akui" -> true),
                    ^.value := classTalent.flatMap(_.akui).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  ^.classSet("noborder" -> true)
                )
              ),
              <.tr(
                <.th("タイプ："),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("shihai" -> true),
                    ^.value := typeTalent.flatMap(_.shihai).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyujyun" -> true),
                    ^.value := typeTalent.flatMap(_.jyujyun).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("dasan" -> true),
                    ^.value := typeTalent.flatMap(_.dasan).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyunshin" -> true),
                    ^.value := typeTalent.flatMap(_.jyunshin).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("oshi" -> true),
                    ^.value := typeTalent.flatMap(_.oshi).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("sasshi" -> true),
                    ^.value := typeTalent.flatMap(_.sasshi).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("koui" -> true),
                    ^.value := typeTalent.flatMap(_.koui).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("akui" -> true),
                    ^.value := typeTalent.flatMap(_.akui).map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  ^.classSet("noborder" -> true)
                )
              ),
              <.tr(
                <.th("関係による加算："),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("shihai" -> true),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyujyun" -> true),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("dasan" -> true),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyunshin" -> true),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("oshi" -> true),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("sasshi" -> true),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("koui" -> true),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("akui" -> true),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  ^.classSet("noborder" -> true)
                )
              ),
              <.tr(
                <.th("世界の部品"),
                <.th("支配"),
                <.th("従順"),
                <.th("打算"),
                <.th("純真"),
                <.th("押し"),
                <.th("察し"),
                <.th("好意"),
                <.th("悪意"),
                <.th(
                  ^.classSet("noborder" -> true)
                )
              ),
              (if(p.parts.isEmpty) Seq(Part("", Talent.default)) else p.parts).map{ part =>
                <.tr(
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.value := part.name
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("shihai" -> true),
                      ^.value := part.talent.shihai
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("jyujyun" -> true),
                      ^.value := part.talent.jyujyun
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("dasan" -> true),
                      ^.value := part.talent.dasan
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("jyunshin" -> true),
                      ^.value := part.talent.jyunshin
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("oshi" -> true),
                      ^.value := part.talent.oshi
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("sasshi" -> true),
                      ^.value := part.talent.sasshi
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("koui" -> true),
                      ^.value := part.talent.koui
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("akui" -> true),
                      ^.value := part.talent.akui
                    )
                  ),
                  <.td(
                    ^.classSet("noborder" -> true),
                    <.button(
                      ^.classSet("deleteRow" -> true),
                      "削除"
                    )
                  )
                )
              }
            )
          ),
          <.button(
            ^.classSet("addPart" -> true),
            "行追加"
          )
        )
      )
    }
  }
  def component(p: Prop) = {
    ReactComponentB[TalentTable.Prop]("TalentTable")
      .stateless
      .renderBackend[TalentTable.Backend]
      .build(p)
  }
}

