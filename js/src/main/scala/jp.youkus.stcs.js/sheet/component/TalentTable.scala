package jp.youkus.stcs.js.sheet.component

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.js.sheet.{model => M, util}

object TalentTable {
  case class Prop(
    csClass: Option[Int],
    csType: Option[Int],
    relations: Map[Int, M.Relation],
    parts: Map[Int, M.Part]
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, M.App]) {
    def onChange(index: Int, f: (M.Part, String) => M.Part)(e: ReactEventI): Callback = {
      pScope.modState(s => 
        s.copy(
          parts = s.parts.get(index) match {
            case Some(p) => s.parts + (index -> f(p, e.target.value))
            case None => s.parts
          }
        )
      )
    }
    def deleteRow(index: Int): Callback = {
      pScope.modState(s =>
        s.copy(
          parts = s.parts.get(index) match {
            case Some(p) => s.parts - index
            case None => s.parts
          }
        )
      )
    }
    def addRow(): Callback = {
      pScope.modState(s =>
        s.copy(
          parts = {
            val next = if (s.parts.isEmpty) 0 else s.parts.keys.max + 1
            s.parts + (next -> M.Part("", M.Talent.default))
          }
        )
      )
    }
    def calculateRelationTalent(relations: Map[Int, M.Relation]): M.Talent = {
      val rels = relations.values
      var shihai = 0
      var jyunshin = 0
      var dasan = 0
      var jyujyun = 0
      var oshi = 0
      var sasshi = 0
      val ueshitaGroup = rels.groupBy(x => x.ueshita)
      val semeukeGroup = rels.groupBy(x => x.semeuke)
      shihai += ueshitaGroup.get(Some(1)).getOrElse(Seq.empty).size / 4
      dasan += ueshitaGroup.get(Some(1)).getOrElse(Seq.empty).size / 4
      jyunshin += ueshitaGroup.get(Some(2)).getOrElse(Seq.empty).size / 4
      oshi += ueshitaGroup.get(Some(2)).getOrElse(Seq.empty).size / 4
      jyujyun += ueshitaGroup.get(Some(3)).getOrElse(Seq.empty).size / 4
      jyunshin += ueshitaGroup.get(Some(3)).getOrElse(Seq.empty).size / 4
      shihai += semeukeGroup.get(Some(1)).getOrElse(Seq.empty).size / 4
      oshi += semeukeGroup.get(Some(1)).getOrElse(Seq.empty).size / 4
      dasan += semeukeGroup.get(Some(2)).getOrElse(Seq.empty).size / 4
      sasshi += semeukeGroup.get(Some(2)).getOrElse(Seq.empty).size / 4
      jyujyun += semeukeGroup.get(Some(3)).getOrElse(Seq.empty).size / 4
      sasshi += semeukeGroup.get(Some(3)).getOrElse(Seq.empty).size / 4
      M.Talent.preset(shihai, jyujyun, dasan, jyunshin, oshi, sasshi, 0, 0)
    }
    def calculateTotalTalent(csT: Option[M.Talent], tyT: Option[M.Talent], rlT: M.Talent, parts: Map[Int, M.Part]): M.Talent = {
      val psTs = parts.values.map(_.talent).toSeq
      def calc(csT: Option[M.Talent], tyT: Option[M.Talent], rlT: M.Talent, psTs: Seq[M.Talent])(f: M.Talent => Option[Int]): Int = {
        csT.flatMap(f).getOrElse(0) + tyT.flatMap(f).getOrElse(0) + f(rlT).getOrElse(0) + psTs.foldLeft(0)(_ + f(_).getOrElse(0))
      }
      M.Talent.preset(
        shihai = calc(csT, tyT, rlT, psTs)(_.shihai),
        jyujyun = calc(csT, tyT, rlT, psTs)(_.jyujyun),
        dasan = calc(csT, tyT, rlT, psTs)(_.dasan),
        jyunshin = calc(csT, tyT, rlT, psTs)(_.jyunshin),
        oshi = calc(csT, tyT, rlT, psTs)(_.oshi),
        sasshi = calc(csT, tyT, rlT, psTs)(_.sasshi),
        koui = calc(csT, tyT, rlT, psTs)(_.koui),
        akui = calc(csT, tyT, rlT, psTs)(_.akui)
      )
    }
    val classTalentMap = Map(
      0 -> M.Talent.preset(3, 1, 1, 1, 1, 1, 1, 1),
      1 -> M.Talent.preset(3, 2, 2, 2, 1, 0, 0, 0),
      2 -> M.Talent.preset(2, 1, 3, 1, 0, 1, 1, 1),
      3 -> M.Talent.preset(2, 2, 1, 2, 0, 1, 1, 1),
      4 -> M.Talent.preset(1, 2, 1, 3, 1, 1, 1, 0),
      5 -> M.Talent.preset(1, 3, 2, 1, 0, 1, 1, 1),
      6 -> M.Talent.preset(1, 2, 2, 2, 0, 1, 1, 1),
      7 -> M.Talent.preset(1, 3, 2, 1, 1, 1, 0, 1),
      8 -> M.Talent.preset(3, 1, 1, 2, 1, 0, 1, 1),
      9 -> M.Talent.preset(1, 2, 2, 3, 1, 0, 1, 0),
      10 -> M.Talent.preset(2, 1, 3, 2, 1, 0, 0, 1)
    )
    val typeTalentMap = Map(
      0 -> M.Talent.preset(1, 0, 2, 0, 2, 1, 2, 1),
      1 -> M.Talent.preset(1, 1, 1, 1, 1, 2, 1, 1),
      2 -> M.Talent.preset(2, 0, 1, 0, 2, 1, 1, 2),
      3 -> M.Talent.preset(0, 2, 0, 1, 1, 1, 2, 2),
      4 -> M.Talent.preset(1, 1, 0, 0, 1, 2, 2, 2),
      5 -> M.Talent.preset(0, 1, 1, 1, 2, 2, 1, 1),
      6 -> M.Talent.preset(0, 1, 0, 2, 2, 1, 1, 2),
      7 -> M.Talent.preset(1, 0, 1, 1, 1, 2, 2, 1)
    )
    def render(p: Prop): ReactElement = {
      val classTalent = p.csClass.flatMap(c => classTalentMap.get(c))
      val typeTalent = p.csType.flatMap(c => typeTalentMap.get(c))
      val relTalent = calculateRelationTalent(p.relations)
      val totalTalent = calculateTotalTalent(classTalent, typeTalent, relTalent, p.parts)
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
                <.th("総計："),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("shihai" -> true),
                    ^.value := totalTalent.shihai.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyujyun" -> true),
                    ^.value := totalTalent.jyujyun.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("dasan" -> true),
                    ^.value := totalTalent.dasan.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyunshin" -> true),
                    ^.value := totalTalent.jyunshin.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("oshi" -> true),
                    ^.value := totalTalent.oshi.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("sasshi" -> true),
                    ^.value := totalTalent.sasshi.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("koui" -> true),
                    ^.value := totalTalent.koui.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("akui" -> true),
                    ^.value := totalTalent.akui.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  ^.classSet("noborder" -> true)
                )
              ),
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
                    ^.value := relTalent.shihai.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyujyun" -> true),
                    ^.value := relTalent.jyujyun.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("dasan" -> true),
                    ^.value := relTalent.dasan.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("jyunshin" -> true),
                    ^.value := relTalent.jyunshin.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("oshi" -> true),
                    ^.value := relTalent.oshi.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("sasshi" -> true),
                    ^.value := relTalent.sasshi.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("koui" -> true),
                    ^.value := relTalent.koui.map(_.toString).getOrElse(""),
                    ^.readOnly := true
                  )
                ),
                <.td(
                  <.input(
                    ^.`type` := "text",
                    ^.classSet("akui" -> true),
                    ^.value := relTalent.akui.map(_.toString).getOrElse(""),
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
              p.parts.toList.sortBy(_._1).map{ case (index, part) =>
                <.tr(
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.value := part.name,
                      ^.onChange ==> onChange(index, (p, v) => p.copy(name = v))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("shihai" -> true),
                      ^.value := part.talent.shihai.map(_.toString).getOrElse(""),
                      ^.onChange ==> onChange(index, (p, v) => p.copy(talent = p.talent.copy(shihai = util.toIntOpt(v))))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("jyujyun" -> true),
                      ^.value := part.talent.jyujyun.map(_.toString).getOrElse(""),
                      ^.onChange ==> onChange(index, (p, v) => p.copy(talent = p.talent.copy(jyujyun = util.toIntOpt(v))))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("dasan" -> true),
                      ^.value := part.talent.dasan.map(_.toString).getOrElse(""),
                      ^.onChange ==> onChange(index, (p, v) => p.copy(talent = p.talent.copy(dasan = util.toIntOpt(v))))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("jyunshin" -> true),
                      ^.value := part.talent.jyunshin.map(_.toString).getOrElse(""),
                      ^.onChange ==> onChange(index, (p, v) => p.copy(talent = p.talent.copy(jyunshin = util.toIntOpt(v))))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("oshi" -> true),
                      ^.value := part.talent.oshi.map(_.toString).getOrElse(""),
                      ^.onChange ==> onChange(index, (p, v) => p.copy(talent = p.talent.copy(oshi = util.toIntOpt(v))))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("sasshi" -> true),
                      ^.value := part.talent.sasshi.map(_.toString).getOrElse(""),
                      ^.onChange ==> onChange(index, (p, v) => p.copy(talent = p.talent.copy(sasshi = util.toIntOpt(v))))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("koui" -> true),
                      ^.value := part.talent.koui.map(_.toString).getOrElse(""),
                      ^.onChange ==> onChange(index, (p, v) => p.copy(talent = p.talent.copy(koui = util.toIntOpt(v))))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.classSet("akui" -> true),
                      ^.value := part.talent.akui.map(_.toString).getOrElse(""),
                      ^.onChange ==> onChange(index, (p, v) => p.copy(talent = p.talent.copy(akui = util.toIntOpt(v))))_
                    )
                  ),
                  <.td(
                    ^.classSet("noborder" -> true),
                    <.button(
                      ^.onClick --> deleteRow(index),
                      "削除"
                    )
                  )
                )
              }
            )
          ),
          <.button(
            ^.onClick --> addRow,
            "行追加"
          )
        )
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.App]) = {
    ReactComponentB[TalentTable.Prop]("TalentTable")
      .stateless
      .backend(scope => new TalentTable.Backend(scope, pScope))
      .renderBackend
      .build
  }
}

