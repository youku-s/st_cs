package jp.youkus.stcs.server.controller

import org.scalatra.{Ok, ScalatraServlet}
import org.scalatra.scalate.ScalateSupport

import scalikejdbc.DB

import jp.youkus.stcs.server.model.Charactor

class Sheet extends ScalatraServlet with ScalateSupport {
  get("/") {
    contentType="text/html"
    ssp("/sheet", "title" -> "少女展爛会キャラクターシート", "id" -> "")
  }
  get("/:id") {
    contentType="text/html"
    val (title, id) = params.get("id")
      .flatMap(id => DB.readOnly { implicit s => Charactor.find(id) })
      .map(c => (c.name, c.id)).getOrElse(("少女展爛会キャラクターシート", ""))
    ssp("/sheet", "title" -> title, "id" -> id)
  }
}
