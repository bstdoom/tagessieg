package io.toolisticon.kobweb.tabler.component

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.modifiers.attr
import io.toolisticon.kobweb.tabler.chart.DonutRow
import io.toolisticon.kobweb.tabler.style.TablerStyles
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Ul

@Composable
fun RowList(rows: List<DonutRow>) {
  Ul(TablerStyles.listGroup) {
    rows.forEach { row ->
      Li(TablerStyles.listGroupItem) {
        Div(TablerStyles.listGroupRow) {
          Span(TablerStyles.badge, attrs = {
            attr("style", "background-color: ${row.color}; width: 0.75rem; height: 0.75rem; padding: 0;")
          })
          Span(TablerStyles.fwSemibold) { Text(row.label) }
        }
        Span(TablerStyles.textSecondary) { Text(row.value.toString()) }
      }
    }
  }
}
