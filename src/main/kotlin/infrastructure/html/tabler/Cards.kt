package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.html.Renderable
import kotlinx.html.FlowContent
import kotlinx.html.div

data class Cards(
  private val cards: List<Card> = emptyList(),
) : Iterable<Card> by cards, Renderable {

  constructor(vararg cards: Card) : this(cards.toList())

  operator fun plus(card: Card): Cards = copy(cards = cards + card)
  operator fun plus(cards: Cards): Cards = copy(cards = cards + cards.cards)

  override fun render(receiver: FlowContent) = with(receiver){
    div("container-xl") {
      div("row row-deck row-cards") {
        cards.forEach { card ->
          card.render(this)
        }
      }
    }
  }
}
