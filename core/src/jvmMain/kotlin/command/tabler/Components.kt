package io.github.bstdoom.tagessieg.command.tabler

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.span
import kotlinx.html.style
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.tr

data class StatCard(
    val title: String,
    val value: String,
    val note: String,
    val progressPercent: Int? = null,
    val progressClass: String = "bg-primary",
    val badgeText: String? = null,
    val badgeClass: String = "bg-green-lt",
)

data class ChartCard(
    val title: String,
    val chartId: String,
    val minHeightPx: Int = 300,
)

object DashboardComponents {

    fun FlowContent.statCard(card: StatCard) {
        div(classes = "card") {
            div(classes = "card-body") {
                div(classes = "subheader") { +card.title }
                div(classes = "h1 mb-3") { +card.value }
                div(classes = "text-secondary") { +card.note }

                if (card.badgeText != null) {
                    div(classes = "mt-3") {
                        span(classes = "badge ${card.badgeClass}") { +card.badgeText }
                    }
                }

                if (card.progressPercent != null) {
                    div(classes = "mt-3") {
                        div(classes = "progress progress-sm") {
                            div(classes = "progress-bar ${card.progressClass}") {
                                style = "width: ${card.progressPercent}%"
                                attributes["role"] = "progressbar"
                            }
                        }
                    }
                }
            }
        }
    }

    fun FlowContent.chartCard(card: ChartCard) {
        div(classes = "card") {
            div(classes = "card-header") {
                h3(classes = "card-title") { +card.title }
            }
            div(classes = "card-body") {
                div {
                    id = card.chartId
                    style = "min-height: ${card.minHeightPx}px"
                }
            }
        }
    }

    fun FlowContent.yearTable(rows: List<YearlyResult>) {
        div(classes = "card") {
            div(classes = "card-header") {
                h3(classes = "card-title") { +"Jahresübersicht" }
            }
            div(classes = "table-responsive") {
                table(classes = "table card-table table-vcenter") {
                    thead {
                        tr {
                            th { +"Jahr" }
                            th { +"Jens" }
                            th { +"Holger" }
                            th { +"Grand Slams" }
                            th { +"Spieltage" }
                            th { +"Bilanz" }
                        }
                    }
                    tbody {
                        rows.sortedByDescending { it.year }.forEach { row ->
                            tr {
                                td { +row.year.toString() }
                                td { +row.jens.toString() }
                                td { +row.holger.toString() }
                                td { +row.grandSlams.toString() }
                                td { +row.days.toString() }
                                td {
                                    val (label, badgeClass) = when {
                                        row.jens > row.holger -> "Jens vorne" to "bg-green-lt"
                                        row.holger > row.jens -> "Holger vorne" to "bg-orange-lt"
                                        else -> "gleichauf" to "bg-yellow-lt"
                                    }
                                    span(classes = "badge $badgeClass") { +label }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun buildDashboardDataScript(dataJson: String): String = buildString {
        appendLine("const dashboardData = $dataJson;")
        appendLine(
            """
            const colors = {
              blue: "#206bc4",
              yellow: "#f59f00",
              green: "#2fb344",
              grid: "#e6e7e9"
            };
            """.trimIndent()
        )
    }
}
