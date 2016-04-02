package jp.youkus.stcs.shared.json

case class SearchResult[A](
  result: Seq[A],
  count: Int
)
