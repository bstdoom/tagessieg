package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.model.H
import io.github.bstdoom.tagessieg.model.J
import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.model.X
import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
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
    val tagessiegCount = TagessiegCount.invoke(matches)

    assertThat(tagessiegCount.j).isEqualTo(2)
    assertThat(tagessiegCount.h).isEqualTo(1)
  }

  @Test
  fun `TagessiegCount should be serializable polymorphically`() {
    val range = LocalDateRange.ByYear(2026)
    val stats: List<Statistic> = listOf(
      TagessiegCount(j = 10, h = 5, x = 0)
    )

    val json = Json.Default.encodeToString(ListSerializer(Statistic.serializer()), stats)

    // Verify type is present in JSON (polymorphic)
    assertThat(json).contains(TagessiegCount.NAME)

    val decoded = Json.Default.decodeFromString(ListSerializer(Statistic.serializer()), json)
    assertThat(decoded).isEqualTo(stats)
  }

  @Test
  fun `calc first 7 games of 2026 correctly`() {
    val matches = Matches(
      Fixtures.match("2026-01-02","3:0","2:3","0:2"), // H
      Fixtures.match("2026-01-09","2:3","1:3","1:1"), // H
      Fixtures.match("2026-01-16","2:2","0:2","3:0"), // X
      Fixtures.match("2026-01-23","2:3","3:1","2:3"), // H
      Fixtures.match("2026-01-23","2:2","1:1","2:1"), // J
      Fixtures.match("2026-01-30","2:2","4:2","2:5"), // X
      Fixtures.match("2026-01-30","0:2","3:4","3:2"), // H
    )

    assertThat(matches.map { it.winner }).containsExactly(H, H, X, H, J, X, H)
    val stats = TagessiegCount(matches)

    assertThat(stats.j).isEqualTo(1)
    assertThat(stats.h).isEqualTo(4)
    assertThat(stats.x).isEqualTo(2)
  }
}
