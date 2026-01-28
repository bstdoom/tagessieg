package io.github.bstdoom.tagessieg

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import io.github.bstdoom.tagessieg.TagessiegCli.Companion.CLI

fun main(vararg args: String) = CLI.main(args)

class TagessiegCli : CliktCommand(name = NAME) {
  companion object {
    const val NAME = "tagessieg"
    const val SCOPE_TEST = "Scope: Test"

    val CLI = TagessiegCli().subcommands(
      //ImportIssueCommand(),
    )
  }

  override fun run() = Unit
}
