package jp.youkus.stcs.server.model

import scalikejdbc.{DBSession, ResultName, SQLSyntaxSupport, WrappedResultSet, insert, select, delete, withSQL}

case class Relation(
  id: String,
  cid: String,
  target: String,
  name: String,
  ueshita: Option[Int],
  semeuke: Option[Int],
  sort: Int
)
object Relation extends SQLSyntaxSupport[Relation] {
  override val tableName = "RELATION"
  override val columns = Seq("ID", "CID", "TARGET", "NAME", "UESHITA", "SEMEUKE", "SORT")
  def apply(r: ResultName[Relation])(rs: WrappedResultSet): Relation = Relation(
    id = rs.string(r.id),
    cid = rs.string(r.cid),
    target = rs.string(r.target),
    name = rs.string(r.name),
    ueshita = rs.intOpt(r.ueshita),
    semeuke = rs.intOpt(r.semeuke),
    sort = rs.int(r.sort)
  )
  def findByCid(cid: String)(implicit session: DBSession): List[Relation] = {
    val r = Relation.syntax("r")
    withSQL {
      select.from(Relation as r).where.eq(r.cid, cid)
    }.map(Relation(r.resultName)).list().apply()
  }
  def create(
    cid: String,
    target: String,
    name: String,
    ueshita: Option[Int],
    semeuke: Option[Int],
    sort: Int
  )(implicit session: DBSession): Relation = {
    val id = java.util.UUID.randomUUID.toString
    withSQL {
      insert.into(Relation).columns(
        Relation.column.id,
        Relation.column.cid,
        Relation.column.target,
        Relation.column.name,
        Relation.column.ueshita,
        Relation.column.semeuke,
        Relation.column.sort
      ).values(
        id,
        cid,
        target,
        name,
        ueshita,
        semeuke,
        sort
      )
    }.update.apply()
    Relation(
      id = id,
      cid = cid,
      target = target,
      name = name,
      ueshita = ueshita,
      semeuke = semeuke,
      sort = sort
    )
  }
  def removeByCid(cid: String)(implicit session: DBSession): Boolean = {
    val count = withSQL {
      delete.from(Relation).where.eq(Relation.column.cid, cid)
    }.update.apply()
    count > 0
  }
}
