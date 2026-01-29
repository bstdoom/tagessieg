package io.github.bstdoom.tagessieg.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ArgumentConverter
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

class WinnerTest {

  class WinnerConverter : ArgumentConverter {
    override fun convert(source: Any?, context: ParameterContext): Any {
      if (source is String) {
        return Winner.valueOf(source)
      }
      throw IllegalArgumentException("Source must be a string")
    }
  }

  @ParameterizedTest
  @CsvSource(
    "H, H, H",
    "H, J, X",
    "H, X, H",
    "X, H, H",
    "X, J, J",
    "X, X, X",
    "J, H, X",
    "J, J, J",
    "J, X, J",
  )
  fun `adding two winners gives new correct value`(
    @ConvertWith(WinnerConverter::class) first: Winner,
    @ConvertWith(WinnerConverter::class) second: Winner,
    @ConvertWith(WinnerConverter::class) expected: Winner
  ) {
    assertThat(first + second).isEqualTo(expected)
  }
}
