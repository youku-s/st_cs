import javax.servlet.ServletContext
import jp.youkus.stcs.server.controller.{Api, Lists, Sheet}
import org.scalatra.LifeCycle
import scalikejdbc.DB
import scalikejdbc.config.DBs

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
