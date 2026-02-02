package io.github.bstdoom.tagessieg.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ArgumentConverter
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

class GameTest {

  class WinnerConverter : ArgumentConverter {
    override fun convert(source: Any?, context: ParameterContext): Any {
      if (source is String) {
        return Winner.valueOf(source)
      }
      throw IllegalArgumentException("Source must be a string")
    }
  }

  @Test
  fun `parses score string`() {
    val result = Game.parse("3:1")
    assertThat(result.j).isEqualTo(3)
    assertThat(result.h).isEqualTo(1)
  }

  @Test
  fun `parses score string with spaces`() {
    val result = Game.parse(" 2 : 4 ")
    assertThat(result.j).isEqualTo(2)
    assertThat(result.h).isEqualTo(4)
  }

  @ParameterizedTest
  @CsvSource("3:1,J", "1:3,H", "2:2,X")
  fun `determines result correctly`(goals: String, @ConvertWith(WinnerConverter::class) expected: Winner) {
    assertThat(Game.parse(goals).winner).isEqualTo(expected)
  }

  @Test
  fun `toString returns colon separated goals`() {
    assertThat(Game(5, 2).toString()).isEqualTo("5:2[J]")
  }

  @Test
  fun `throws exception on invalid input`() {
    assertThatThrownBy { Game.parse("invalid") }
      .isInstanceOf(IllegalArgumentException::class.java)
  }

  @ParameterizedTest
  @CsvSource(
    "1:2, 1",
    "3:1, 2",
    "3:0, 3",
    "1:1, 0"
  )
  fun `calc diff`(game: String, expected: Int) {
    assertThat(Game.Companion.parse(game).diff).isEqualTo(expected)
  }
}
