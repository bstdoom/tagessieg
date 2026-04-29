package io.toolisticon.kobweb.tabler.chart

import androidx.compose.runtime.Composable
import io.toolisticon.kobweb.tabler.component.RowList
import io.toolisticon.kobweb.tabler.style.TablerStyles
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Hr
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun DonutChartCard(title: String, rows: List<DonutRow>, note: String, chartHeightPx: Int = 280) {
  Div(TablerStyles.colLg4) {
    Div(TablerStyles.cardH100) {
      Div(TablerStyles.cardHeader) {
        H3(TablerStyles.cardTitle) { Text(title) }
      }

      Div(TablerStyles.cardBody) {
        ApexDonutChart(rows = rows, chartHeightPx = chartHeightPx)
        Hr(TablerStyles.my4)
        RowList(rows = rows)
        P(TablerStyles.textSecondaryMb0) {
          Text(note)
        }
      }
    }
  }
}
