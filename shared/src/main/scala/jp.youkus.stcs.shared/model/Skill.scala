package jp.youkus.stcs.shared.model

import scalikejdbc.{DBSession, ResultName, SQLSyntaxSupport, WrappedResultSet, insert, select, delete, withSQL}

case class Skill(
  id: String,
  cid: String,
  name: String,
  timing: String,
  cost: Option[Int],
  detail: String,
  sort: Int
)
object Skill extends SQLSyntaxSupport[Skill] {
  override val tableName = "SKILL"
  override val columns = Seq("ID", "CID", "NAME", "TIMING", "COST", "DETAIL", "SORT")
  def apply(s: ResultName[Skill])(rs: WrappedResultSet): Skill = Skill(
    id = rs.string(s.id),
    cid = rs.string(s.cid),
    name = rs.string(s.name),
    timing = rs.string(s.timing),
    cost = rs.intOpt(s.cost),
    detail = rs.string(s.detail),
    sort = rs.int(s.sort)
  )
  def findByCid(cid: String)(implicit session: DBSession): List[Skill] = {
    val s = Skill.syntax("s")
    withSQL {
      select.from(Skill as s).where.eq(s.cid, cid).orderBy(s.sort)
    }.map(Skill(s.resultName)).list().apply()
  }
  def create(
    cid: String,
    name: String,
    timing: String,
    cost: Option[Int],
    detail: String,
    sort: Int
  )(implicit session: DBSession): Skill = {
    val id = java.util.UUID.randomUUID.toString
    withSQL {
      insert.into(Skill).columns(
        Skill.column.id,
        Skill.column.cid,
        Skill.column.name,
        Skill.column.timing,
        Skill.column.cost,
        Skill.column.detail,
        Skill.column.sort
      ).values(
        id,
        cid,
        name,
        timing,
        cost,
        detail,
        sort
      )
    }.update.apply()
    Skill(
      id = id,
      cid = cid,
      name = name,
      timing = timing,
      cost = cost,
      detail = detail,
      sort = sort
    )
  }
  def removeByCid(cid: String)(implicit session: DBSession): Boolean = {
    val count = withSQL {
      delete.from(Skill).where.eq(Skill.column.cid, cid)
    }.update.apply()
    count > 0
  }
}
