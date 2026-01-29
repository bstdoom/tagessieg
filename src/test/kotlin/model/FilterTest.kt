package io.github.bstdoom.tagessieg.model

import io.github.bstdoom.tagessieg.Fixtures.match
import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FilterTest {

    private val match1 = match("2026-01-01", "1:0", "1:0", "1:0")
    private val match2 = match("2026-01-15", "1:0", "1:0", "1:0")
    private val match3 = match("2026-01-31", "1:0", "1:0", "1:0")
    private val matches = Matches(match1, match2, match3)

    @Test
    fun `after filter`() {
        val filtered = matches.filter(afterDate(LocalDate.parse("2026-01-15")))
        assertThat(filtered).containsExactly(match2, match3)
    }

    @Test
    fun `before filter`() {
        val filtered = matches.filter(beforeDate(LocalDate.parse("2026-01-15")))
        assertThat(filtered).containsExactly(match1, match2)
    }

    @Test
    fun `inRange filter with start and end`() {
        val filtered = matches.filter(dateInRange(LocalDate.parse("2026-01-02"), LocalDate.parse("2026-01-30")))
        assertThat(filtered).containsExactly(match2)
    }

    @Test
    fun `inRange filter with ClosedRange`() {
        val range = LocalDate.parse("2026-01-01")..LocalDate.parse("2026-01-15")
        val filtered = matches.filter(dateInRange(range))
        assertThat(filtered).containsExactly(match1, match2)
    }

    @Test
    fun `inRange filter with LocalDateRange`() {
        val range = LocalDateRange.ByYear(2026)
        val filtered = matches.filter(dateInRange(range))
        assertThat(filtered).containsExactly(match1, match2, match3)
    }
}
