package io.github.bstdoom.tagessieg.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Match(
  val date: LocalDate,
  val game1: Game,
  val game2: Game,
  val game3: Game,
  val comment: String? = null
) : Comparable<Match> {
  companion object {
    private val BY_DATE : Comparator<Match> = Comparator.comparing(Match::date)
  }

  @Transient
  val winner: Winner = listOf(game1, game2, game3).fold(X as Winner) { acc , g -> acc + g.winner }

  @Transient
  val grandSlam: Boolean = (listOf(game1, game2, game3).map(Game::winner).distinct().singleOrNull() ?: X) != X

  override fun toString() = """Match[${if (grandSlam) "$winner!" else winner}](date=$date, games=[$game1, $game2, $game3], comment=${comment ?: "n/a"})"""

  override fun compareTo(other: Match): Int = BY_DATE.compare(this, other)
}
