package jp.youkus.stcs.server.controller

import jp.youkus.stcs.shared.model.Charactor
import org.scalatra.{Ok, ScalatraServlet}
import org.scalatra.scalate.ScalateSupport
import scalikejdbc.DB

class Sheet extends ScalatraServlet with ScalateSupport {
  get("/") {
    contentType="text/html"
    ssp("/sheet", "layout" -> "", "title" -> "少女展爛会キャラクターシート(β版)", "id" -> "")
  }
  get("/:id") {
    contentType="text/html"
    val (title, id) = params.get("id")
      .flatMap(id => DB.readOnly { implicit s => Charactor.find(id) })
      .map(c => (s"${c.name} - 少女展爛会キャラクターシート(β版)", c.id)).getOrElse(("少女展爛会キャラクターシート(β版)", ""))
    ssp("/sheet", "layout" -> "", "title" -> title, "id" -> id)
  }
}
