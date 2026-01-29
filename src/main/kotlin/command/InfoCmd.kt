package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.CliktCommand

class InfoCmd : CliktCommand(NAME){
  companion object {
    const val NAME = "info"
  }

  override fun run() {
    echo("Info command executed")
  }

}
