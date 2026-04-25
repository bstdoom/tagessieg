package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.model.*
import kotlinx.serialization.Serializable

@Serializable
data class LeagueTable(
  val rows: List<Row>
) : Statistic {
  companion object : StatisticFactory {
    const val NAME = "league-table"

    override fun invoke(matches: Matches): LeagueTable {
      val p = matches.flatMap { listOf(it.game1, it.game2, it.game3) }
        .fold(Row(J) to Row(H)) { p, g ->
          p.copy(
            first = p.first.copy(
              goals = p.first.goals.copy(
                first = p.first.goals.first + g.j,
                second = p.first.goals.second + g.h,
              ),
              results = p.first.results.copy(
                first = if (g.winner == p.first.player) p.first.results.first + 1 else p.first.results.first,
                second = if (g.winner == X) p.first.results.second + 1 else p.first.results.second,
                third = if (g.winner == p.second.player) p.first.results.third + 1 else p.first.results.third
              )
            ),
            second = p.second.copy(
              goals = p.second.goals.copy(
                first = p.second.goals.first + g.h,
                second = p.second.goals.second + g.j,
              ),
              results = p.second.results.copy(
                first = if (g.winner == p.second.player) p.second.results.first + 1 else p.second.results.first,
                second = if (g.winner == X) p.second.results.second + 1 else p.second.results.second,
                third = if (g.winner == p.first.player) p.second.results.third + 1 else p.second.results.third
              )
            )
          )
        }

      return LeagueTable(
        rows = listOf(p.first, p.second).sorted()
      )
    }


  }

  @Serializable
  data class Row(
    val player: Player,
    val goals: Pair<Int, Int> = Pair(0, 0),
    val results: Triple<Int, Int, Int> = Triple(0, 0, 0)
  ) : Comparable<Row> {
    override fun compareTo(other: Row): Int = Comparator
      .comparingInt<Row> { it.points }
      .thenComparingInt { it.goals.second - it.goals.first }
      .thenComparingInt { it.goals.first }
      .reversed()
      .compare(this, other)

    val points by lazy {
      results.first * 3 + results.second
    }

    val diff by lazy {
      goals.first - goals.second
    }

  }

  operator fun get(player: Player): Row = rows.find { it.player == player } ?: throw IllegalStateException("Player $player not found in league table.")
}
