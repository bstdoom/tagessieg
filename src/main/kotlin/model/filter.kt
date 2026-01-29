package io.github.bstdoom.tagessieg.model

import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.datetime.LocalDate

sealed interface MatchFilter {
  fun test(match: Match): Boolean

  data class AfterDateFilter(val date: LocalDate) : MatchFilter {
    override fun test(match: Match): Boolean = match.date >= date
  }

  data class BeforeDateFilter(val date: LocalDate) : MatchFilter {
    override fun test(match: Match): Boolean = match.date <= date
  }

  data class DateRangeFilter(val start: LocalDate, val end: LocalDate) : MatchFilter {
    override fun test(match: Match): Boolean = match.date in start..end
  }

  data class LocalDateRangeFilter(val range: LocalDateRange) : MatchFilter {
    override fun test(match: Match): Boolean = match.date in range
  }
}

fun afterDate(date: LocalDate) = MatchFilter.AfterDateFilter(date)
fun beforeDate(date: LocalDate) = MatchFilter.BeforeDateFilter(date)
fun dateInRange(start: LocalDate, end: LocalDate) = MatchFilter.DateRangeFilter(start, end)
fun dateInRange(range: ClosedRange<LocalDate>) = MatchFilter.DateRangeFilter(range.start, range.endInclusive)
fun dateInRange(range: LocalDateRange) = MatchFilter.LocalDateRangeFilter(range)


