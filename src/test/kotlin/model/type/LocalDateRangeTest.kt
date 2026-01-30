package io.github.bstdoom.tagessieg.model.type

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class LocalDateRangeTest {

  companion object {
    @JvmStatic
    fun localDateRangeVariants() = listOf(
      LocalDateRange.AllTime,
      LocalDateRange.ByDate(LocalDate(2023, 1, 1), LocalDate(2023, 1, 10)),
      LocalDateRange.ByYear(2024),
      LocalDateRange.ByMonth(2024, Month.FEBRUARY),
      LocalDateRange.After(LocalDate(2023, 6, 1)),
      LocalDateRange.Before(LocalDate(2023, 6, 1)),
      LocalDateRange.ByValues(LocalDate(2023, 1, 5), LocalDate(2023, 1, 1), LocalDate(2023, 1, 10))
    )
  }

  @ParameterizedTest
  @MethodSource("localDateRangeVariants")
  fun `serialization should work for all variants`(range: LocalDateRange) {
    val json = Json.encodeToString(range)
    val decoded = Json.decodeFromString<LocalDateRange>(json)
    assertThat(decoded).isEqualTo(range)
  }

  @Test
  fun `iterator should return months in range`() {
    val range = LocalDateRange.ByDate(LocalDate(2024, 3, 1), LocalDate(2025, 4, 1))
    val months = range.toList()

    assertThat(months).containsExactly(
      YearMonth(2024, Month.MARCH),
      YearMonth(2024, Month.APRIL),
      YearMonth(2024, Month.MAY),
      YearMonth(2024, Month.JUNE),
      YearMonth(2024, Month.JULY),
      YearMonth(2024, Month.AUGUST),
      YearMonth(2024, Month.SEPTEMBER),
      YearMonth(2024, Month.OCTOBER),
      YearMonth(2024, Month.NOVEMBER),
      YearMonth(2024, Month.DECEMBER),
      YearMonth(2025, Month.JANUARY),
      YearMonth(2025, Month.FEBRUARY),
      YearMonth(2025, Month.MARCH),
      YearMonth(2025, Month.APRIL)
    )
  }

  @Test
  fun `iterator should work for ByMonth`() {
    val range = LocalDateRange.ByMonth(2024, Month.FEBRUARY)
    val months = range.toList()

    assertThat(months).containsExactly(YearMonth(2024, Month.FEBRUARY))
  }

  @Test
  fun `iterator should work for ByYear`() {
    val range = LocalDateRange.ByYear(2024)
    val months = range.toList()

    assertThat(months).hasSize(12)
    assertThat(months.first()).isEqualTo(YearMonth(2024, Month.JANUARY))
    assertThat(months.last()).isEqualTo(YearMonth(2024, Month.DECEMBER))
  }

  @Test
  fun `AllTime should contain any date`() {
    val range = LocalDateRange.AllTime
    assertThat(range.contains(LocalDate(2023, 1, 1))).isTrue()
    assertThat(range.contains(LocalDate(2026, 12, 31))).isTrue()
    assertThat(range.contains(LocalDate(2024, 6, 15))).isTrue()
  }

  @Nested
  inner class ByDateTests {
    @Test
    fun `ByDate should contain date within range`() {
      val range = LocalDateRange.ByDate(LocalDate(2023, 1, 1), LocalDate(2023, 1, 10))

      assertThat(range.contains(LocalDate(2023, 1, 1))).isTrue()
      assertThat(range.contains(LocalDate(2023, 1, 5))).isTrue()
      assertThat(range.contains(LocalDate(2023, 1, 10))).isTrue()
      assertThat(range.contains(LocalDate(2022, 12, 31))).isFalse()
      assertThat(range.contains(LocalDate(2023, 1, 11))).isFalse()
    }

    @Test
    fun `ByDate should throw exception if start is not before end`() {
      assertThatThrownBy {
        LocalDateRange.ByDate(LocalDate(2023, 1, 10), LocalDate(2023, 1, 1))
      }.isInstanceOf(IllegalArgumentException::class.java)

      assertThatThrownBy {
        LocalDateRange.ByDate(LocalDate(2023, 1, 1), LocalDate(2023, 1, 1))
      }.isInstanceOf(IllegalArgumentException::class.java)
    }
  }

  @Test
  fun `ByYear should contain all dates in that year`() {
    val range = LocalDateRange.ByYear(2024)
    assertThat(range.start).isEqualTo(LocalDate(2024, 1, 1))
    assertThat(range.endInclusive).isEqualTo(LocalDate(2024, 12, 31))
    assertThat(range.contains(LocalDate(2024, 1, 1))).isTrue()
    assertThat(range.contains(LocalDate(2024, 6, 15))).isTrue()
    assertThat(range.contains(LocalDate(2024, 12, 31))).isTrue()
    assertThat(range.contains(LocalDate(2023, 12, 31))).isFalse()
    assertThat(range.contains(LocalDate(2025, 1, 1))).isFalse()
  }

  @Nested
  inner class ByMonthTests {
    @Test
    fun `ByMonth should contain all dates in that month`() {
      val range = LocalDateRange.ByMonth(2024, Month.FEBRUARY) // Leap year
      assertThat(range.start).isEqualTo(LocalDate(2024, 2, 1))
      assertThat(range.endInclusive).isEqualTo(LocalDate(2024, 2, 29))
      assertThat(range.contains(LocalDate(2024, 2, 1))).isTrue()
      assertThat(range.contains(LocalDate(2024, 2, 29))).isTrue()
      assertThat(range.contains(LocalDate(2024, 3, 1))).isFalse()
    }

    @Test
    fun `ByMonth should handle non-leap year February`() {
      val range = LocalDateRange.ByMonth(2023, Month.FEBRUARY)
      assertThat(range.endInclusive).isEqualTo(LocalDate(2023, 2, 28))
    }

    @Test
    fun `ByMonth should handle 30-day months`() {
      val range = LocalDateRange.ByMonth(2023, Month.APRIL)
      assertThat(range.endInclusive).isEqualTo(LocalDate(2023, 4, 30))
    }
  }

  @Test
  fun `After should contain all dates from start`() {
    val range = LocalDateRange.After(LocalDate(2023, 6, 1))
    assertThat(range.contains(LocalDate(2023, 6, 1))).isTrue()
    assertThat(range.contains(LocalDate(2023, 12, 31))).isTrue()
    assertThat(range.contains(LocalDate(2026, 12, 31))).isTrue()
    assertThat(range.contains(LocalDate(2023, 5, 31))).isFalse()
  }

  @Test
  fun `Before should contain all dates up to end`() {
    val range = LocalDateRange.Before(LocalDate(2023, 6, 1))
    assertThat(range.contains(LocalDate(2023, 6, 1))).isTrue()
    assertThat(range.contains(LocalDate(2023, 1, 1))).isTrue()
    assertThat(range.contains(LocalDate(2023, 1, 1))).isTrue()
    assertThat(range.contains(LocalDate(2023, 6, 2))).isFalse()
  }

  @Test
  fun `ByValues should use min and max of values`() {
    val range = LocalDateRange.ByValues(
      LocalDate(2023, 1, 5),
      LocalDate(2023, 1, 1),
      LocalDate(2023, 1, 10)
    )
    assertThat(range.start).isEqualTo(LocalDate(2023, 1, 1))
    assertThat(range.endInclusive).isEqualTo(LocalDate(2023, 1, 10))
    assertThat(range.contains(LocalDate(2023, 1, 1))).isTrue()
    assertThat(range.contains(LocalDate(2023, 1, 5))).isTrue()
    assertThat(range.contains(LocalDate(2023, 1, 10))).isTrue()
    assertThat(range.contains(LocalDate(2022, 12, 31))).isFalse()
    assertThat(range.contains(LocalDate(2023, 1, 11))).isFalse()
  }

  @Test
  fun `ByValues should throw exception if values are empty`() {
    assertThatThrownBy {
      LocalDateRange.ByValues(emptyList())
    }.isInstanceOf(IllegalArgumentException::class.java)
  }
}
