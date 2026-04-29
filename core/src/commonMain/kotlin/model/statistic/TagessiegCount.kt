package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.model.H
import io.github.bstdoom.tagessieg.model.J
import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.model.X
import io.github.bstdoom.tagessieg.model.statistic.TagessiegCount.Companion.NAME
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName(NAME)
data class TagessiegCount(
  val j: Int,
  val h: Int,
  val x: Int,
) : Statistic {

  companion object : StatisticFactory {
    const val NAME = "tagessieg-count"

    override operator fun invoke(matches: Matches): TagessiegCount {
      val wins = matches.map { it.winner }

      return TagessiegCount(
        j = wins.count { it == J },
        h = wins.count { it == H },
        x = wins.count { it == X },
      )
    }
  }

  @Transient
  val total: Int = h + j + x

}
