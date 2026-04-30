package io.github.bstdoom.tagessieg.adapter.out

import io.github.bstdoom.tagessieg.application.port.out.GenerateStaticSite
import io.github.bstdoom.tagessieg.domain.StatisticData
import io.github.bstdoom.tagessieg.infrastructure.FileContent
import io.github.bstdoom.tagessieg.infrastructure.html.tabler.*
import io.github.bstdoom.tagessieg.model.statistic.LeagueTable
import io.github.bstdoom.tagessieg.model.statistic.TagessiegCount
import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import java.nio.file.Path

class GenerateTablerSite : GenerateStaticSite {

  override fun invoke(data: StatisticData): List<FileContent> {
    val tscj = TagessiegCountCard(
      player = "Jan",
      color = "primary",
      count = data.wins.j,
      percent = data.wins.j.toDouble() / data.wins.total,
      diff = data.wins.j - data.wins.h
    )

    val tsch = TagessiegCountCard(
      player = "Heiko",
      color = "secondary",
      count = data.wins.h,
      percent = data.wins.h.toDouble() / data.wins.total,
      diff = data.wins.h - data.wins.j
    )

    val donut = DonutChartCard(
      title = "Verteilung Tagessiege",
      series = linkedMapOf(
        "J" to data.wins.j,
        "H" to data.wins.h,
        "X" to data.wins.x
      )
    )

    val leagueTable = LeagueTable(data.matches)
    val leagueCard = TableCard(
      title = "League Table",
      width = 6,
      cols = listOf("#", "Name", "S", "U", "N", "T", "+/-", "P"),
      rows = leagueTable.rows.mapIndexed { index, row ->
        listOf(
          "${index + 1}",
          "${row.player}",
          "${row.results.first}",
          "${row.results.second}",
          "${row.results.third}",
          "${row.goals.first}:${row.goals.second}",
          "${row.diff}",
          "${row.points}"
        )
      }
    )

    val series: LinkedHashMap<String, MutableList<Int>> = linkedMapOf(
      "J" to mutableListOf(),
      "H" to mutableListOf(),
      "X" to mutableListOf(),
    )

    val categories: MutableList<String> = mutableListOf()
    (2023..2026).forEach { year ->
      categories.add("$year")
      val year = TagessiegCount(data.matches.filter(LocalDateRange.ByYear(year)))
      series["J"]!!.add(year.j)
      series["H"]!!.add(year.h)
      series["X"]!!.add(year.x)
    }


    val barChartCard = BarChartCard(
      title = "Historie",
      width = 8,
      categories = categories,
      series = series.entries.map { (k, v) -> k to v.map { i -> i as Number }.toList() }.toMap()
    )

    val page = TablerHtml(
      icon = "⚽",
      title = "Tagessieg Statistik",
      description = "Statistiken zu KO2 Tagessieg",
      pageHeader = PageHeader(title = "Dashboard", subtitle = "All your stats in one place"),
      bodyElements = listOf(
        Cards(
          tscj, tsch, leagueCard,

          donut, barChartCard,
        ),
        divider("To be continued", true),
      )
    )

    return listOf(FileContent(Path.of("index.html"), page.html))
  }
}
