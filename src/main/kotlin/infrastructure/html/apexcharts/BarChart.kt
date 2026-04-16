package io.github.bstdoom.tagessieg.infrastructure.html.apexcharts

/**
 * Data class representing an ApexCharts Bar Chart configuration.
 *
 * @param id The HTML element ID to render the chart into.
 * @param height The chart height.
 * @param series List of data series.
 * @param categories X-axis categories.
 * @param colors List of colors for the series.
 * @param horizontal Whether the bar chart is horizontal.
 * @param columnWidth Width of the bars.
 * @param borderRadius Border radius for the bars.
 */
@Deprecated("can be used in tabler")
data class BarChart(
    val id: String,
    val height: Int = 300,
    val series: List<Series> = emptyList(),
    val categories: List<Any> = emptyList(),
    val colors: List<String> = listOf("colors.blue", "colors.yellow", "colors.red"),
    val horizontal: Boolean = false,
    val columnWidth: String = "45%",
    val borderRadius: Int = 5,
) {
    data class Series(
        val name: String,
        val data: List<Number>,
    ) {
        override fun toString(): String = """
            {
              name: "$name",
              data: [${data.joinToString(",")}],
            }
        """.trimIndent()
    }

    override fun toString(): String {
        val seriesStr = series.joinToString(",\n") { it.toString().prependIndent("      ") }
        val categoriesStr = categories.joinToString(",") {
            if (it is String) "\"$it\"" else it.toString()
        }
        val colorsStr = colors.joinToString(", ")

        return """
            new ApexCharts(document.getElementById("$id"), {
              chart: {
                type: "bar",
                height: $height,
                fontFamily: "inherit",
                toolbar: { show: false },
                animations: { enabled: true },
              },
              series: [
$seriesStr
              ],
              colors: [$colorsStr],

              xaxis: {
                categories: [$categoriesStr],
              },
              plotOptions: {
                bar: {
                  horizontal: $horizontal,
                  columnWidth: "$columnWidth",
                  borderRadius: $borderRadius,
                },
              },
              dataLabels: {
                enabled: false,
              },
              grid: {
                borderColor: colors.grid,
                strokeDashArray: 4,
              },
              tooltip: {
                theme: "light",
              },
              legend: {
                position: "top",
              },
            }).render();
        """.trimIndent()
    }
}
