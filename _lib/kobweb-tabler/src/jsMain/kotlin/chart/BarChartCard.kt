package io.toolisticon.kobweb.tabler.chart

import androidx.compose.runtime.Composable
import io.toolisticon.kobweb.tabler.style.TablerStyles
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text
import kotlin.random.Random

@Composable
fun BarChartCard(
  title: String,
  labels: List<String>,
  jensSeries: List<Int>,
  holgerSeries: List<Int>,
  chartHeightPx: Int = 300,
) {
  Div(TablerStyles.col12) {
    Div(TablerStyles.card) {
      Div(TablerStyles.cardHeader) {
        H3(TablerStyles.cardTitle) { Text(title) }
      }
      Div(TablerStyles.cardBody) {
        ApexChart(
          chartId = "chart-bar-${Random.nextInt().toUInt().toString(16)}",
          options = barOptions(labels, jensSeries, holgerSeries),
          minHeightPx = chartHeightPx
        )
      }
    }
  }
}
