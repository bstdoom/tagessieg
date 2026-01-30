package io.github.bstdoom.tagessieg.model.type

import io.github.bstdoom.tagessieg.infrastructure.TagessiegProperties
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed interface LocalDateRange : ClosedRange<LocalDate>, Iterable<YearMonth> {
  companion object {
    val DATE_RANGE = TagessiegProperties.read().configuration.activeDateRange
  }

  override val start: LocalDate
  override val endInclusive: LocalDate

  override operator fun contains(value: LocalDate): Boolean = value in start..endInclusive

  override fun iterator(): Iterator<YearMonth> = object : Iterator<YearMonth> {
    private var current = YearMonth(start.year, start.month)
    private val end = YearMonth(endInclusive.year, endInclusive.month)

    override fun hasNext(): Boolean = current <= end

    override fun next(): YearMonth {
      if (!hasNext()) throw NoSuchElementException()
      val result = current
      current = if (current.month == Month.DECEMBER) {
        YearMonth(current.year + 1, Month.JANUARY)
      } else {
        YearMonth(current.year, Month(current.month.ordinal + 2))
      }
      return result
    }
  }

  @Serializable
  data object AllTime : LocalDateRange {
    override val start: LocalDate = DATE_RANGE.start
    override val endInclusive: LocalDate = DATE_RANGE.endInclusive
  }

  @Serializable
  data object None : LocalDateRange {
    override val start: LocalDate = LocalDate(0,1,1)
    override val endInclusive: LocalDate = LocalDate(0,1,1)
  }

  @Serializable
  data class ByDate(
    override val start: LocalDate,
    override val endInclusive: LocalDate,
    @Transient
    private val check: Boolean = true
  ) : LocalDateRange {
    init {
      if (check) require(start in DATE_RANGE && endInclusive in DATE_RANGE) { "Date range ($start to $endInclusive) must be within allowed date range (${DATE_RANGE.start} to ${DATE_RANGE.endInclusive})" }
      require(start < endInclusive) { "Start date ($start) must be before end date ($endInclusive)" }
    }
  }

  @Serializable
  data class ByYear(val year: Int) : LocalDateRange {
    override val start: LocalDate = LocalDate(year, 1, 1)
    override val endInclusive: LocalDate = LocalDate(year, 12, 31)
    init {
      require(start in DATE_RANGE && endInclusive in DATE_RANGE) { "Date range ($start to $endInclusive) must be within allowed date range (${DATE_RANGE.start} to ${DATE_RANGE.endInclusive})" }
    }
  }

  @Serializable
  data class ByMonth(val year: Int, val month: Month) : LocalDateRange {

    constructor(yearMonth: YearMonth) : this(yearMonth.year, yearMonth.month)


    override val start: LocalDate = LocalDate(year, month, 1)
    override val endInclusive: LocalDate = LocalDate(year, month, month.length(isLeapYear(year)))

    init {
      require(start in DATE_RANGE && endInclusive in DATE_RANGE) { "Date range ($start to $endInclusive) must be within allowed date range (${DATE_RANGE.start} to ${DATE_RANGE.endInclusive})" }
    }

    private fun isLeapYear(year: Int): Boolean {
      return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

    private fun Month.length(leapYear: Boolean): Int = when (this) {
      Month.FEBRUARY -> if (leapYear) 29 else 28
      Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
      else -> 31
    }
  }

  @Serializable
  data class After(override val start: LocalDate) : LocalDateRange {
    override val endInclusive: LocalDate = DATE_RANGE.endInclusive
    init {
      require(start in DATE_RANGE) { "Date range ($start to $endInclusive) must be within allowed date range (${DATE_RANGE.start} to ${DATE_RANGE.endInclusive})" }
    }
  }

  @Serializable
  data class Before(override val endInclusive: LocalDate) : LocalDateRange {
    override val start: LocalDate = DATE_RANGE.start
    init {
      require(endInclusive in DATE_RANGE) { "Date range ($start to $endInclusive) must be within allowed date range (${DATE_RANGE.start} to ${DATE_RANGE.endInclusive})" }
    }
  }

  @Serializable
  data class ByValues(val values: List<LocalDate>) : LocalDateRange {
    constructor(vararg values: LocalDate) : this(values.toList())

    init {
      require(values.isNotEmpty()) { "Values cannot be empty" }
      require(values.all { it in DATE_RANGE }) { "All values must be within allowed date range (${DATE_RANGE.start} to ${DATE_RANGE.endInclusive})" }
    }

    override val start: LocalDate = values.min()
    override val endInclusive: LocalDate = values.max()
  }
}
