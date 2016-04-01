package jp.youkus.stcs.js.sheet.component

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.js.sheet.{model => M, util}

object SkillTable {
  case class Prop(
    skills: Map[Int, M.Skill]
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, M.App]) {
    def onChange(index: Int, f: (M.Skill, String) => M.Skill)(e: ReactEventI): Callback = {
      pScope.modState(s =>
        s.copy(
          skills = s.skills.get(index) match {
            case Some(p) => s.skills + (index -> f(p, e.target.value))
            case None => s.skills
          }
        )
      )
    }
    def render(p: Prop): ReactElement = {
      <.div(
        ^.classSet("box" -> true),
        <.h2("特技"),
        <.div(
          <.table(
            ^.classSet("skill" -> true),
            <.thead(
              <.tr(
                <.th(
                  ^.style := js.Dictionary("width" -> "20%"),
                  "名前"
                ),
                <.th(
                  ^.style := js.Dictionary("width" -> "10%"),
                  "ﾀｲﾐﾝｸﾞ"
                ),
                <.th(
                  ^.style := js.Dictionary("width" -> "10%"),
                  "重さ"
                ),
                <.th(
                  ^.style := js.Dictionary("width" -> "60%"),
                  "内容"
                )
              )
            ),
            <.tbody(
              p.skills.toList.sortBy(_._1).map{ case (index, skill) =>
                <.tr(
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.value := skill.name,
                      ^.onChange ==> onChange(index, (p, v) => p.copy(name = v))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.value := skill.timing,
                      ^.onChange ==> onChange(index, (p, v) => p.copy(timing = v))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.value := skill.cost.map(_.toString).getOrElse(""),
                      ^.onChange ==> onChange(index, (p, v) => p.copy(cost = util.toIntOpt(v)))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.value := skill.detail,
                      ^.onChange ==> onChange(index, (p, v) => p.copy(detail = v))_
                    )
                  )
                )
              }
            )
          )
        )
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.App]) = {
    ReactComponentB[SkillTable.Prop]("SkillTable")
      .stateless
      .backend(scope => new SkillTable.Backend(scope, pScope))
      .renderBackend
      .build
  }
}
