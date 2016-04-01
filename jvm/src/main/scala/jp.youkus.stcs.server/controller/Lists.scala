package jp.youkus.stcs.server.controller

import org.scalatra.{Ok, ScalatraServlet}
import org.scalatra.scalate.ScalateSupport

class Lists extends ScalatraServlet with ScalateSupport {
  get("/") {
    contentType="text/html"
    ssp("/lists", "title" -> "キャラクターシート一覧")
  }
}
