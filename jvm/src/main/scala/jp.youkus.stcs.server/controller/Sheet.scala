package jp.youkus.stcs.server.controller

import jp.youkus.stcs.shared.model.Charactor
import org.scalatra.{Ok, ScalatraServlet}
import org.scalatra.scalate.ScalateSupport
import scalikejdbc.DB

class Sheet extends ScalatraServlet with ScalateSupport {
  get("/") {
    contentType="text/html"
    ssp("/sheet", "title" -> "少女展爛会キャラクターシート", "id" -> "")
  }
  get("/:id") {
    contentType="text/html"
    val (title, id) = params.get("id")
      .flatMap(id => DB.readOnly { implicit s => Charactor.find(id) })
      .map(c => (s"${c.name} - 少女展爛会キャラクターシート", c.id)).getOrElse(("少女展爛会キャラクターシート", ""))
    ssp("/sheet", "title" -> title, "id" -> id)
  }
}
