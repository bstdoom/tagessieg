package io.toolisticon.kobweb.tabler.chart

import androidx.compose.runtime.Composable
import kotlin.random.Random

@Composable
fun ApexDonutChart(rows: List<DonutRow>, chartHeightPx: Int) {
  ApexChart(
    chartId = "apex-donut-${Random.nextInt().toUInt().toString(16)}",
    options = donutOptions(rows),
    minHeightPx = chartHeightPx,
    loadingMessage = "Loading donut chart...",
  )
}
