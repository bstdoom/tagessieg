package io.github.bstdoom.tagessieg.command

class InfoCmd : SubCommand(NAME){
  companion object {
    const val NAME = "info"
  }

  override fun run() {
    echo("Info command executed")
    echof(ctx)
  }
}
