package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.obj
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat
import io.github.bstdoom.tagessieg.infrastructure.TagessiegProperties
import java.nio.file.Path

class RootCmd : CliktCommand(name = NAME) {

  companion object {
    const val NAME = "tagessieg"
  }

  private val workDirectory: Path by option(
    "-w", "--workdir",
    help = "The workDir, relative base for all paths, defaults to '.'"
  ).convert { Path.of(it) }.default(Path.of("build/out"))

  private val quiet by option(
    "-q", "--quiet",
    help = "Suppress non-essential output, precedence over `--format`."
  ).flag(default = false)

  private val dryRun by option(
    "-n", "--dry-run",
    help = "Perform a trial run with no changes made."
  ).flag(default = false)

  private val format: EchoFormat by option(
    "-f", "--format",
    help = "The output format for `echo()`, options are '${EchoFormat.entries.map { it.name.lowercase() }.joinToString()}', defaults to 'plain'."
  ).convert { EchoFormat.of(it) }.default(EchoFormat.PLAIN)

  private val test: Boolean by option(
    "-t", "--test",
    help = "Test mode"
  ).flag(default = false)

  override fun run() {
    // Set context once with global options and properties
    currentContext.obj = RootCtx(
      workDir = workDirectory,
      properties = TagessiegProperties.read(workDirectory),
      format = if (quiet) EchoFormat.QUIET else format,
      dryRun = dryRun,
      test = test
    )
  }

  override val invokeWithoutSubcommand: Boolean = true
  override val allowMultipleSubcommands: Boolean = true
}
