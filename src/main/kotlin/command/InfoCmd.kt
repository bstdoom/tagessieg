package io.github.bstdoom.tagessieg.command

class InfoCmd : SubCommand(name = NAME, help = "Prints info about the current context."){
  companion object {
    const val NAME = "info"
  }

  override fun run() {
    echo("Info command executed")
    echof(ctx)
  }
}
