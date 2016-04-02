package jp.youkus.stcs.server.json

case class SearchResult[A](
  result: Seq[A],
  count: Int
)
