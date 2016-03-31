package jp.youkus.stcs.js.sheet

object util {
  def toIntOpt(x: String): Option[Int] = try {
    Some(x.toInt)
  } catch {
    case e: Exception => None
  }
}
