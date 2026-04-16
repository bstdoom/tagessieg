package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.serialization.jsonArr
import io.github.bstdoom.tagessieg.infrastructure.serialization.jsonObj
import io.github.bstdoom.tagessieg.infrastructure.serialization.series
import io.github.bstdoom.tagessieg.infrastructure.serialization.toJson
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.style
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import java.util.UUID

class BarChartCard(
  override val title: String,
  val categories : List<String>,
  val series: Map<String, List<Number>>,
  override val width: Int = 12
) : AbstractApexChartCard(ApexChartType.BAR) {

  override val chartOptions: JsonObject by lazy {
    jsonObj {
      "chart" to jsonObj {
        "type" to chartType.value
        "height" to 300
        "fontFamily" to "inherit"
        "toolbar" to jsonObj {
          "show" to false
        }
        "animations" to jsonObj {
          "enabled" to true
        }
      }

      "series" to JsonArray(
        series.map { series(it.key, it.value) }
      )

      "xaxis" to jsonObj {
        "categories" to JsonArray(categories.map { it.toJson() })
      }

      "plotOptions" to jsonObj {
        "bar" to jsonObj {
          "horizontal" to false
          "columnWidth" to "45%"
          "borderRadius" to 5
        }
      }

      "dataLabels" to jsonObj {
        "enabled" to false
      }

      "grid" to jsonObj {
        //"borderColor" to "grey"
        "strokeDashArray" to 4
      }

      "tooltip" to jsonObj {
        "theme" to "light"
      }

      "legend" to jsonObj {
        "position" to "top"
      }
    }
  }


  override fun render(receiver: FlowContent) = wrap(receiver) {
    div(classes = "card-header") {
      h3(classes = "card-title") { +title }
    }
    div(classes = "card-body") {
      div("chart-box") {
        id = chartId
        style = "min-height: 300px"
      }
      apexChartsScript().render(this)
    }
  }
}
