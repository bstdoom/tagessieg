package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.html.Icon
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.unsafe

class TagessiegCountCard(
  player: String,
  val color: String,
  val count: Int,
  val percent: Double,
  val diff: Int = 0
) : Card {
  override val title: String = "Tagessiege $player"
  override val width: Int = 3

  override fun render(receiver: FlowContent) = wrap(receiver) {
    div("card-body") {
      div("subheader") { +title }
      if (diff > 0) {
        div("ribbon bg-yellow") {
          unsafe {
            +Icon.TROPHY.svg
          }
        }
      }

      div("h1 mb-3") {
        +"$count"
      }
      div("subnote") {
        +"${(percent * 100).toInt()}% aller Tagessiege";
      }
      progressBar(color, (percent * 100).toInt()).render(this)
    }
  }
}
