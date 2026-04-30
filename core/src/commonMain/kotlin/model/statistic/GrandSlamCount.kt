package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.model.H
import io.github.bstdoom.tagessieg.model.J
import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.model.statistic.GrandSlamCount.Companion.NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName(NAME)
data class GrandSlamCount(
  val j: Int,
  val h: Int,
) : Statistic {

  companion object : StatisticFactory {
    const val NAME = "grandslam-count"

    override operator fun invoke(matches: Matches): GrandSlamCount {
      val wins = matches
        .filter { it.grandSlam }
        .map { it.winner }

      return GrandSlamCount(
        j = wins.count { it == J },
        h = wins.count { it == H },
      )
    }
  }

  @Transient
  val total: Int = h + j

}
