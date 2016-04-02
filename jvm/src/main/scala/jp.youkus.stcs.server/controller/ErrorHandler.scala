package jp.youkus.stcs.server.controller

import org.scalatra.{InternalServerError, ScalatraServlet}

trait ErrorHandler { this: ScalatraServlet =>
  error {
    case e: Throwable => InternalServerError("")
  }
}
