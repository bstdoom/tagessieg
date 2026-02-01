package io.github.bstdoom.tagessieg.command

import dev.limebeck.revealkt.core.elements.HtmlDslElement
import dev.limebeck.revealkt.dsl.*
import dev.limebeck.revealkt.dsl.slides.regularSlide
import dev.limebeck.revealkt.dsl.slides.verticalSlide
import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import io.github.bstdoom.tagessieg.infrastructure.RevealKtRenderer.Companion.render
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

    val wins = TagessiegCount.invoke(alltime)

    val reveal = revealKt("KickOff2 - Statistics J vs H") {

      slides {
        verticalSlide {
          regularSlide {
            +title { "All time" }
            +img("kick-off-2-screen.jpg")
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
