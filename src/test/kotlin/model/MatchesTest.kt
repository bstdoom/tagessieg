package io.github.bstdoom.tagessieg.model

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.Fixtures.match
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MatchesTest {

    @Test
    fun `Matches should be sorted by date asc`() {
        val match1 = match("2026-01-31", "1:0", "1:0", "1:0")
        val match2 = match("2026-01-01", "1:0", "1:0", "1:0")
        val match3 = match("2026-01-15", "1:0", "1:0", "1:0")

        val matches = Matches(match1, match2, match3)

        assertThat(matches.toList()).containsExactly(match2, match3, match1)
    }

    @Test
    fun `Matches from list should be sorted by date asc`() {
        val match1 = match("2026-01-31", "1:0", "1:0", "1:0")
        val match2 = match("2026-01-01", "1:0", "1:0", "1:0")
        val match3 = match("2026-01-15", "1:0", "1:0", "1:0")

        val matches = Matches(match1, match2, match3)

        assertThat(matches.toList()).containsExactly(match2, match3, match1)
    }

  @Test
  fun `calc winner correctly`() {
    val match = Fixtures.match("2026-01-30","0:2","3:4","3:2") // H
    assertThat(match.game1.winner).isEqualTo(H)
    assertThat(match.game2.winner).isEqualTo(H)
    assertThat(match.game3.winner).isEqualTo(J)
    assertThat(match.winner).isEqualTo(H)
  }
}
