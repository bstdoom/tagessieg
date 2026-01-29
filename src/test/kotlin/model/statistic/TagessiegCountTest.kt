package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.datetime.LocalDate
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TagessiegCountTest {

    @Test
    fun `TagessiegCount should calculate wins correctly`() {
        val match1 = Fixtures.match("2026-01-01", "1:0", "1:0", "1:0") // Winners: J, J, J -> J
        val match2 = Fixtures.match("2026-01-02", "0:1", "0:1", "0:1") // Winners: H, H, H -> H
        val match3 = Fixtures.match("2026-01-03", "1:0", "1:0", "1:1") // Winners: J, J, X -> J
        val match4 = Fixtures.match("2026-01-04", "0:0", "0:0", "0:0") // Winners: X, X, X -> X
        val match5 = Fixtures.match("2026-01-05", "1:0", "0:1", "1:1") // Winners: J, H, X -> X

        val matches = Matches(match1, match2, match3, match4, match5)
        val tagessiegCount = TagessiegCount(matches)

        assertThat(tagessiegCount.j).isEqualTo(2)
        assertThat(tagessiegCount.h).isEqualTo(1)
        assertThat(tagessiegCount.range).isInstanceOf(LocalDateRange.AllTime::class.java)
//        assertThat(tagessiegCount.range.start).isEqualTo(LocalDate(2026, 1, 1))
//        assertThat(tagessiegCount.range.endInclusive).isEqualTo(LocalDate(2026, 1, 5))
    }

    @Test
    fun `TagessiegCount should be serializable polymorphically`() {
        val range = LocalDateRange.ByYear(2026)
        val stats: List<Statistic> = listOf(
          TagessiegCount(range, total = 15, j = 10, h = 5)
        )

        val json = Json.Default.encodeToString(ListSerializer(Statistic.serializer()), stats)

        // Verify type is present in JSON (polymorphic)
        assertThat(json).contains(TagessiegCount.NAME)

        val decoded = Json.Default.decodeFromString(ListSerializer(Statistic.serializer()), json)
        assertThat(decoded).isEqualTo(stats)
    }
}
