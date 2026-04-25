package io.github.bstdoom.tagessieg.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.random.Random

@Serializable
data class Match(
  val id: Long = Random.nextLong(1, Long.MAX_VALUE),
  val date: LocalDate,
  val game1: Game,
  val game2: Game,
  val game3: Game,
  val comment: String? = null
) : Comparable<Match> {
  companion object {
    private val BY_DATE: Comparator<Match> = Comparator.comparing(Match::date)
  }

  val winner: Winner  by lazy  {
    val wins = listOf(game1.winner, game2.winner, game3.winner)
    val jWins = wins.count { it == J }
    val hWins = wins.count { it == H }
    when {
      jWins > hWins -> J
      hWins > jWins -> H
      else -> X
    }
  }

  @Transient
  val grandSlam: Boolean = (listOf(game1, game2, game3).map(Game::winner).distinct().singleOrNull() ?: X) != X

  @Transient
  val goals: Pair<Int, Int> = listOf(game1, game2, game3).fold(0 to 0) { acc, g -> acc.first + g.j to acc.second + g.h }

  override fun toString() = """Match[${if (grandSlam) "$winner!" else winner}](date=$date, games=[$game1, $game2, $game3], comment=${comment ?: "n/a"})"""

  override fun compareTo(other: Match): Int = BY_DATE.compare(this, other)
}
