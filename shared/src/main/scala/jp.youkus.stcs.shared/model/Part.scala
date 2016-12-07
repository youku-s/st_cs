package jp.youkus.stcs.shared.model

import scalikejdbc._

case class Part(
  id: String,
  cid: String,
  name: String,
  shihai: Option[Int],
  jyujyun: Option[Int],
  dasan: Option[Int],
  jyunshin: Option[Int],
  oshi: Option[Int],
  sasshi: Option[Int],
  koui: Option[Int],
  akui: Option[Int],
  sort: Int
)
object Part extends SQLSyntaxSupport[Part] {
  override val tableName = "PART"
  override val columns = Seq("ID", "CID", "NAME", "SHIHAI", "JYUJYUN", "DASAN", "JYUNSHIN", "OSHI", "SASSHI", "KOUI", "AKUI", "SORT")
  def apply(p: ResultName[Part])(rs: WrappedResultSet): Part = Part(
    id = rs.string(p.id),
    cid = rs.string(p.cid),
    name = rs.string(p.name),
    shihai = rs.intOpt(p.shihai),
    jyujyun = rs.intOpt(p.jyujyun),
    dasan = rs.intOpt(p.dasan),
    jyunshin = rs.intOpt(p.jyunshin),
    oshi = rs.intOpt(p.oshi),
    sasshi = rs.intOpt(p.sasshi),
    koui = rs.intOpt(p.koui),
    akui = rs.intOpt(p.akui),
    sort = rs.int(p.sort)
  )
  def findByCid(cid: String)(implicit session: DBSession): List[Part] = {
    val p = Part.syntax("p")
    withSQL {
      select.from(Part as p).where.eq(p.cid, cid).orderBy(p.sort)
    }.map(Part(p.resultName)).list().apply()
  }
  def create(
    cid: String,
    name: String,
    shihai: Option[Int],
    jyujyun: Option[Int],
    dasan: Option[Int],
    jyunshin: Option[Int],
    oshi: Option[Int],
    sasshi: Option[Int],
    koui: Option[Int],
    akui: Option[Int],
    sort: Int
  )(implicit session: DBSession): Part = {
    val id = java.util.UUID.randomUUID.toString
    withSQL {
      insert.into(Part).columns(
        Part.column.id,
        Part.column.cid,
        Part.column.name,
        Part.column.shihai,
        Part.column.jyujyun,
        Part.column.dasan,
        Part.column.jyunshin,
        Part.column.oshi,
        Part.column.sasshi,
        Part.column.koui,
        Part.column.akui,
        Part.column.sort
      ).values(
        id,
        cid,
        name,
        shihai,
        jyujyun,
        dasan,
        jyunshin,
        oshi,
        sasshi,
        koui,
        akui,
        sort
      )
    }.update.apply()
    Part(
      id = id,
      cid = cid,
      name = name,
      shihai = shihai,
      jyujyun = jyujyun,
      dasan = dasan,
      jyunshin = jyunshin,
      oshi = oshi,
      sasshi = sasshi,
      koui = koui,
      akui = akui,
      sort = sort
    )
  }
  def removeByCid(cid: String)(implicit session: DBSession): Boolean = {
    val count = withSQL {
      delete.from(Part).where.eq(Part.column.cid, cid)
    }.update.apply()
    count > 0
  }
}
