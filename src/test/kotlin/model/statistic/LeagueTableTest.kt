package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.model.H
import io.github.bstdoom.tagessieg.model.J
import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.model.X
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LeagueTableTest {

  @Test
  fun `calc table for single match`() {
    val match = Fixtures.match("2026-01-01", "1:0", "1:0", "1:0")
    assertThat(match.goals).isEqualTo(3 to 0)
    assertThat(match.game1.winner).isEqualTo(J)
    assertThat(match.game2.winner).isEqualTo(J)
    assertThat(match.game3.winner).isEqualTo(J)
    assertThat(match.winner).isEqualTo(J)
    assertThat(match.grandSlam).isTrue
    val matches = Matches(match)
    val table = LeagueTable(matches = matches)


    val j = table.rows.find { it.player == J }!!
    assertThat(j.goals).isEqualTo(3 to 0)
    assertThat(j.diff).isEqualTo(3)
    assertThat(j.results).isEqualTo(Triple(3, 0, 0))
    assertThat(j.points).isEqualTo(9)
  }

  @Test
  fun `calc league for first seven matches in 2026`() {
    val matches = Matches(
      Fixtures.match("2026-01-02", "3:0", "2:3", "0:2"), // H
      Fixtures.match("2026-01-09", "2:3", "1:3", "1:1"), // H
      Fixtures.match("2026-01-16", "2:2", "0:2", "3:0"), // X
      Fixtures.match("2026-01-23", "2:3", "3:1", "2:3"), // H
      Fixtures.match("2026-01-23", "2:2", "1:1", "2:1"), // J
      Fixtures.match("2026-01-30", "2:2", "4:2", "2:5"), // X
      Fixtures.match("2026-01-30", "0:2", "3:4", "3:2"), // H
    )

    assertThat(matches.map { it.winner }).containsExactly(H, H, X, H, J, X, H)
    val stats = LeagueTable(matches)

    with(stats[J]) {
      assertThat(results).isEqualTo(Triple(6, 5, 10))
      assertThat(points).isEqualTo(23)
      assertThat(goals).isEqualTo(40 to 44)
      assertThat(diff).isEqualTo(-4)
    }

    with(stats[H]) {
      assertThat(results).isEqualTo(Triple(10, 5, 6))
      assertThat(points).isEqualTo(35)
      assertThat(goals).isEqualTo(44 to 40)
      assertThat(diff).isEqualTo(4)
    }

  }
}
