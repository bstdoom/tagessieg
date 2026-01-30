package io.github.bstdoom.tagessieg

import io.github.bstdoom.tagessieg.infrastructure.TagessiegProperties
import io.github.bstdoom.tagessieg.model.Game
import io.github.bstdoom.tagessieg.model.Match
import kotlinx.datetime.LocalDate
import kotlin.io.path.readText

data object Fixtures {
  val properties = TagessiegProperties.read()
  val _2026_01_22 = "2026-01-22"
  val LD_2026_01_22 = LocalDate.parse(_2026_01_22)

  fun match(
    date: String,
    game1: String, game2: String, game3: String,
    comment: String? = null
  ) = Match(date = LocalDate.parse(date), game1 = Game.parse(game1), game2 = Game.parse(game2), game3 = Game.parse(game3), comment = comment)

  val testData = properties.testCsv.readText()
}
