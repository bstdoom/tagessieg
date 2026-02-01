package io.github.bstdoom.tagessieg.command

import dev.limebeck.revealkt.core.elements.HtmlDslElement
import dev.limebeck.revealkt.dsl.*
import dev.limebeck.revealkt.dsl.slides.regularSlide
import dev.limebeck.revealkt.dsl.slides.verticalSlide
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
            +smallTitle { "All time" }
            +HtmlDslElement {
              table {
                thead {
                  tr {
                    th { +"J" }
                    th { +"H" }
                    th { +"X" }
                  }
                }
                tbody {
                  tr {
                    td { +"${wins.j} (${grandslam.j})" }
                    td { +"${wins.h} (${grandslam.h})" }
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
                    th { +"Points" }
                    th { +"GD" }
                  }
                }
                tbody {
                  tr {
                    td { +"${leagueTable.total})" }
                    td { +"J" }
                    td { +"${leagueTable.pointsJ})" }
                    td { +"${leagueTable.goalsJ}:-${leagueTable.goalsH}" }
                  }
                  tr {
                    td { +"${leagueTable.total})" }
                    td { +"H" }
                    td { +"${leagueTable.pointsH})" }
                    td { +"${leagueTable.goalsH}:-${leagueTable.goalsJ}" }
                  }
                }
              }
            }
          }
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
                    td { +"${wins.j}" }
                  }
                  tr {
                    td { +"H" }
                    td { +"${wins.h}" }
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
            +smallTitle { "All time" }

            +row {
              column { +smallTitle { "J" } }
              column { +smallTitle { "H" } }
              column { +smallTitle { "X" } }
            }
            +row {
              column { +smallTitle { "${wins.j}" } }
              column { +smallTitle { "${wins.h}" } }
              column { +smallTitle { "${wins.x}" } }
            }
          }
          regularSlide {
            +smallTitle { "All time" }

          }
        }
      }

    }.render()

    target.writeText(reveal)
    echo("Reveal file written to $target")
  }
}
