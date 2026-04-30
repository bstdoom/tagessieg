package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.tr

data class TableCard(
  override val title: String,
  val cols: List<String>,
  val rows: List<List<String>>,
  override val width: Int = 12,
) : Card {
  override fun render(receiver: FlowContent) = wrap(receiver) {
    div("card-body") {
      div("subheader") { +title }

      div("table-responsive") {
        table("table card-table table-vcenter text-nowrap datatable") {
          thead {
            tr {
              cols.forEach { col ->
                th { +col }
              }
            }
          }
          tbody {
            rows.forEach { row ->
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
  }
}
