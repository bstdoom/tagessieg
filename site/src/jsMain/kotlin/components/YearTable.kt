package io.github.bstdoom.tagessieg.site.components

import androidx.compose.runtime.Composable
import io.toolisticon.kobweb.tabler.style.TablerStyles
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Table
import org.jetbrains.compose.web.dom.Tbody
import org.jetbrains.compose.web.dom.Td
import org.jetbrains.compose.web.dom.Th
import org.jetbrains.compose.web.dom.Thead
import org.jetbrains.compose.web.dom.Tr

@Composable
fun YearTable(rows: List<YearlyResult>) {
    Div(TablerStyles.card) {
        Div(TablerStyles.cardHeader) {
            H3(TablerStyles.cardTitle) { Text("Jahresübersicht") }
        }
        Div(TablerStyles.tableResponsive) {
            Table(TablerStyles.table) {
                Thead {
                    Tr {
                        Th { Text("Jahr") }
                        Th { Text("Jens") }
                        Th { Text("Holger") }
                        Th { Text("Grand Slams") }
                        Th { Text("Spieltage") }
                        Th { Text("Bilanz") }
                    }
                }
                Tbody {
                    rows.sortedByDescending { it.year }.forEach { row ->
                        Tr {
                            Td { Text(row.year.toString()) }
                            Td { Text(row.jens.toString()) }
                            Td { Text(row.holger.toString()) }
                            Td { Text(row.grandSlams.toString()) }
                            Td { Text(row.days.toString()) }
                            Td {
                                val (label, badgeClass) = when {
                                    row.jens > row.holger -> "Jens vorne" to "bg-green-lt"
                                    row.holger > row.jens -> "Holger vorne" to "bg-orange-lt"
                                    else -> "gleichauf" to "bg-yellow-lt"
                                }
                                Span(TablerStyles.badge(badgeClass)) { Text(label) }
                            }
                        }
                    }
                }
            }
        }
    }
}
