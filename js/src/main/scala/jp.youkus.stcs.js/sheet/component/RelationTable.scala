package jp.youkus.stcs.js.sheet.component

import scala.scalajs.js

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement, ReactEventI}
import japgolly.scalajs.react.vdom.Implicits._
import japgolly.scalajs.react.vdom.prefix_<^.{<, ^}

import jp.youkus.stcs.js.sheet.util
import jp.youkus.stcs.js.{model => M}

object RelationTable {
  case class Prop(
    relations: Map[Int, M.Relation]
  )
  class Backend(scope: BackendScope[Prop, Unit], pScope: BackendScope[Unit, M.Sheet]) {
    def onChange(index: Int, f: (M.Relation, String) => M.Relation)(e: ReactEventI): Callback = {
      pScope.modState(s => 
        s.copy(
          relations = s.relations.get(index) match {
            case Some(p) => s.relations + (index -> f(p, e.target.value))
            case None => s.relations
          }
        )
      )
    }
    def deleteRow(index: Int): Callback = {
      pScope.modState(s =>
        s.copy(
          relations = s.relations.get(index) match {
            case Some(p) => s.relations - index
            case None => s.relations
          }
        )
      )
    }
    def addRow(): Callback = {
      pScope.modState(s =>
        s.copy(
          relations = {
            val next = if (s.relations.isEmpty) 0 else s.relations.keys.max + 1
            s.relations + (next -> M.Relation("", "", None, None))
          }
        )
      )
    }
    def render(p: Prop): ReactElement = {
      <.div(
        ^.classSet("box" -> true),
        <.h2("関係"),
        <.div(
          <.table(
            ^.classSet("relation" -> true),
            <.thead(
              <.tr(
                <.th(
                  ^.style := js.Dictionary("width" -> "25%"),
                  "対象"
                ),
                <.th(
                  ^.style := js.Dictionary("width" -> "25%"),
                  "関係名"
                ),
                <.th(
                  ^.style := js.Dictionary("width" -> "15%"),
                  "上下"
                ),
                <.th(
                  ^.style := js.Dictionary("width" -> "15%"),
                  "攻受"
                ),
                <.th(
                  ^.classSet("noborder" -> true),
                  ^.style := js.Dictionary("width" -> "20%")
                )
              )
            ),
            <.tbody(
              p.relations.toList.sortBy(_._1).map{ case (index, relation) =>
                <.tr(
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.value := relation.to,
                      ^.onChange ==> onChange(index, (p, v) => p.copy(to = v))_
                    )
                  ),
                  <.td(
                    <.input(
                      ^.`type` := "text",
                      ^.value := relation.name,
                      ^.onChange ==> onChange(index, (p, v) => p.copy(name = v))_
                    )
                  ),
                  <.td(
                    <.select(
                      ^.value := relation.ueshita.getOrElse(-1),
                      ^.onChange ==> onChange(index, (i, v) => i.copy(ueshita = util.toIntOpt(v)))_,
                      <.option(^.value := "-1", "-"),
                      <.option(^.value := "0", "無"),
                      <.option(^.value := "1", "上"),
                      <.option(^.value := "2", "同"),
                      <.option(^.value := "3", "下")
                    )
                  ),
                  <.td(
                    <.select(
                      ^.value := relation.semeuke.getOrElse(-1),
                      ^.onChange ==> onChange(index, (i, v) => i.copy(semeuke = util.toIntOpt(v)))_,
                      <.option(^.value := "-1", "-"),
                      <.option(^.value := "0", "無"),
                      <.option(^.value := "1", "攻"),
                      <.option(^.value := "2", "等"),
                      <.option(^.value := "3", "受")
                    )
                  ),
                  <.td(
                    ^.classSet("noborder" -> true),
                    <.button(
                      ^.onClick --> deleteRow(index),
                      "削除"
                    )
                  )
                )
              }
            )
          ),
          <.button(
            ^.onClick --> addRow,
            "行追加"
          )
        )
      )
    }
  }
  def component(pScope: BackendScope[Unit, M.Sheet]) = {
    ReactComponentB[RelationTable.Prop]("RelationTable")
      .stateless
      .backend(scope => new RelationTable.Backend(scope, pScope))
      .renderBackend
      .build
  }
}

