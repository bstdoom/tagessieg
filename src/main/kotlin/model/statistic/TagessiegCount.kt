package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.model.H
import io.github.bstdoom.tagessieg.model.J
import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.model.statistic.TagessiegCount.Companion.NAME
import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(NAME)
data class TagessiegCount(
  override val range: LocalDateRange,
  val total: Int,
  val j: Int,
  val h: Int
) : Statistic {
  companion object {
    const val NAME = "tagessieg-count"
    operator fun invoke(matches: Matches): TagessiegCount {
      val wins = matches.map { it.winner }
      return TagessiegCount(
        range = matches.filteredRange,
        j = wins.count { it == J },
        h = wins.count { it == H },
        total = wins.size,
      )
    }

  }
}
