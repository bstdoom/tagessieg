package io.github.bstdoom.tagessieg.model

import io.github.bstdoom.tagessieg.Fixtures._2026_01_22
import io.github.bstdoom.tagessieg.Fixtures.match
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ArgumentConverter
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

class MatchTest {

  class WinnerConverter : ArgumentConverter {
    override fun convert(source: Any?, context: ParameterContext): Any {
      if (source is String) {
        return Winner.valueOf(source)
      }
      throw IllegalArgumentException("Source must be a string")
    }
  }

  @Test
  fun `verify toString`() {
    assertThat(
      match(
        date = _2026_01_22,
        game1 = "1:1",
        game2 = "2:2",
        game3 = "3:3"
      )
    ).hasToString("Match[X](date=2026-01-22, games=[1:1[X], 2:2[X], 3:3[X]], comment=n/a)")
    assertThat(
      match(
        date = _2026_01_22,
        game1 = "1:2",
        game2 = "1:2",
        game3 = "1:3"
      )
    ).hasToString("Match[H!](date=2026-01-22, games=[1:2[H], 1:2[H], 1:3[H]], comment=n/a)")
  }

  @ParameterizedTest
  @CsvSource(
    "1:1, 2:2, 3:3, X, false",
    "2:1, 2:2, 3:3, J, false",
    "2:1, 3:2, 3:2, J, true",
  )
  fun `determine match result`(
    game1: String,
    game2: String,
    game3: String,
    @ConvertWith(WinnerConverter::class) expectWinner: Winner,
    expectGrandslam: Boolean
  ) {
    val match = match(date = "2026-01-22", game1 = game1, game2 = game2, game3 = game3)
    assertThat(match.winner).isEqualTo(expectWinner)
    assertThat(match.grandSlam).`as` { "expected $match grandSlam=$expectGrandslam" }.isEqualTo(expectGrandslam)
    assertThat(match.id).isPositive()
  }
}
