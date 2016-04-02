package jp.youkus.stcs.shared.model

import scalikejdbc.{DBSession, ResultName, SQLSyntaxSupport, WrappedResultSet, insert, select, delete, withSQL}

case class Tag(
  id: String,
  cid: String,
  name: String,
  sort: Int
)
object Tag extends SQLSyntaxSupport[Tag] {
  override val tableName = "TAG"
  override val columns = Seq("ID", "CID", "NAME", "SORT")
  def apply(t: ResultName[Tag])(rs: WrappedResultSet): Tag = Tag(
    id = rs.string(t.id),
    cid = rs.string(t.cid),
    name = rs.string(t.name),
    sort = rs.int(t.sort)
  )
  def findByCid(cid: String)(implicit session: DBSession): List[Tag] = {
    val t = Tag.syntax("t")
    withSQL {
      select.from(Tag as t).where.eq(t.cid, cid)
    }.map(Tag(t.resultName)).list().apply()
  }
  def create(
    cid: String,
    name: String,
    sort: Int
  )(implicit session: DBSession): Tag = {
    val id = java.util.UUID.randomUUID.toString
    withSQL {
      insert.into(Tag).columns(
        Tag.column.id,
        Tag.column.cid,
        Tag.column.name,
        Tag.column.sort
      ).values(
        id,
        cid,
        name,
        sort
      )
    }.update.apply()
    Tag(
      id = id,
      cid = cid,
      name = name,
      sort = sort
    )
  }
  def removeByCid(cid: String)(implicit session: DBSession): Boolean = {
    val count = withSQL {
      delete.from(Tag).where.eq(Tag.column.cid, cid)
    }.update.apply()
    count > 0
  }
}
