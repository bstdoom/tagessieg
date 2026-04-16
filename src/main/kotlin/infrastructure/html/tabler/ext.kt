package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import kotlinx.html.*

fun FlowContent.table(card: TableCard) = this.div("card") {
  div("card-header") {
    h3("card-title") { +card.title }
  }
  div("table-responsive") {
    table("table card-table table-vcenter text-nowrap datatable") {
      thead {
        tr {
          card.cols.forEach { col ->
            th { +col }
          }
        }
      }
      tbody {
        card.rows.forEach { row ->
          tr {
            row.forEach { cell ->
              td { +cell }
            }
          }
        }
      }
    }
  }
}
