package io.github.bstdoom.tagessieg.infrastructure.html.apexcharts

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BarChartTest {

    @Test
    fun `toString matches the js snippet structure`() {
        val chart = BarChart(
            id = "chart-bar",
            height = 300,
            series = listOf(
                BarChart.Series("Jens", listOf(20, 10, 11, 27)),
                BarChart.Series("Holger", listOf(1, 6, 30, 5)),
                BarChart.Series("X", listOf(2, 3, 40, 5))
            ),
            categories = listOf(2023, 2024, 2025, 2026),
            colors = listOf("colors.blue", "colors.yellow", "colors.red"),
            horizontal = false,
            columnWidth = "45%",
            borderRadius = 5
        )

        val output = chart.toString()

        Assertions.assertThat(output).contains("""new ApexCharts(document.getElementById("chart-bar"), {""")
        Assertions.assertThat(output).contains("""type: "bar"""")
        Assertions.assertThat(output).contains("""height: 300""")
        Assertions.assertThat(output).contains("""name: "Jens"""")
        Assertions.assertThat(output).contains("""data: [20,10,11,27]""")
        Assertions.assertThat(output).contains("""categories: [2023,2024,2025,2026]""")
        Assertions.assertThat(output).contains("""colors: [colors.blue, colors.yellow, colors.red]""")
        Assertions.assertThat(output).contains("""columnWidth: "45%"""")
        Assertions.assertThat(output).contains("""borderRadius: 5""")
        Assertions.assertThat(output).contains(""").render();""")
    }
}
