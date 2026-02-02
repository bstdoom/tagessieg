package io.github.bstdoom.tagessieg.model

import io.github.bstdoom.tagessieg.model.type.LocalDateRange

/**
 * Holds a list of matches sorted by date.
 */
class Matches private constructor(
  private val sortedValue: List<Match>,
  val filteredRange: LocalDateRange,
  val containedRange: LocalDateRange = if (sortedValue.isEmpty()) LocalDateRange.None else LocalDateRange.ByValues(sortedValue.map { it.date })
) : Iterable<Match> by sortedValue {

  constructor(vararg matches: Match) : this(matches.toList().sorted(), LocalDateRange.AllTime)

  companion object {
    operator fun invoke(
      value: List<Match>,
      range: LocalDateRange = LocalDateRange.AllTime
    ): Matches = Matches(value.sorted().filter { it.date in range }, range)

    operator fun invoke(
      vararg value: Match
    ): Matches = invoke(value.toList(), range = LocalDateRange.AllTime)
  }



  fun filter(filter: MatchFilter) = Matches(sortedValue.filter { filter.test(it) }, filteredRange)

  operator fun plus(match: Match): Matches {
    if (sortedValue.any { it.id == match.id }) {
      throw IllegalArgumentException("Match with id ${match.id} already exists")
    }
    return Matches((sortedValue + match).sorted(), filteredRange)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Matches

    if (sortedValue != other.sortedValue) return false
    if (filteredRange != other.filteredRange) return false

    return true
  }

  override fun hashCode(): Int {
    var result = sortedValue.hashCode()
    result = 31 * result + filteredRange.hashCode()
    return result
  }

  override fun toString(): String {
    return "Matches(sortedValue=$sortedValue, range=$filteredRange)"
  }

}
