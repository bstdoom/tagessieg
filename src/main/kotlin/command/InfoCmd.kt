package io.github.bstdoom.tagessieg.command

import io.github.bstdoom.tagessieg.model.Game
import io.github.bstdoom.tagessieg.model.Match
import kotlinx.datetime.LocalDate

class InfoCmd : SubCommand(NAME){
  companion object {
    const val NAME = "info"
  }

  override fun run() {
    val match = Match(id=1, date = LocalDate(2026,1,30), game1 = Game(1,2), game2 = Game(3,4), game3 = Game(5,6), comment = "test")
    echo("Info command executed")
    echof(ctx)
    echof(match)
  }
}
