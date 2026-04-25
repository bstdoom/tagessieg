package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.model.Matches
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StatisticTest {
  val matches = Matches(
    Fixtures.match("2026-01-01", "1:0", "1:0", "1:0"), // J wins
    Fixtures.match("2026-01-02", "0:1", "0:1", "0:1"), // H wins
    Fixtures.match("2026-01-03", "1:1", "1:1", "1:1")  // X wins
  )

  @Test
  fun `detect factories automatically`() {
    assertThat(StatisticFactories::ALL).isNotEmpty()
  }

  @Test
  fun `should create all statistics`() {
    val matches = Matches(
      Fixtures.match("2026-01-01", "1:0", "1:0", "1:0"), // J wins
      Fixtures.match("2026-01-02", "0:1", "0:1", "0:1"), // H wins
      Fixtures.match("2026-01-03", "1:1", "1:1", "1:1")  // X wins
    )

    val statistics = StatisticFactories.ALL(matches)

    assertThat(statistics).hasSize(3)
    val tagessiegCount = statistics.filterIsInstance<TagessiegCount>().first()
    assertThat(tagessiegCount.j).isEqualTo(1)
    assertThat(tagessiegCount.h).isEqualTo(1)
    assertThat(tagessiegCount.x).isEqualTo(1)
  }
}
