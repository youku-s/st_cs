package jp.youkus.stcs.server.controller

import org.json4s.jackson.JsonMethods
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.{Ok, ScalatraServlet}

class Api extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats
  before() {
    contentType = formats("json")
  }
  after() {
    response.setHeader("Cache-Control", "no-cache")
  }
  post("/sheet") {
    Ok()
  }
  get("/sheet/:id") {
    Ok()
  }
  post("/sheet/:id") {
    Ok()
  }
  delete("/sheet/:id") {
    Ok()
  }
  get("/sheets") {
    Ok()
  }
}
