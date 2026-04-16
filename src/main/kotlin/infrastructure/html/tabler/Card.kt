package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.h3

sealed interface Card : Component {
  val width: Int
    get() = 4

  val title: String

  fun wrap(receiver: FlowContent, block: FlowContent.() -> Unit) = receiver
    .div("col-${width}") {
      div("card") {
        block()
      }
    }

  operator fun plus(other: Card): Cards = Cards(this, other)
}

data class EmptyCard(override val title: String, override val width: Int = 4) : Card {

  override fun render(receiver: FlowContent) = wrap(receiver) {
    div("card-body") {
      h3("card-title") { +title }
    }
  }
}
