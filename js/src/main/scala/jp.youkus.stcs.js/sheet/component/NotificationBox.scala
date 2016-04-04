package jp.youkus.stcs.js.sheet.component

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react.vdom.EmptyTag
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}
import jp.youkus.stcs.js.{model => M}

object NotificationBox {
  case class Prop(
    notification: Option[M.Notification]
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, M.Sheet]) {
    def onClick(): Callback = {
      pScope.modState(s => s.copy(notification = None))
    }
    def render(p: Prop) = {
      <.div(
        ^.classSet("notification" -> true),
        p.notification match {
          case None => EmptyTag
          case Some(M.Normal(message)) => {
            <.div(
              ^.classSet("success" -> true),
              <.span(
                ^.classSet("ion-close-circled" -> true),
                ^.onClick --> onClick
              ),
              " ",
              message
            )
          }
          case Some(M.Error(message)) => {
            <.div(
              ^.classSet("error" -> true),
              <.span(
                ^.classSet("ion-close-circled" -> true),
                ^.onClick --> onClick
              ),
              " ",
              message
            )
          }
        }
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.Sheet]) = {
    ReactComponentB[Prop]("NotificationBox")
      .stateless
      .backend(scope => new NotificationBox.Backend(scope, pScope))
      .renderBackend
      .build
  }
}
