package io.github.bstdoom.tagessieg.command

import io.github.bstdoom.tagessieg.adapter.out.GenerateTablerSite
import io.github.bstdoom.tagessieg.adapter.out.MatchesCsvRepository
import io.github.bstdoom.tagessieg.command.tabler.DashboardData
import io.github.bstdoom.tagessieg.command.tabler.MonthlyTrend
import io.github.bstdoom.tagessieg.command.tabler.Summary
import io.github.bstdoom.tagessieg.command.tabler.YearlyResult
import io.github.bstdoom.tagessieg.domain.DefaultStatisticData
import kotlin.io.path.exists

class TablerCmd : SubCommand(name = NAME, help = "Generate charts.") {

  companion object {
    const val NAME = "tabler"
  }

  override fun run() {
    val matchesCsv = ctx.workDir.resolve("matches.csv")
    if (!matchesCsv.exists()) {
      echo("No matches.csv found in workDir. Please run 'init' first.", err = true)
      return
    }
    val matches = MatchesCsvRepository(matchesCsv).load()
    val data = DefaultStatisticData(matches)

    val result = GenerateTablerSite()(data).single()

    result.write(ctx.workDir)
    echo("Tabler file written to ${ctx.workDir.resolve(result.path)}")
  }

  fun sampleData(): DashboardData =
    DashboardData(
      generatedAt = "2026-04-14",
      summary = Summary(
        jens = 184,
        holger = 133,
        grandSlamsJens = 7,
        grandSlamsHolger = 5,
        totalDays = 317,
        latestDay = "2026-03-18",
        currentLeader = "Jens",
      ),
      yearlyResults = listOf(
        YearlyResult(year = 2023, jens = 14, holger = 19, grandSlams = 1, days = 33),
        YearlyResult(year = 2024, jens = 22, holger = 15, grandSlams = 3, days = 37),
        YearlyResult(year = 2025, jens = 18, holger = 21, grandSlams = 2, days = 39),
        YearlyResult(year = 2026, jens = 3, holger = 1, grandSlams = 1, days = 4),
      ),
      monthlyTrend = MonthlyTrend(
        labels = listOf("Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"),
        jens = listOf(1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3),
        holger = listOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
      ),
    )

}
