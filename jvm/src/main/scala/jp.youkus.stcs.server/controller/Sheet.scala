package jp.youkus.stcs.server.controller

import org.scalatra.{Ok, ScalatraServlet}
import org.scalatra.scalate.ScalateSupport

class Sheet extends ScalatraServlet with ScalateSupport {
  get("/") {
    contentType="text/html"
    ssp("/sheet", "title" -> "少女展爛会キャラクターシート")
  }
  get("/:id") {
    contentType="text/html"
    ssp("/sheet", "title" -> params.get("id").getOrElse(""))
  }
}
