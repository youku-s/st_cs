package jp.youkus.stcs.server.controller

import org.scalatra.{Ok, ScalatraServlet}
import org.scalatra.scalate.ScalateSupport

class Lists extends ScalatraServlet with ScalateSupport {
  get("/") {
    contentType="text/html"
    ssp("/lists", "layout" -> "", "title" -> "少女展爛会キャラクターシート一覧(β版)")
  }
}
