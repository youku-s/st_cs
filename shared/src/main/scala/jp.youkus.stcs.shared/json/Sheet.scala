package jp.youkus.stcs.shared.json

import jp.youkus.stcs.shared.{model, util}
import org.joda.time.format.DateTimeFormat

object request {
  case class Sheet(
    id: Option[String],
    name: String,
    csClass: Option[Int],
    csType: Option[Int],
    csEaude: Option[Int],
    parts: Seq[Sort[Part]],
    items: Seq[Sort[Item]],
    skills: Seq[Sort[Skill]],
    relations: Seq[Sort[Relation]],
    tensions: Seq[Sort[Tension]],
    memo: String,
    password: Option[String],
    tags: Seq[Sort[String]],
    display: Boolean
  )
}
object response {
  case class Sheet(
    id: Option[String],
    name: String,
    csClass: Option[Int],
    csType: Option[Int],
    csEaude: Option[Int],
    parts: Seq[Sort[Part]],
    items: Seq[Sort[Item]],
    skills: Seq[Sort[Skill]],
    relations: Seq[Sort[Relation]],
    tensions: Seq[Sort[Tension]],
    memo: String,
    usePassword: Boolean,
    tags: Seq[Sort[String]],
    display: Boolean,
    updateDate: String
  )
  object Sheet {
    def apply(
      charactor: model.Charactor,
      parts: Seq[Sort[Part]],
      items: Seq[Sort[Item]],
      skills: Seq[Sort[Skill]],
      relations: Seq[Sort[Relation]],
      tensions: Seq[Sort[Tension]],
      tags: Seq[Sort[String]]
    ): Sheet = Sheet(
      id = Some(charactor.id),
      name = charactor.name,
      csClass = charactor.csClass,
      csType = charactor.csType,
      csEaude = charactor.csEaude,
      parts = parts,
      items = items,
      skills = skills,
      relations = relations,
      tensions = tensions,
      memo = charactor.memo,
      usePassword = charactor.password.isDefined,
      tags = tags,
      display = charactor.display,
      updateDate = charactor.updateDate.map(_.toString(DateTimeFormat.mediumDateTime())).getOrElse("-")
    )
  }
}
case class Part(
  name: String,
  shihai: Option[Int],
  jyujyun: Option[Int],
  dasan: Option[Int],
  jyunshin: Option[Int],
  oshi: Option[Int],
  sasshi: Option[Int],
  koui: Option[Int],
  akui: Option[Int]
) {
  def toString(max: Int): String = {
    s"${util.padding(name, max)}\t${strip(shihai)}\t${strip(jyujyun)}\t${strip(dasan)}\t${strip(jyunshin)}\t${strip(oshi)}\t${strip(sasshi)}\t${strip(koui)}\t${strip(akui)}"
  }
  def strip(opt: Option[Int]): String = {
    opt.map(_.toString).getOrElse("-")
  }
}
object Part {
  def apply(part: model.Part): Part = Part(
    name = part.name,
    shihai = part.shihai,
    jyujyun = part.jyujyun,
    dasan = part.dasan,
    jyunshin = part.jyunshin,
    oshi = part.oshi,
    sasshi = part.sasshi,
    koui = part.koui,
    akui = part.akui
  )
}
case class Item(
  name: String,
  main: Option[Int],
  sub: Option[Int]
) {
  def toString(max: Int): String = {
    s"${util.padding(name, max)}\t${mainName}\t${subName}"
  }
  val mainTable = Map(
    0 -> "支",
    1 -> "従",
    2 -> "打",
    3 -> "純"
  )
  val subTable = Map(
    0 -> "支",
    1 -> "従",
    2 -> "打",
    3 -> "純",
    4 -> "押",
    5 -> "察"
  )
  def mainName: String = {
    main.flatMap(mainTable.get).getOrElse("-")
  }
  def subName: String = {
    sub.flatMap(subTable.get).getOrElse("-")
  }
}
object Item {
  def apply(item: model.Item): Item = Item(
    name = item.name,
    main = item.main,
    sub = item.sub
  )
}
case class Skill(
  name: String,
  timing: String,
  cost: Option[Int],
  detail: String
) {
  def toString(max: Int): String = {
    s"${util.padding(name, max)}\t${timing}\t${cost.map(_.toString).getOrElse("-")}\t${detail}"
  }
}
object Skill {
  def apply(skill: model.Skill): Skill = Skill(
    name = skill.name,
    timing = skill.timing,
    cost = skill.cost,
    detail = skill.detail
  )
}
case class Relation(
  to: String,
  name: String,
  ueshita: Option[Int],
  semeuke: Option[Int]
) {
  def toString(max: Int): String = {
    s"${util.padding(to, max)}\t${name}\t${ueshitaName}\t${semeukeName}"
  }
  val ueshitaTable = Map(
    0 -> "無",
    1 -> "上",
    2 -> "同",
    3 -> "下"
  )
  val semeukeTable = Map(
    0 -> "無",
    1 -> "攻",
    2 -> "等",
    3 -> "受"
  )
  def ueshitaName: String = {
    ueshita.flatMap(ueshitaTable.get).getOrElse("-")
  }
  def semeukeName: String = {
    semeuke.flatMap(semeukeTable.get).getOrElse("-")
  }
}
object Relation {
  def apply(relation: model.Relation): Relation = Relation(
    to = relation.target,
    name = relation.name,
    ueshita = relation.ueshita,
    semeuke = relation.semeuke
  )
}
case class Tension(
  number: Option[Int],
  status: Int
)
object Tension {
  def apply(tension: model.Tension): Tension = Tension(
    number = tension.num,
    status = tension.status
  )
}
case class Sort[T](
  sort: Int,
  content: T
)
