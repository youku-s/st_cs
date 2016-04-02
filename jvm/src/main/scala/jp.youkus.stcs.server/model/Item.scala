package jp.youkus.stcs.server.model

import scalikejdbc.{DBSession, ResultName, SQLSyntaxSupport, WrappedResultSet, insert, select, delete, withSQL}

case class Item(
  id: String,
  cid: String,
  name: String,
  main: Option[Int],
  sub: Option[Int],
  sort: Int
)
object Item extends SQLSyntaxSupport[Item] {
  override val tableName = "ITEM"
  override val columns = Seq("ID", "CID", "NAME", "MAIN", "SUB", "SORT")
  def apply(i: ResultName[Item])(rs: WrappedResultSet): Item = Item(
    id = rs.string(i.id),
    cid = rs.string(i.cid),
    name = rs.string(i.name),
    main = rs.intOpt(i.main),
    sub = rs.intOpt(i.sub),
    sort = rs.int(i.sort)
  )
  def findByCid(cid: String)(implicit session: DBSession): List[Item] = {
    val i = Item.syntax("i")
    withSQL {
      select.from(Item as i).where.eq(i.cid, cid).orderBy(i.sort)
    }.map(Item(i.resultName)).list().apply()
  }
  def create(
    cid: String,
    name: String,
    main: Option[Int],
    sub: Option[Int],
    sort: Int
  )(implicit session: DBSession): Item = {
    val id = java.util.UUID.randomUUID.toString
    withSQL {
      insert.into(Item).columns(
        Item.column.id,
        Item.column.cid,
        Item.column.name,
        Item.column.main,
        Item.column.sub,
        Item.column.sort
      ).values(
        id,
        cid,
        name,
        main,
        sub,
        sort
      )
    }.update.apply()
    Item(
      id = id,
      cid = cid,
      name = name,
      main = main,
      sub = sub,
      sort = sort
    )
  }
  def removeWithCid(cid: String)(implicit session: DBSession): Boolean = {
    val count = withSQL {
      delete.from(Item).where.eq(Item.column.cid, cid)
    }.update.apply()
    count > 0
  }
}
