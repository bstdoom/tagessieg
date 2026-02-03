package io.github.bstdoom.tagessieg

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.core.theme
import io.github.bstdoom.tagessieg.command.CreateStatisticsCmd
import io.github.bstdoom.tagessieg.command.GenerateRevealCmd
import io.github.bstdoom.tagessieg.command.ImportMatchCmd
import io.github.bstdoom.tagessieg.command.InfoCmd
import io.github.bstdoom.tagessieg.command.InitWorkDirCmd

class TagessiegCli : CliktCommand(name = NAME) {
  companion object {
    const val NAME = "tagessieg"

    @JvmStatic
    fun main(vararg args: String) = tagessieg.subcommands(
      InfoCmd(),
      InitWorkDirCmd(),
      ImportMatchCmd(),
      CreateStatisticsCmd(),
      GenerateRevealCmd()
    ).main(args)
  }
  override fun run() {

    TODO("Not yet implemented")
  }

  override val invokeWithoutSubcommand: Boolean = true
  override val allowMultipleSubcommands: Boolean = true
  override fun help(context: Context) = context.theme.info("tagessieg - A tool to generate statistics for KO2")
}
