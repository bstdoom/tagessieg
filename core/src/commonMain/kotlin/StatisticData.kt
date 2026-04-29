package io.github.bstdoom.tagessieg.domain

import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.model.statistic.TagessiegCount

interface StatisticData {
  val matches: Matches

  val wins : TagessiegCount

}

data class DefaultStatisticData(override val matches: Matches) : StatisticData {
  override val wins: TagessiegCount by lazy { TagessiegCount(matches) }
}
