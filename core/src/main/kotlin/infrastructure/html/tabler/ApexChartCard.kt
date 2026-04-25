package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.html.Renderable
import kotlinx.html.script
import kotlinx.html.unsafe
import kotlinx.serialization.json.JsonObject

enum class ApexChartType(val value: String) {
  BAR("bar"),
  DONUT("donut"),
  LINE("line"),
  PIE("pie"),
  RADAR("radar"),
}

interface ApexChartCard : Card {

  fun apexChartsScript() = Renderable { receiver ->
    with(receiver) {
      script {
        unsafe {
          +StringBuilder()
            .append("document.addEventListener(\"DOMContentLoaded\", function () {")
            .append("new ApexCharts(document.getElementById(\"$chartId\"),")
            .append(chartOptions.toString())
            .append(").render();")
            .append("});")
            .toString()
        }
      }
    }
  }

  val chartId: String
  val chartOptions: JsonObject
  val chartType: ApexChartType
}

sealed class AbstractApexChartCard(
  override val chartType: ApexChartType
) : ApexChartCard {
  override val chartId: String = "chart-" + (1..8)
    .map { ('a'..'z').random() }
    .joinToString("")
}
