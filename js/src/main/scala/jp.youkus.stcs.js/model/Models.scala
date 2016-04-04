package jp.youkus.stcs.js.model

import jp.youkus.stcs.shared.json

case class Sheet(
  id: Option[String],
  name: String,
  csClass: Option[Int],
  csType: Option[Int],
  csEaude: Option[Int],
  parts: Map[Int, Part],
  items: Map[Int, Item],
  skills: Map[Int, Skill],
  relations: Map[Int, Relation],
  tensions: Map[Int, Tension],
  memo: String,
  password: Option[String],
  tags: Map[Int, String],
  display: Boolean,
  updateDate: String,
  usePassword: Boolean,
  notification: Option[Notification]
)
case class Part(
  name: String,
  talent: Talent
)
object Part {
  def apply(part: json.Part): Part = Part(
    name = part.name,
    talent = Talent(
      shihai = part.shihai,
      jyujyun = part.jyujyun,
      dasan = part.dasan,
      jyunshin = part.jyunshin,
      oshi = part.oshi,
      sasshi = part.sasshi,
      koui = part.koui,
      akui = part.akui
    )
  )
  def toJson(part: Part): json.Part = json.Part(
    name = part.name,
    shihai = part.talent.shihai,
    jyujyun = part.talent.jyujyun,
    dasan = part.talent.dasan,
    jyunshin = part.talent.jyunshin,
    oshi = part.talent.oshi,
    sasshi = part.talent.sasshi,
    koui = part.talent.koui,
    akui = part.talent.akui
  )
}
case class Talent(
  shihai: Option[Int],
  jyujyun: Option[Int],
  dasan: Option[Int],
  jyunshin: Option[Int],
  oshi: Option[Int],
  sasshi: Option[Int],
  koui: Option[Int],
  akui: Option[Int]
)
object Talent {
  def default: Talent = Talent(None, None, None, None, None, None, None, None)
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
}
case class Item(
  name: String,
  main: Option[Int],
  sub: Option[Int]
)
object Item {
  def apply(item: json.Item): Item = Item(
    name = item.name,
    main = item.main,
    sub = item.sub
  )
  def toJson(item: Item): json.Item = json.Item(
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
  def apply(skill: json.Skill): Skill = Skill(
    name = skill.name,
    timing = skill.timing,
    cost = skill.cost,
    detail = skill.detail
  )
  def toJson(skill: Skill): json.Skill = json.Skill(
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
  def apply(relation: json.Relation): Relation = Relation(
    to = relation.to,
    name = relation.name,
    ueshita = relation.ueshita,
    semeuke = relation.semeuke
  )
  def toJson(relation: Relation): json.Relation = json.Relation(
    to = relation.to,
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
  def apply(tension: json.Tension): Tension = Tension(
    number = tension.number,
    status = tension.status
  )
  def toJson(tension: Tension): json.Tension = json.Tension(
    number = tension.number,
    status = tension.status
  )
  def initial = Map(
    0 -> Tension(None, 0),
    1 -> Tension(None, 0),
    2 -> Tension(None, 0),
    3 -> Tension(None, 0),
    4 -> Tension(Some(5), 0),
    5 -> Tension(None, 0),
    6 -> Tension(None, 0),
    7 -> Tension(None, 0),
    8 -> Tension(None, 0),
    9 -> Tension(Some(10), 0),
    10 -> Tension(None, 0),
    11 -> Tension(None, 0),
    12 -> Tension(None, 0),
    13 -> Tension(None, 0),
    14 -> Tension(Some(15), 0),
    15 -> Tension(None, 0),
    16 -> Tension(None, 0),
    17 -> Tension(None, 0),
    18 -> Tension(None, 0),
    19 -> Tension(Some(20), 0)
  )
}
sealed trait Notification
case class Normal(message: String) extends Notification
case class Error(message: String) extends Notification
object Sheet {
  def initialValue: Sheet = Sheet(
    id = None,
    name = "",
    csClass = None,
    csType = None,
    csEaude = None,
    parts = Map(0 -> Part("", Talent.default)),
    items = Map(0 -> Item("", None, None)),
    skills = Map(
      0 -> Skill("", "", None, ""),
      1 -> Skill("", "", None, ""),
      2 -> Skill("", "", None, ""),
      3 -> Skill("", "", None, "")
    ),
    relations = Map(0 -> Relation("", "", None, None)),
    tensions = Tension.initial,
    memo = "",
    password = None,
    tags = Map.empty,
    display = true,
    usePassword = false,
    updateDate = "",
    notification = None
  )
  def fromJson(sheet: json.response.Sheet): Sheet = Sheet(
    id = sheet.id,
    name = sheet.name,
    csClass = sheet.csClass,
    csType = sheet.csType,
    csEaude = sheet.csEaude,
    parts = sheet.parts.map{ case json.Sort(sort, part) => sort -> Part(part) }.toMap,
    items = sheet.items.map{ case json.Sort(sort, item) => sort -> Item(item) }.toMap,
    skills = sheet.skills.map{ case json.Sort(sort, skill) => sort -> Skill(skill) }.toMap,
    relations = sheet.relations.map{ case json.Sort(sort, relation) => sort -> Relation(relation) }.toMap,
    tensions = sheet.tensions.map{ case json.Sort(sort, tension) => sort -> Tension(tension) }.toMap,
    memo = sheet.memo,
    password = None,
    usePassword = sheet.usePassword,
    tags = sheet.tags.map{ case json.Sort(sort, tag) => sort -> tag }.toMap,
    display = sheet.display,
    updateDate = sheet.updateDate,
    notification = None
  )
  def toJson(sheet: Sheet): json.request.Sheet = json.request.Sheet(
    id = sheet.id,
    name = sheet.name,
    csClass = sheet.csClass,
    csType = sheet.csType,
    csEaude = sheet.csEaude,
    parts = sheet.parts.toList.map { case (sort, part) => json.Sort(sort, Part.toJson(part)) },
    items = sheet.items.toList.map { case (sort, item) => json.Sort(sort, Item.toJson(item)) },
    skills = sheet.skills.toList.map { case (sort, skill) => json.Sort(sort, Skill.toJson(skill)) },
    relations = sheet.relations.toList.map { case (sort, relation) => json.Sort(sort, Relation.toJson(relation)) },
    tensions = sheet.tensions.toList.map { case (sort, tension) => json.Sort(sort, Tension.toJson(tension)) },
    memo = sheet.memo,
    password = sheet.password,
    tags = sheet.tags.toList.map { case (sort, tag) => json.Sort(sort, tag) },
    display = sheet.display
  )
}
