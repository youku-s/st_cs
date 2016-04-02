package jp.youkus.stcs.shared.json

import jp.youkus.stcs.shared.model

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
    password = None,
    tags = tags,
    display = charactor.display
  )
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
)
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
)
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
)
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
)
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
