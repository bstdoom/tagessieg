package io.github.bstdoom.tagessieg.command

import dev.limebeck.revealkt.core.elements.HtmlDslElement
import dev.limebeck.revealkt.dsl.regular
import dev.limebeck.revealkt.dsl.revealKt
import dev.limebeck.revealkt.dsl.slides.regularSlide
import dev.limebeck.revealkt.dsl.slides.verticalSlide
import dev.limebeck.revealkt.dsl.smallTitle
import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import io.github.bstdoom.tagessieg.infrastructure.RevealKtRenderer.Companion.render
import io.github.bstdoom.tagessieg.model.statistic.GrandSlamCount
import io.github.bstdoom.tagessieg.model.statistic.LeagueTable
import io.github.bstdoom.tagessieg.model.statistic.TagessiegCount
import kotlinx.html.*
import kotlin.io.path.writeText

class GenerateRevealCmd : SubCommand(NAME) {
  companion object {
    const val NAME = "reveal"
  }

  override fun run() {
    val target = ctx.properties.indexHtml
    val alltime = MatchesCsv.invoke(ctx.csvPath).matches

    val wins = TagessiegCount(alltime)
    val grandslam = GrandSlamCount(alltime)
    val leagueTable = LeagueTable(alltime)

    val reveal = revealKt("KickOff2 - Statistics J vs H") {

      slides {
        verticalSlide {
          regularSlide {
            +HtmlDslElement {
              table {
                thead {
                  tr {
                    th { +"Player" }
                    th { +"Wins" }
                  }
                }
                tbody {
                  tr {
                    td { +"J" }
                    td { +"${wins.j} (${grandslam.j})" }
                  }
                  tr {
                    td { +"H" }
                    td { +"${wins.h} (${grandslam.h})" }
                  }
                  tr {
                    td { +"X" }
                    td { +"${wins.x}" }
                  }
                }
              }
            }
          }

          regularSlide {
            +smallTitle { "Per Game" }

            +HtmlDslElement {
              table {
                thead {
                  tr {
                    th { +"#" }
                    th { +"Name" }
                    th { +"S" }
                    th { +"U" }
                    th { +"N" }
                    th { +"T" }
                    th { +"+/-" }
                    th { +"P" }
                  }
                }
                tbody {
                  leagueTable.rows.forEachIndexed { index, row ->
                    tr {
                      td { +"${index + 1}" }
                      td { +"${row.player}" }
                      td { +"${row.results.first}" }
                      td { +"${row.results.second}" }
                      td { +"${row.results.third}" }
                      td { +"${row.goals.first}:${row.goals.second}" }
                      td { +"${row.diff}" }
                      td { +"${row.points}" }
                    }
                  }
                }
              }
            }
            +regular {
              "Matches: ${wins.total}, Games: ${leagueTable.rows.first().results.first + leagueTable.rows.first().results.second + leagueTable.rows.first().results.third}"
            }
          }
        }
      }
    }.render()

    target.writeText(reveal)
    echo("Reveal file written to $target")
  }
}
