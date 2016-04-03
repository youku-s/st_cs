package jp.youkus.stcs.shared.json

case class Search(
  limit: Int,
  offset: Int,
  tags: Option[Seq[String]]
)
