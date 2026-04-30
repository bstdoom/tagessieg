package io.github.bstdoom.tagessieg.site.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import io.github.bstdoom.tagessieg.site.components.DashboardData
import io.github.bstdoom.tagessieg.site.components.MonthlyTrend
import io.github.bstdoom.tagessieg.site.components.Summary
import io.github.bstdoom.tagessieg.site.components.YearTable
import io.github.bstdoom.tagessieg.site.components.YearlyResult
import io.toolisticon.kobweb.tabler.KobwebTabler.Body
import io.toolisticon.kobweb.tabler.KobwebTabler.Charts.BarChartCard
import io.toolisticon.kobweb.tabler.KobwebTabler.Charts.DonutChartCard
import io.toolisticon.kobweb.tabler.KobwebTabler.Charts.LineChartCard
import io.toolisticon.kobweb.tabler.KobwebTabler.Components.Cards
import io.toolisticon.kobweb.tabler.KobwebTabler.Components.StatCard
import io.toolisticon.kobweb.tabler.KobwebTabler.Header
import io.toolisticon.kobweb.tabler.KobwebTabler.NavBar
import io.toolisticon.kobweb.tabler.KobwebTabler.Page as TablerPage
import io.toolisticon.kobweb.tabler.chart.DonutRow
import io.toolisticon.kobweb.tabler.component.StatCardData
import io.toolisticon.kobweb.tabler.style.TablerStyles
import org.jetbrains.compose.web.dom.Div

@Page
@Composable
fun Index() {
  val data = dashboardData()
  val totalWins = data.summary.jens + data.summary.holger
  val jensPercent = if (totalWins == 0) 0 else ((data.summary.jens * 100.0) / totalWins).toInt()
  val holgerPercent = 100 - jensPercent
  val grandSlamsTotal = data.summary.grandSlamsJens + data.summary.grandSlamsHolger

  TablerPage {
    NavBar(title = "Tagessieg", icon = "⚽", generatedAtText = "Stand: ${data.generatedAt}")

    Header(
      title = "Tagessieg 2026",
      subtitle = "Der aktuelle Stand der Tagessiege zwischen Jens und Holger."
    )

    Body {
      Div(TablerStyles.containerXl) {
        Cards {
          StatCard(
            StatCardData(
              title = "Tagessiege Jens",
              value = data.summary.jens.toString(),
              note = "$jensPercent% aller Tagessiege",
              progressPercent = jensPercent,
              progressClass = "bg-primary",
            )
          )
          StatCard(
            StatCardData(
              title = "Tagessiege Holger",
              value = data.summary.holger.toString(),
              note = "$holgerPercent% aller Tagessiege",
              progressPercent = holgerPercent,
              progressClass = "bg-yellow",
            )
          )
          StatCard(
            StatCardData(
              title = "Grand Slams",
              value = grandSlamsTotal.toString(),
              note = "Jens ${data.summary.grandSlamsJens} · Holger ${data.summary.grandSlamsHolger}",
              badgeText = "Letzter Spieltag: ${data.summary.latestDay}",
              badgeClass = "bg-green-lt",
            )
          )
          StatCard(
            StatCardData(
              title = "Spieltage gesamt",
              value = data.summary.totalDays.toString(),
              note = "Führend: ${data.summary.currentLeader}",
            )
          )
        }

        Cards {
          DonutChartCard(
            title = "Verteilung Tagessiege",
            rows = listOf(
              DonutRow("Jens", data.summary.jens, "#206bc4"),
              DonutRow("Holger", data.summary.holger, "#f59f00"),
              DonutRow("Grand Slams", grandSlamsTotal, "#2fb344"),
            ),
            note = "Verteilung der bisherigen Tagessiege inklusive Grand Slams.",
            chartHeightPx = 300,
          )
          LineChartCard(
            title = "Verlauf 2026",
            labels = data.monthlyTrend.labels,
            jensSeries = data.monthlyTrend.jens,
            holgerSeries = data.monthlyTrend.holger,
          )
        }

        Cards {
          BarChartCard(
            title = "Jahresvergleich",
            labels = data.yearlyResults.map { it.year.toString() },
            jensSeries = data.yearlyResults.map { it.jens },
            holgerSeries = data.yearlyResults.map { it.holger },
          )
        }

        YearTable(data.yearlyResults)
      }
    }

  }

}

private fun dashboardData(): DashboardData {
  return DashboardData(
    generatedAt = "2026-04-26",
    summary = Summary(
      jens = 18,
      holger = 12,
      grandSlamsJens = 4,
      grandSlamsHolger = 2,
      totalDays = 30,
      latestDay = "2026-04-20",
      currentLeader = "Jens",
    ),
    yearlyResults = listOf(
      YearlyResult(2026, 18, 12, 6, 30),
      YearlyResult(2025, 16, 14, 5, 30),
      YearlyResult(2024, 13, 17, 3, 30),
      YearlyResult(2023, 15, 15, 4, 30),
    ),
    monthlyTrend = MonthlyTrend(
      labels = listOf("Jan", "Feb", "Mär", "Apr", "Mai", "Jun"),
      jens = listOf(3, 5, 4, 2, 3, 1),
      holger = listOf(2, 3, 3, 4, 2, 1),
    ),
  )
}
