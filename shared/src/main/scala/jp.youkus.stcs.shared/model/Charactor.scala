package jp.youkus.stcs.shared.model

import org.joda.time.DateTime
import scalikejdbc._
import java.security.{MessageDigest, SecureRandom}

case class Charactor(
                      id: String,
                      name: String,
                      csClass: Option[Int],
                      csType: Option[Int],
                      csEaude: Option[Int],
                      memo: String,
                      password: Option[String],
                      display: Boolean,
                      createDate: DateTime,
                      updateDate: DateTime,
                      deleteDate: Option[DateTime] = None
                    )

object Charactor extends SQLSyntaxSupport[Charactor] {
  override val tableName = "CHARACTOR"
  override val columns = Seq("ID", "NAME", "CS_CLASS", "CS_TYPE", "CS_EAUDE", "MEMO", "PASSWORD", "DISPLAY", "CREATE_DATE", "UPDATE_DATE", "DELETE_DATE")

  def apply(c: ResultName[Charactor])(rs: WrappedResultSet): Charactor = Charactor(
    id = rs.string(c.id),
    name = rs.string(c.name),
    csClass = rs.intOpt(c.csClass),
    csType = rs.intOpt(c.csType),
    csEaude = rs.intOpt(c.csEaude),
    memo = rs.string(c.memo),
    password = rs.stringOpt(c.password),
    display = rs.boolean(c.display),
    createDate = rs.jodaDateTime(c.createDate),
    updateDate = rs.jodaDateTime(c.updateDate),
    deleteDate = rs.jodaDateTimeOpt(c.deleteDate)
  )

  def find(id: String)(implicit session: DBSession): Option[Charactor] = {
    val c = Charactor.syntax("c")
    withSQL {
      select.from(Charactor as c).where.eq(c.id, id).and.isNull(c.deleteDate)
    }.map(Charactor(c.resultName)).single().apply()
  }

  def lists(limit: Int, offset: Int, tags: Option[Seq[String]])(implicit session: DBSession): (Int, List[Charactor]) = {
    val c = Charactor.syntax("c")
    val t = Tag.syntax("t")
    tags match {
      case None => {
        val count = withSQL {
          select[Int](sqls.count).from(Charactor as c)
            .where
            .eq(c.display, true)
            .and
            .isNull(c.deleteDate)
        }.map(rs => rs.int(1)).single.apply()
        val sheets = withSQL {
          select.from(Charactor as c)
            .where
            .eq(c.display, true)
            .and
            .isNull(c.deleteDate)
            .orderBy(c.updateDate).desc
            .limit(limit)
            .offset(offset)
        }.map(Charactor(c.resultName)).list().apply()
        (count.getOrElse(0), sheets)
      }
      case Some(ts) => {
        val count = withSQL {
          select[Int](sqls.count).from(Charactor as c)
            .innerJoin(Tag as t).on(c.id, t.cid)
            .where
            .eq(c.display, true)
            .and
            .isNull(c.deleteDate)
            .and
            .in(t.name, ts)
        }.map(rs => rs.int(1)).single.apply()
        println(count)
        val sheets = withSQL {
          select.from(Charactor as c)
            .innerJoin(Tag as t).on(c.id, t.cid)
            .where
            .eq(c.display, true)
            .and
            .isNull(c.deleteDate)
            .and
            .in(t.name, ts)
            .orderBy(c.updateDate).desc
            .limit(limit)
            .offset(offset)
        }.map(Charactor(c.resultName)).list().apply()
        (count.getOrElse(0), sheets)
      }
    }
  }

  def create(
              name: String,
              csClass: Option[Int],
              csType: Option[Int],
              csEaude: Option[Int],
              memo: String,
              display: Boolean,
              password: Option[String]
            )(implicit session: DBSession): Charactor = {
    val createDate = DateTime.now
    val id = java.util.UUID.randomUUID.toString
    val hashed = password.map(toHash)
    withSQL {
      insert.into(Charactor).columns(
        Charactor.column.id,
        Charactor.column.name,
        Charactor.column.csClass,
        Charactor.column.csType,
        Charactor.column.csEaude,
        Charactor.column.memo,
        Charactor.column.password,
        Charactor.column.display,
        Charactor.column.createDate,
        Charactor.column.updateDate
      ).values(
        id,
        name,
        csClass,
        csType,
        csEaude,
        memo,
        hashed,
        display,
        createDate,
        createDate
      )
    }.update.apply()
    Charactor(
      id = id,
      name = name,
      csClass = csClass,
      csType = csType,
      csEaude = csEaude,
      memo = memo,
      password = hashed,
      display = display,
      createDate = createDate,
      updateDate = createDate
    )
  }

  def updateDetail(
                    id: String,
                    name: String,
                    csClass: Option[Int],
                    csType: Option[Int],
                    csEaude: Option[Int],
                    memo: String,
                    display: Boolean,
                    password: Option[String]
                  )(implicit session: DBSession): Charactor = {
    withSQL {
      update(Charactor).set(
        Charactor.column.name -> name,
        Charactor.column.csClass -> csClass,
        Charactor.column.csType -> csType,
        Charactor.column.csEaude -> csEaude,
        Charactor.column.memo -> memo,
        Charactor.column.password -> password.map(toHash),
        Charactor.column.display -> display,
        Charactor.column.updateDate -> DateTime.now
      ).where.eq(Charactor.column.id, id)
    }.update.apply()
    find(id).get
  }

  def remove(id: String)(implicit session: DBSession): Boolean = {
    val count = withSQL {
      update(Charactor).set(
        Charactor.column.deleteDate -> DateTime.now
      ).where.eq(Charactor.column.id, id)
    }.update.apply()
    count > 0
  }

  def toHash(password: String): String = {
    val digest = MessageDigest.getInstance("SHA-512")
    digest.digest(password.getBytes).map(x => "%02x".format(x)).mkString
  }
}
