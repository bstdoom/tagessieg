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

class DonutChartCard(
  override val title: String,
  override val width: Int = 4,
  val series: LinkedHashMap<String,Int>,
) : AbstractApexChartCard(ApexChartType.DONUT) {
  override val chartOptions: JsonObject by lazy {
    jsonObj {
      "chart" to jsonObj {
        "type" to chartType.value
        "height" to 300
        "fontFamily" to "inherit"
        "animations" to jsonObj {
          "enabled" to false
        }
      }

      "labels" to JsonArray(series.keys.map {  it.toJson() })
      "series" to JsonArray(series.values.map {  it.toJson() })

      "dataLabels" to jsonObj {
        "enabled" to false
      }
    }
//
//      "xaxis" to jsonObj {
//        "categories" to jsonArr(2023, 2024, 2025, 2026)
//      }
//
//      "plotOptions" to jsonObj {
//        "bar" to jsonObj {
//          "horizontal" to false
//          "columnWidth" to "45%"
//          "borderRadius" to 5
//        }
//      }
//
//      "grid" to jsonObj {
//        //"borderColor" to "grey"
//        "strokeDashArray" to 4
//      }
//
//      "tooltip" to jsonObj {
//        "theme" to "light"
//      }
//
//      "legend" to jsonObj {
//        "position" to "top"
//      }
//    }

//      series: [
//      data.summary.jens,
//      data.summary.holger,
//      grandSlamsTotal,
//      ],
//      labels: ["Jens", "Holger", "Grand Slams"],
//      colors: [colors.blue, colors.yellow, colors.green],
//      legend: {
//      position: "bottom",
//    },
//      dataLabels: {
//        enabled: true,
//    },
//      stroke: {
//        width: 1,
//    },
//      tooltip: {
//      theme: "light",
//    },
//    }
  }

  override fun render(receiver: FlowContent) = wrap(receiver){
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
