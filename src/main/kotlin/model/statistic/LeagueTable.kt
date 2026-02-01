package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.model.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class LeagueTable(
  val goalsJ: Int,
  val goalsH: Int,
  val j: Int,
  val h: Int,
  val x: Int,
  val rows: List<Row> = emptyList()
) : Statistic {
  companion object : StatisticFactory {
    const val NAME = "league-table"

    override fun invoke(matches: Matches): LeagueTable {
      matches.flatMap { listOf(it.game1, it.game2, it.game3) }.forEach { game ->
        println("${game.winner} ${game.j} ${game.h}")
      }

      return matches.flatMap { listOf(it.game1, it.game2, it.game3) }.fold(
        LeagueTable(0, 0, 0, 0, 0)
      ) { table, game -> table + game }.copy(
        rows = listOf(
          Row(player = J, goals = Pair(0, 0)),
          Row(player = H, goals = Pair(0, 0)),
        )
      )
    }

    private operator fun LeagueTable.plus(game: Game): LeagueTable {
      val c = this.copy(
        goalsJ = goalsJ + (game.j),
        goalsH = goalsH + (game.h),
      )

      return when (game.winner) {
        H -> copy(h = h + 1)
        J -> copy(j = j + 1)
        X -> copy(x = x + 1)
      }
    }

  }

  @Serializable
  data class Row(
    val player: Player,
    val goals: Pair<Int, Int>
  )

  @Transient
  val diff = goalsJ - goalsH

  @Transient
  val pointsJ = j * 3 + x

  @Transient
  val pointsH = h * 3 + x

  @Transient
  val total = h + j + x


//  Sp.
//  s
//  U
//  N
//  Tore
//  Diff.
//  Punkt
}
