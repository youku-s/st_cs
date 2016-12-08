package jp.youkus.stcs.shared.model

import jp.youkus.stcs.shared.{json, util}

case class Talent(
  shihai: Option[Int],
  jyujyun: Option[Int],
  dasan: Option[Int],
  jyunshin: Option[Int],
  oshi: Option[Int],
  sasshi: Option[Int],
  koui: Option[Int],
  akui: Option[Int]
) {
  override def toString(): String = {
    s"${strip(shihai)} ${strip(jyujyun)} ${strip(dasan)} ${strip(jyunshin)} ${strip(oshi)} ${strip(sasshi)} ${strip(koui)} ${strip(akui)}"
  }
  def strip(opt: Option[Int]): String = {
    util.padding(opt.map(_.toString).getOrElse("-"), 4)
  }
}
object Talent {
  def preset(shihai: Int, jyujyun: Int, dasan: Int, jyunshin: Int, oshi: Int, sasshi: Int, koui: Int, akui: Int): Talent = Talent(
    Option(shihai),
    Option(jyujyun),
    Option(dasan),
    Option(jyunshin),
    Option(oshi),
    Option(sasshi),
    Option(koui),
    Option(akui)
  )
  def calculateRelationTalent(relations: Seq[json.Relation]): Talent = {
    var shihai = 0
    var jyunshin = 0
    var dasan = 0
    var jyujyun = 0
    var oshi = 0
    var sasshi = 0
    val ueshitaGroup = relations.groupBy(x => x.ueshita)
    val semeukeGroup = relations.groupBy(x => x.semeuke)
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
    Talent.preset(shihai, jyujyun, dasan, jyunshin, oshi, sasshi, 0, 0)
  }
  def calculateTotalTalent(csClass: Option[Int], csType: Option[Int], relations: Seq[json.Relation], parts: Seq[json.Part]): Talent = {
    val csT = csClass.flatMap(classTalentMap.get)
    val tyT = csType.flatMap(typeTalentMap.get)
    val rlT = calculateRelationTalent(relations)
    val pts = parts.map(x => Talent(x.shihai, x.jyujyun, x.dasan, x.jyunshin, x.oshi, x.sasshi, x.koui, x.akui))
    def calc(csT: Option[Talent], tyT: Option[Talent], rlT: Talent, pts: Seq[Talent])(f: Talent => Option[Int]): Int = {
      csT.flatMap(f).getOrElse(0) + tyT.flatMap(f).getOrElse(0) + f(rlT).getOrElse(0) + pts.foldLeft(0)(_ + f(_).getOrElse(0))
    }
    Talent.preset(
      shihai = calc(csT, tyT, rlT, pts)(_.shihai),
      jyujyun = calc(csT, tyT, rlT, pts)(_.jyujyun),
      dasan = calc(csT, tyT, rlT, pts)(_.dasan),
      jyunshin = calc(csT, tyT, rlT, pts)(_.jyunshin),
      oshi = calc(csT, tyT, rlT, pts)(_.oshi),
      sasshi = calc(csT, tyT, rlT, pts)(_.sasshi),
      koui = calc(csT, tyT, rlT, pts)(_.koui),
      akui = calc(csT, tyT, rlT, pts)(_.akui)
    )
  }
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
}
