package jp.youkus.stcs.js

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport
object Main extends JSApp {
  def main(): Unit = {
  }

  @JSExport
  def renderSheet(id: String): Unit = {
    sheet.Main.render(id)
  }

  @JSExport
  def renderList(): Unit = {
    list.Main.main()
  }
}
