package jp.youkus.stcs.server.controller

import jp.youkus.stcs.shared.{json, model, util}
import org.scalatra.{ActionResult, BadRequest, NotFound, Ok, ScalatraServlet}
import scalikejdbc.DB
import upickle.default._

class Api extends ScalatraServlet with ErrorHandler {
  before() {
    contentType = "application/json"
  }
  after() {
    response.setHeader("Cache-Control", "no-cache")
  }
  post("/sheet") {
    val ret = for {
      sheet <- withoutError(read[json.request.Sheet](request.body)).right
    } yield {
      DB.localTx { implicit session =>
        val charactor = model.Charactor.create(
          name = sheet.name,
          csClass = sheet.csClass,
          csType = sheet.csType,
          csEaude = sheet.csEaude,
          memo = sheet.memo,
          display = sheet.display,
          password = sheet.password
        )
        val parts = sheet.parts.map{ case json.Sort(sort, p) =>
          val part = model.Part.create(
            cid = charactor.id,
            name = p.name,
            shihai = p.shihai,
            jyujyun = p.jyujyun,
            dasan = p.dasan,
            jyunshin = p.jyunshin,
            oshi = p.oshi,
            sasshi = p.sasshi,
            koui = p.koui,
            akui = p.akui,
            sort = sort,
            others = p.others
          )
          json.Sort(sort, json.Part(part))
        }
        val items = sheet.items.map{ case json.Sort(sort, i) =>
          val item = model.Item.create(
            cid = charactor.id,
            name = i.name,
            main = i.main,
            sub = i.sub,
            sort = sort
          )
          json.Sort(sort, json.Item(item))
        }
        val skills = sheet.skills.map{ case json.Sort(sort, s) =>
          val skill = model.Skill.create(
            cid = charactor.id,
            name = s.name,
            timing = s.timing,
            cost = s.cost,
            detail = s.detail,
            sort = sort
          )
          json.Sort(sort, json.Skill(skill))
        }
        val relations = sheet.relations.map{ case json.Sort(sort, r) =>
         val relation =  model.Relation.create(
            cid = charactor.id,
            target = r.to,
            name = r.name,
            ueshita = r.ueshita,
            semeuke = r.semeuke,
            sort = sort
          )
         json.Sort(sort, json.Relation(relation))
        }
        val tensions = sheet.tensions.map{ case json.Sort(sort, t) =>
          val tension = model.Tension.create(
            cid = charactor.id,
            num = t.number,
            status = t.status,
            sort = sort
          )
          json.Sort(sort, json.Tension(tension))
        }
        val tags = sheet.tags.map{ case json.Sort(sort, t) =>
          val tag = model.Tag.create(
            cid = charactor.id,
            name = t,
            sort = sort
          )
          json.Sort(sort, t)
        }
        Ok(write(json.response.Sheet(charactor, parts, items, skills, relations, tensions, tags)))
      }
    }
    ret.merge
  }
  get("/sheet/:id") {
    val ret = for {
      id <- required(params.get("id")).right
      charactor <- found(DB.readOnly { implicit s => model.Charactor.find(id) }).right
    } yield {
      DB.readOnly { implicit session =>
        val parts = model.Part.findByCid(id).map(x => json.Sort(x.sort, json.Part(x)))
        val items = model.Item.findByCid(id).map(x => json.Sort(x.sort, json.Item(x)))
        val skills = model.Skill.findByCid(id).map(x => json.Sort(x.sort, json.Skill(x)))
        val relations = model.Relation.findByCid(id).map(x => json.Sort(x.sort, json.Relation(x)))
        val tensions = model.Tension.findByCid(id).map(x => json.Sort(x.sort, json.Tension(x)))
        val tags = model.Tag.findByCid(id).map(x => json.Sort(x.sort, x.name))
        Ok(write(json.response.Sheet(charactor, parts, items, skills, relations, tensions, tags)))
      }
    }
    ret.merge
  }
  post("/sheet/output/txt") {
    contentType = "text/plane"
    println(request.body)
    val ret = for {
      jd <- required(params.get("q")).right
      sheet <- withoutError(read[json.request.Sheet](jd)).right
    } yield {
      Ok(toText(sheet))
    }
    ret.merge
  }
  def toText(sheet: json.request.Sheet): String = {
    val parts = sheet.parts.map(_.content)
    val items = sheet.items.map(_.content)
    val skills = sheet.skills.map(_.content)
    val relations = sheet.relations.map(_.content)
    val sb = new StringBuilder()
    sb.append(s"呼び名： ${sheet.name}\r\n")
    sb.append(s"タイプ： ${util.toClassName(sheet.csClass)}\r\n")
    sb.append(s"クラス： ${util.toTypeName(sheet.csType)}\r\n")
    sb.append(s"オーデ： ${util.toEaudeName(sheet.csEaude)}\r\n")
    sb.append("\r\n")
    sb.append("■資質\r\n")
    sb.append("支配 従順 打算 純真 押し 察し 好意 悪意\r\n")
    val total = model.Talent.calculateTotalTalent(sheet.csClass, sheet.csType, relations, parts)
    sb.append(s"${total.toString}\r\n")
    sb.append("\r\n")
    sb.append("■世界の部品\r\n")
    val partNameMax = math.max(parts.map(_.name.getBytes("Shift_JIS").size).max, 4)
    sb.append(s"${util.padding("名前", partNameMax)} 支配 従順 打算 純真 押し 察し 好意 悪意 効果\r\n")
    for (part <- parts) {
      sb.append(s"${part.toString(partNameMax)}\r\n")
    }
    sb.append("\r\n")
    sb.append("■アイテム\r\n")
    val itemNameMax = math.max(items.map(_.name.getBytes("Shift_JIS").size).max, 4)
    sb.append(s"${util.padding("名前", itemNameMax)} 主 副\r\n")
    for(item <- items) {
      sb.append(s"${item.toString(itemNameMax)}\r\n")
    }
    sb.append("\r\n")
    sb.append("■特技\r\n")
    val skillNameMax = math.max(skills.map(_.name.getBytes("Shift_JIS").size).max, 4)
    sb.append(s"${util.padding("名前", skillNameMax)} ﾀｲﾐﾝｸﾞ 重さ 内容\r\n")
    for(skill <- skills) {
      sb.append(s"${skill.toString(skillNameMax)}\r\n")
    }
    sb.append("\r\n")
    sb.append("■関係\r\n")
    val toNameMax = math.max(relations.map(_.to.getBytes("Shift_JIS").size).max, 4)
    val relNameMax = math.max(relations.map(_.name.getBytes("Shift_JIS").size).max, 6)
    sb.append(s"${util.padding("対象", toNameMax)} ${util.padding("関係名", relNameMax)} 上下 攻受\r\n")
    for(relation <- relations) {
      sb.append(s"${relation.toString(toNameMax, relNameMax)}\r\n")
    }
    sb.append("\r\n")
    sb.append("■メモ\r\n")
    sb.append(s"${sheet.memo}\r\n")
    sb.toString
  }
  post("/sheet/:id") {
    val ret = for {
      id <- required(params.get("id")).right
      sheet <- withoutError(read[json.request.Sheet](request.body)).right
      charactor <- found(DB.readOnly { implicit s => model.Charactor.find(id) }).right
      _ <- required(charactor.password == sheet.password.map(model.Charactor.toHash)).right
    } yield {
      DB.localTx { implicit session =>
        val updated = model.Charactor.updateDetail(
          id,
          name = sheet.name,
          csClass = sheet.csClass,
          csType = sheet.csType,
          csEaude = sheet.csEaude,
          memo = sheet.memo,
          display = sheet.display,
          password = sheet.password
        )
        model.Part.removeByCid(charactor.id)
        model.Item.removeByCid(charactor.id)
        model.Skill.removeByCid(charactor.id)
        model.Relation.removeByCid(charactor.id)
        model.Tension.removeByCid(charactor.id)
        model.Tag.removeByCid(charactor.id)
        val parts = sheet.parts.map{ case json.Sort(sort, p) =>
          val part = model.Part.create(
            cid = charactor.id,
            name = p.name,
            shihai = p.shihai,
            jyujyun = p.jyujyun,
            dasan = p.dasan,
            jyunshin = p.jyunshin,
            oshi = p.oshi,
            sasshi = p.sasshi,
            koui = p.koui,
            akui = p.akui,
            sort = sort,
            others = p.others
          )
          json.Sort(sort, json.Part(part))
        }
        val items = sheet.items.map{ case json.Sort(sort, i) =>
          val item = model.Item.create(
            cid = charactor.id,
            name = i.name,
            main = i.main,
            sub = i.sub,
            sort = sort
          )
          json.Sort(sort, json.Item(item))
        }
        val skills = sheet.skills.map{ case json.Sort(sort, s) =>
          val skill = model.Skill.create(
            cid = charactor.id,
            name = s.name,
            timing = s.timing,
            cost = s.cost,
            detail = s.detail,
            sort = sort
          )
          json.Sort(sort, json.Skill(skill))
        }
        val relations = sheet.relations.map{ case json.Sort(sort, r) =>
         val relation =  model.Relation.create(
            cid = charactor.id,
            target = r.to,
            name = r.name,
            ueshita = r.ueshita,
            semeuke = r.semeuke,
            sort = sort
          )
         json.Sort(sort, json.Relation(relation))
        }
        val tensions = sheet.tensions.map{ case json.Sort(sort, t) =>
          val tension = model.Tension.create(
            cid = charactor.id,
            num = t.number,
            status = t.status,
            sort = sort
          )
          json.Sort(sort, json.Tension(tension))
        }
        val tags = sheet.tags.map{ case json.Sort(sort, t) =>
          val tag = model.Tag.create(
            cid = charactor.id,
            name = t,
            sort = sort
          )
          json.Sort(sort, t)
        }
        Ok(write(json.response.Sheet(updated, parts, items, skills, relations, tensions, tags)))
      }
    }
    ret.merge
  }
  delete("/sheet/:id") {
    val ret = for {
      id <- required(params.get("id")).right
      p <- withoutError(read[json.Password](request.body)).right
      charactor <- found(DB.readOnly { implicit s => model.Charactor.find(id) }).right
      _ <- required(charactor.password == p.password.map(model.Charactor.toHash)).right
    } yield {
      DB.localTx { implicit session =>
        model.Part.removeByCid(charactor.id)
        model.Item.removeByCid(charactor.id)
        model.Skill.removeByCid(charactor.id)
        model.Relation.removeByCid(charactor.id)
        model.Tension.removeByCid(charactor.id)
        model.Tag.removeByCid(charactor.id)
        model.Charactor.remove(charactor.id)
      }
      Ok()
    }
    ret.merge
  }
  get("/lists") {
    val ret = for {
      jd <- required(params.get("q")).right
      search <- withoutError(read[json.Search](jd)).right
    } yield {
      DB.readOnly { implicit session =>
        val (count, results) = model.Charactor.lists(search.limit, search.offset, search.tags)
        val sheets = results.map { charactor =>
          val parts = model.Part.findByCid(charactor.id).map(x => json.Sort(x.sort, json.Part(x)))
          val items = model.Item.findByCid(charactor.id).map(x => json.Sort(x.sort, json.Item(x)))
          val skills = model.Skill.findByCid(charactor.id).map(x => json.Sort(x.sort, json.Skill(x)))
          val relations = model.Relation.findByCid(charactor.id).map(x => json.Sort(x.sort, json.Relation(x)))
          val tensions = model.Tension.findByCid(charactor.id).map(x => json.Sort(x.sort, json.Tension(x)))
          val tags = model.Tag.findByCid(charactor.id).map(x => json.Sort(x.sort, x.name))
          json.response.Sheet(charactor, parts, items, skills, relations, tensions, tags)
        }
        Ok(write(json.SearchResult(sheets, count)))
      }
    }
    ret.merge
  }
  def required(x: Boolean): Either[ActionResult, Unit] = if(x) { Right(()) } else { Left(BadRequest("")) }
  def required[T](x: Option[T]): Either[ActionResult, T] = x match {
    case None => Left(BadRequest(""))
    case Some(x) => Right(x)
  }
  def found[T](x: Option[T]): Either[ActionResult, T] = x match {
    case None => Left(NotFound(""))
    case Some(x) => Right(x)
  }
  def withoutError[T](x: => T):Either[ActionResult, T] = {
    try {
      Right(x)
    } catch {
      case _: Exception => Left(BadRequest(""))
    }
  }
}
