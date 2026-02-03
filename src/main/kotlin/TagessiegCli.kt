package io.github.bstdoom.tagessieg

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.core.theme
import io.github.bstdoom.tagessieg.command.CreateStatisticsCmd
import io.github.bstdoom.tagessieg.command.GenerateRevealCmd
import io.github.bstdoom.tagessieg.command.ImportMatchCmd
import io.github.bstdoom.tagessieg.command.InfoCmd
import io.github.bstdoom.tagessieg.command.InitWorkDirCmd
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat
import io.github.bstdoom.tagessieg.infrastructure.TagessiegProperties
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.nio.file.Path

class TagessiegCli : CliktCommand(name = NAME) {
  companion object {
    const val NAME = "tagessieg"

    @JvmStatic
    fun main(vararg args: String) = TagessiegCli().subcommands(
      InfoCmd(),
      InitWorkDirCmd(),
      ImportMatchCmd(),
      CreateStatisticsCmd(),
      GenerateRevealCmd()
    ).main(args)

    @Serializable
    data class TagessiegContext(

      @Contextual
      val rootDir: Path,

      @Contextual
      val workDir: Path,

      val format: EchoFormat,

      val properties: TagessiegProperties,
      val dryRun: Boolean,
      val test: Boolean,
    ) {
      companion object {
      }

      @Transient
      val cs = Charsets.UTF_8

      @Transient
      val quiet = format == EchoFormat.QUIET

      @Transient
      val csvPath = if (!test) properties.mainCsv else properties.testCsv
    }
  }
  override fun run() {

    TODO("Not yet implemented")
  }

  override val invokeWithoutSubcommand: Boolean = true
  override val allowMultipleSubcommands: Boolean = true
  override fun help(context: Context) = context.theme.info("tagessieg - A tool to generate statistics for KO2")
}
