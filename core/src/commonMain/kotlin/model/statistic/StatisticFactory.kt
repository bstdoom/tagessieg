package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.model.Matches

sealed interface StatisticFactory {
  operator fun invoke(matches: Matches): Statistic
}

class StatisticFactories(private val value: List<StatisticFactory>) : List<StatisticFactory> by value {

  companion object {
    val ALL = StatisticFactories(listOf(LeagueTable, GrandSlamCount, TagessiegCount))
  }

  constructor(first: StatisticFactory, vararg rest: StatisticFactory) : this(listOf(first, *rest))

  operator fun invoke(matches: Matches): Statistics = Statistics(range = matches.filteredRange, list = value.map { it(matches) })
}
