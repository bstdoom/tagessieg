package io.github.bstdoom.tagessieg.command.tabler

import io.github.bstdoom.tagessieg.command.tabler.DashboardComponents.chartCard
import io.github.bstdoom.tagessieg.command.tabler.DashboardComponents.statCard
import io.github.bstdoom.tagessieg.command.tabler.DashboardComponents.yearTable
import kotlinx.html.*
import kotlinx.html.stream.createHTML

class DashboardPage {
  fun render(data: DashboardData, dataJson: String): String {
    val totalWins = data.summary.jens + data.summary.holger
    val jensPercent = if (totalWins == 0) 0 else ((data.summary.jens * 100.0) / totalWins).toInt()
    val holgerPercent = 100 - jensPercent
    val grandSlamsTotal = data.summary.grandSlamsJens + data.summary.grandSlamsHolger

    return createHTML().html {
      attributes["lang"] = "de"
      head {
        meta(charset = "utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1")
        title("⚽ Tagessieg Dashboard")
        link(
          rel = "stylesheet",
          href = "https://cdn.jsdelivr.net/npm/@tabler/core@1.4.0/dist/css/tabler.min.css"
        )
      }
      body {
        script {
          unsafe {
            +DashboardComponents.buildDashboardDataScript(dataJson)
          }
        }

        div(classes = "page") {
          header(classes = "navbar navbar-expand-md d-print-none") {
            div(classes = "container-xl") {
              div(classes = "navbar-brand navbar-brand-autodark pe-0 pe-md-3") {
                +"Tagessieg"
              }
              div(classes = "navbar-nav flex-row order-md-last") {
                div(classes = "nav-item d-flex align-items-center text-secondary") {
                  +"Stand: ${data.generatedAt}"
                }
              }
            }
          }

          div(classes = "page-wrapper") {
            div(classes = "page-header d-print-none") {
              div(classes = "container-xl") {
                div(classes = "row g-2 align-items-center") {
                  div(classes = "col") {
                    h2(classes = "page-title") { +"Dashboard" }
                    div(classes = "text-secondary") {
                      +"Generiert mit Kotlin + kotlinx-html + Tabler"
                    }
                  }
                }
              }
            }

            div(classes = "page-body") {
              div(classes = "container-xl") {
                div(classes = "row row-deck row-cards") {
                  div(classes = "col-sm-6 col-lg-3") {
                    statCard(
                      StatCard(
                        title = "Tagessiege Jens",
                        value = data.summary.jens.toString(),
                        note = "$jensPercent% aller Tagessiege",
                        progressPercent = jensPercent,
                        progressClass = "bg-primary",
                      )
                    )
                  }
                  div(classes = "col-sm-6 col-lg-3") {
                    statCard(
                      StatCard(
                        title = "Tagessiege Holger",
                        value = data.summary.holger.toString(),
                        note = "$holgerPercent% aller Tagessiege",
                        progressPercent = holgerPercent,
                        progressClass = "bg-yellow",
                      )
                    )
                  }
                  div(classes = "col-sm-6 col-lg-3") {
                    statCard(
                      StatCard(
                        title = "Grand Slams",
                        value = grandSlamsTotal.toString(),
                        note = "Jens ${data.summary.grandSlamsJens} · Holger ${data.summary.grandSlamsHolger}",
                        badgeText = "Letzter Spieltag: ${data.summary.latestDay}",
                        badgeClass = "bg-green-lt",
                      )
                    )
                  }
                  div(classes = "col-sm-6 col-lg-3") {
                    statCard(
                      StatCard(
                        title = "Spieltage gesamt",
                        value = data.summary.totalDays.toString(),
                        note = "Führend: ${data.summary.currentLeader}",
                      )
                    )
                  }

                  div(classes = "col-lg-4") {
                    chartCard(ChartCard("Verteilung Tagessiege", "chart-donut"))
                  }
                  div(classes = "col-lg-8") {
                    chartCard(ChartCard("Verlauf 2026", "chart-line"))
                  }
                  div(classes = "col-lg-12") {
                    chartCard(ChartCard("Jahresvergleich", "chart-bar"))
                  }
                  div(classes = "col-12") {
                    yearTable(data.yearlyResults)
                  }
                }
              }
            }
          }
        }

        script(src = "https://cdn.jsdelivr.net/npm/apexcharts") {}
        script(src = "https://cdn.jsdelivr.net/npm/@tabler/core@1.4.0/dist/js/tabler.min.js") {}
        script {
          unsafe {
            +chartInitScript()
          }
        }
      }
    }
  }

  private fun chartInitScript(): String = """
        document.addEventListener("DOMContentLoaded", function () {
          const data = dashboardData;
          const grandSlamsTotal = data.summary.grandSlamsJens + data.summary.grandSlamsHolger;

          new ApexCharts(document.getElementById("chart-donut"), {
            chart: {
              type: "donut",
              height: 300,
              fontFamily: "inherit",
              animations: { enabled: false }
            },
            series: [data.summary.jens, data.summary.holger, grandSlamsTotal],
            labels: ["Jens", "Holger", "Grand Slams"],
            colors: [colors.blue, colors.yellow, colors.green],
            legend: { position: "bottom" },
            dataLabels: { enabled: true },
            stroke: { width: 1 },
            tooltip: { theme: "light" }
          }).render();

          new ApexCharts(document.getElementById("chart-line"), {
            chart: {
              type: "line",
              height: 300,
              fontFamily: "inherit",
              toolbar: { show: false },
              animations: { enabled: false }
            },
            series: [
              { name: "Jens", data: data.monthlyTrend.jens },
              { name: "Holger", data: data.monthlyTrend.holger }
            ],
            colors: [colors.blue, colors.yellow],
            stroke: { width: 3, curve: "smooth" },
            xaxis: { categories: data.monthlyTrend.labels },
            yaxis: { min: 0 },
            markers: { size: 4 },
            grid: {
              borderColor: colors.grid,
              strokeDashArray: 4
            },
            legend: { position: "top" },
            tooltip: { theme: "light" }
          }).render();

          new ApexCharts(document.getElementById("chart-bar"), {
            chart: {
              type: "bar",
              height: 300,
              fontFamily: "inherit",
              toolbar: { show: false },
              animations: { enabled: false }
            },
            series: [
              { name: "Jens", data: data.yearlyResults.map(r => r.jens) },
              { name: "Holger", data: data.yearlyResults.map(r => r.holger) }
            ],
            colors: [colors.blue, colors.yellow],
            xaxis: { categories: data.yearlyResults.map(r => String(r.year)) },
            plotOptions: {
              bar: {
                horizontal: false,
                columnWidth: "45%",
                borderRadius: 6
              }
            },
            dataLabels: { enabled: false },
            grid: {
              borderColor: colors.grid,
              strokeDashArray: 4
            },
            legend: { position: "top" },
            tooltip: { theme: "light" }
          }).render();
        });
    """.trimIndent()
}
