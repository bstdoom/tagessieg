package infrastructure.html.apexcharts


import kotlinx.serialization.Serializable

@Serializable
@Deprecated("can be used in tabler")
data class Chart(
  val chart: Chart,
  val dataLabels: DataLabels,
  val grid: Grid,
  val legend: Legend,
  val plotOptions: PlotOptions,
  val series: List<Serie>,
  val tooltip: Tooltip,
  val xaxis: Xaxis
) {
  @Serializable
  data class Chart(
    val animations: Animations,
    val fontFamily: String,
    val height: Int,
    val toolbar: Toolbar,
    val type: String
  ) {
    @Serializable
    data class Animations(
      val enabled: Boolean
    )

    @Serializable
    data class Toolbar(
      val show: Boolean
    )
  }

  @Serializable
  data class DataLabels(
    val enabled: Boolean
  )

  @Serializable
  data class Grid(
    val borderColor: String,
    val strokeDashArray: Int
  )

  @Serializable
  data class Legend(
    val position: String
  )

  @Serializable
  data class PlotOptions(
    val bar: Bar
  ) {
    @Serializable
    data class Bar(
      val borderRadius: Int,
      val columnWidth: String,
      val horizontal: Boolean
    )
  }

  @Serializable
  data class Serie(
    val `data`: List<Int>,
    val name: String
  )

  @Serializable
  data class Tooltip(
    val theme: String
  )

  @Serializable
  data class Xaxis(
    val categories: List<Int>
  )
}
