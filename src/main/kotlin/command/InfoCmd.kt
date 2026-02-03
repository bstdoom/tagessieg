package io.github.bstdoom.tagessieg.command

import io.github.bstdoom.tagessieg.model.Game
import io.github.bstdoom.tagessieg.model.Match
import kotlinx.datetime.LocalDate

class InfoCmd : SubCommand(NAME){
  companion object {
    const val NAME = "info"
  }

  override fun run() {
    echo("Info command executed")
    echof(ctx)
  }
}
