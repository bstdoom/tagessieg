package io.github.bstdoom.tagessieg

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.output.MordantHelpFormatter
import com.github.ajalt.clikt.parameters.groups.default
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.single
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import io.github.bstdoom.tagessieg.command.ImportCmd
import io.github.bstdoom.tagessieg.command.InfoCmd
import io.github.bstdoom.tagessieg.command.InitCmd
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat
import java.nio.file.Path

class TagessiegCli(initSubcommands: Boolean = true) : CliktCommand(name = NAME) {
  companion object {
    const val NAME = "tagessieg"

    @JvmStatic
    fun main(vararg args: String) = TagessiegCli().main(args)

  }

  override fun help(context: Context) = context.theme.info("tagessieg - A tool to generate statistics for KO2")

  init {
    context {
      helpFormatter = { MordantHelpFormatter(it, showDefaultValues = true) }
    }
    if (initSubcommands) {
      subcommands(
        InfoCmd(),
        InitCmd(),
        ImportCmd(),
      )
    }
  }

  private val workDirectory: Path by option(
    "-w", "--workdir",
    help = "The workDir, relative to rootDir."
  ).convert { Path.of(it) }.default(Path.of("build/out"))

  private val dryRun by option(
    "-n", "--dry-run",
    help = "Perform a trial run with no changes made."
  ).flag(default = false)

  private val echoFormat: EchoFormat by mutuallyExclusiveOptions(
    option("-f", "--format", help = "The output format for `echof()`.").enum<EchoFormat> { it.name.lowercase() },
    option("-q", "--quiet", help = "Suppress non-essential output.").flag(default = false).convert { EchoFormat.QUIET }
  ).single().default(EchoFormat.PLAIN)

  override fun run() {
    // Set context once with global options and properties
    val rootDir = Path.of(".")
    currentContext.obj = TagessiegContext(
      rootDir = rootDir,
      workDir = rootDir.resolve(workDirectory),
      format = echoFormat,
      dryRun = dryRun
    )
  }

  override val invokeWithoutSubcommand: Boolean = true
  override val allowMultipleSubcommands: Boolean = true

}
