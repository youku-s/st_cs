package jp.youkus.stcs.js.sheet.model

case class App(
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
  tags: Map[Int, String]
)
case class Part(
  name: String,
  talent: Talent
)
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
case class Skill(
  name: String,
  timing: String,
  cost: Option[Int],
  detail: String
)
case class Relation(
  to: String,
  name: String,
  ueshita: Option[Int],
  semeuke: Option[Int]
)
case class Tension(
  number: Option[Int],
  status: Int
)

object Tension {
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

object App {
  def initialValue: App = App(
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
    tags = Map.empty
  )
}
