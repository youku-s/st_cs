import org.scalatra.LifeCycle
import javax.servlet.ServletContext
import scalikejdbc.DB
import scalikejdbc.config.DBs

import jp.youkus.stcs.server.controller.{Api, Lists, Sheet}

class ScalatraBootstrap extends LifeCycle {
  DBs.setupAll()
  override def init(context: ServletContext) {
    context.mount(new Lists, "/lists/*")
    context.mount(new Sheet, "/sheet/*")
    context.mount(new Api, "/api/*")
  }
  override def destroy(context: ServletContext) {
    DBs.closeAll()
  }
}
