package io.github.bstdoom.tagessieg.command

import dev.limebeck.revealkt.dsl.SlidesHolder
import dev.limebeck.revealkt.dsl.code
import dev.limebeck.revealkt.dsl.regular
import dev.limebeck.revealkt.dsl.revealKt
import dev.limebeck.revealkt.dsl.slides.regularSlide
import dev.limebeck.revealkt.dsl.slides.verticalSlide
import dev.limebeck.revealkt.dsl.title
import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import io.github.bstdoom.tagessieg.infrastructure.RevealKtRenderer.Companion.Ext.RevealTable
import io.github.bstdoom.tagessieg.infrastructure.RevealKtRenderer.Companion.Ext.html
import io.github.bstdoom.tagessieg.infrastructure.RevealKtRenderer.Companion.render
import io.github.bstdoom.tagessieg.infrastructure.SerializationFormat
import io.github.bstdoom.tagessieg.model.Match
import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.model.statistic.GrandSlamCount
import io.github.bstdoom.tagessieg.model.statistic.LeagueTable
import io.github.bstdoom.tagessieg.model.statistic.TagessiegCount
import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.html.small
import kotlinx.serialization.builtins.ListSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.exists
import kotlin.io.path.writeText

class RevealCmd : SubCommand(name = NAME, help = "Generate a reveal.js presentation from the matches data.") {
  companion object {
    const val NAME = "reveal"
  }

  private val GERMAN_DATE_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

  override fun run() {
    val matchesCsv = ctx.workDir.resolve("matches.csv")
    if (!matchesCsv.exists()) {
      echo("No matches.csv found in workDir. Please run 'init' first.", err = true)
      return
    }
    val csv = MatchesCsv(file=matchesCsv, createIfMissing = false)
    val target = ctx.workDir.resolve("index.html")

    val reveal = revealKt("KickOff2 - Statistics J vs H") {

      slides {
        statisticVertical(matches = csv[LocalDateRange.AllTime])
        statisticVertical(matches = csv[LocalDateRange.ByYear(2026)])
        statisticVertical(matches = csv[LocalDateRange.ByYear(2025)])
        statisticVertical(matches = csv[LocalDateRange.ByYear(2024)])
        statisticVertical(matches = csv[LocalDateRange.ByYear(2023)])
      }
    }.render()

    target.writeText(reveal)
    echo("Reveal file written to $target")
  }

  fun SlidesHolder.statisticVertical(matches: Matches) {
    val title = when (matches.filteredRange) {
      LocalDateRange.AllTime -> "All Time"
      is LocalDateRange.ByYear -> "Year ${matches.filteredRange.year}"
      else -> matches.filteredRange.toString()
    }

    val wins = TagessiegCount(matches)
    val grandslam = GrandSlamCount(matches)
    val leagueTable = LeagueTable(matches)


    return verticalSlide {
      regularSlide {
        +title { "Tagessieg $title" }
        +RevealTable(
          header = listOf("Player", "Wins"),
          rows = listOf(
            listOf("J", "${wins.j} (${grandslam.j})"),
            listOf("H", "${wins.h} (${grandslam.h})"),
            listOf("X", "${wins.x}")
          )
        )
        +html {
          small {
            +"Stand: ${GERMAN_DATE_TIME.format(LocalDateTime.now())}"
          }
        }
      }

      regularSlide {
        +title { "Liga $title" }
        +RevealTable(
          header = listOf("#", "Name", "S", "U", "N", "T", "+/-", "P"),
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
        +regular {
          "Matches: ${wins.total}, Games: ${leagueTable.rows.first().results.first + leagueTable.rows.first().results.second + leagueTable.rows.first().results.third}"
        }
      }
      regularSlide {
        +title { "Data $title" }
        +code(lang = "csv") {
          SerializationFormat.CSV.encodeToString(ListSerializer(Match.serializer()), matches.toList())
        }
      }
    }
  }
}

