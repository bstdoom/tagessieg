package io.toolisticon.kobweb.tabler.chart

fun donutOptions(rows: List<DonutRow>): Any {
  val options = js("({})")
  val chart = js("({})")
  chart.type = "donut"
  chart.height = if (rows.isEmpty()) 240 else 280
  chart.toolbar = js("({})")
  chart.toolbar.show = false
  chart.animations = js("({})")
  chart.animations.enabled = false
  options.chart = chart
  options.series = rows.map { it.value }.toTypedArray()
  options.labels = rows.map { it.label }.toTypedArray()
  options.colors = rows.map { it.color }.toTypedArray()
  val legend = js("({})")
  legend.position = "bottom"
  options.legend = legend
  val dataLabels = js("({})")
  dataLabels.enabled = true
  options.dataLabels = dataLabels
  val stroke = js("({})")
  stroke.width = 1
  options.stroke = stroke
  val tooltip = js("({})")
  tooltip.theme = "light"
  options.tooltip = tooltip
  return options
}

fun lineOptions(labels: List<String>, jensSeries: List<Int>, holgerSeries: List<Int>): Any {
  val options = js("({})")
  val chart = js("({})")
  chart.type = "line"
  chart.height = 300
  chart.toolbar = js("({})")
  chart.toolbar.show = false
  chart.animations = js("({})")
  chart.animations.enabled = false
  options.chart = chart
  val seriesJens = js("({})")
  seriesJens.name = "Jens"
  seriesJens.data = jensSeries.toTypedArray()
  val seriesHolger = js("({})")
  seriesHolger.name = "Holger"
  seriesHolger.data = holgerSeries.toTypedArray()
  options.series = arrayOf(seriesJens, seriesHolger)
  options.colors = arrayOf("#206bc4", "#f59f00")
  val stroke = js("({})")
  stroke.width = 3
  stroke.curve = "smooth"
  options.stroke = stroke
  val xaxis = js("({})")
  xaxis.categories = labels.toTypedArray()
  options.xaxis = xaxis
  val yaxis = js("({})")
  yaxis.min = 0
  options.yaxis = yaxis
  val markers = js("({})")
  markers.size = 4
  options.markers = markers
  val grid = js("({})")
  grid.borderColor = "#e6e7e9"
  grid.strokeDashArray = 4
  options.grid = grid
  val legend = js("({})")
  legend.position = "top"
  options.legend = legend
  val tooltip = js("({})")
  tooltip.theme = "light"
  options.tooltip = tooltip
  return options
}

fun barOptions(labels: List<String>, jensSeries: List<Int>, holgerSeries: List<Int>): Any {
  val options = js("({})")
  val chart = js("({})")
  chart.type = "bar"
  chart.height = 300
  chart.toolbar = js("({})")
  chart.toolbar.show = false
  chart.animations = js("({})")
  chart.animations.enabled = false
  options.chart = chart
  val seriesJens = js("({})")
  seriesJens.name = "Jens"
  seriesJens.data = jensSeries.toTypedArray()
  val seriesHolger = js("({})")
  seriesHolger.name = "Holger"
  seriesHolger.data = holgerSeries.toTypedArray()
  options.series = arrayOf(seriesJens, seriesHolger)
  options.colors = arrayOf("#206bc4", "#f59f00")
  val xaxis = js("({})")
  xaxis.categories = labels.toTypedArray()
  options.xaxis = xaxis
  val plotOptions = js("({})")
  val bar = js("({})")
  bar.horizontal = false
  bar.columnWidth = "45%"
  bar.borderRadius = 6
  plotOptions.bar = bar
  options.plotOptions = plotOptions
  val dataLabels = js("({})")
  dataLabels.enabled = false
  options.dataLabels = dataLabels
  val grid = js("({})")
  grid.borderColor = "#e6e7e9"
  grid.strokeDashArray = 4
  options.grid = grid
  val legend = js("({})")
  legend.position = "top"
  options.legend = legend
  val tooltip = js("({})")
  tooltip.theme = "light"
  options.tooltip = tooltip
  return options
}
