package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.model.Matches

sealed interface StatisticFactory : (Matches) -> Statistic

@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
@JvmInline
value class StatisticFactories(private val value: List<StatisticFactory>) : List<StatisticFactory> by value, (Matches) -> Statistics {

  companion object {
    val ALL = StatisticFactories(StatisticFactory::class.sealedSubclasses.mapNotNull { it.objectInstance })
  }

  constructor(first: StatisticFactory, vararg rest: StatisticFactory) : this(listOf(first, *rest))

  override fun invoke(matches: Matches): Statistics = Statistics(range = matches.filteredRange, list = value.map { it(matches) })
}

