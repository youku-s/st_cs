package jp.youkus.stcs.shared

object util {
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
  def padding(str: String, max: Int): String = {
    val length = str.getBytes("Shift_JIS").size
    str + " " * (max - length)
  }
}
