package jp.youkus.stcs.shared.model

import scalikejdbc._

case class Tension(
  id: String,
  cid: String,
  num: Option[Int],
  status: Int,
  sort: Int
)
object Tension extends SQLSyntaxSupport[Tension] {
  override val tableName = "TENSION"
  override val columns = Seq("ID", "CID", "NUM", "STATUS", "SORT")
  def apply(t: ResultName[Tension])(rs: WrappedResultSet): Tension = Tension(
    id = rs.string(t.id),
    cid = rs.string(t.cid),
    num = rs.intOpt(t.num),
    status = rs.int(t.status),
    sort = rs.int(t.sort)
  )
  def findByCid(cid: String)(implicit session: DBSession): List[Tension] = {
    val t = Tension.syntax("t")
    withSQL {
      select.from(Tension as t).where.eq(t.cid, cid).orderBy(t.sort)
    }.map(Tension(t.resultName)).list().apply()
  }
  def create(
    cid: String,
    num: Option[Int],
    status: Int,
    sort: Int
  )(implicit session: DBSession): Tension = {
    val id = java.util.UUID.randomUUID.toString
    withSQL {
      insert.into(Tension).columns(
        Tension.column.id,
        Tension.column.cid,
        Tension.column.num,
        Tension.column.status,
        Tension.column.sort
      ).values(
        id,
        cid,
        num,
        status,
        sort
      )
    }.update.apply()
    Tension(
      id = id,
      cid = cid,
      num = num,
      status = status,
      sort = sort
    )
  }
  def removeByCid(cid: String)(implicit session: DBSession): Boolean = {
    val count = withSQL {
      delete.from(Tension).where.eq(Tension.column.cid, cid)
    }.update.apply()
    count > 0
  }
}
