package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.theme
import io.github.bstdoom.tagessieg.TagessiegContext
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.CSV
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.ERROR
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.JSON
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.PLAIN
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.QUIET

sealed class SubCommand(name: String, private val help: String = "") : CliktCommand(name= name) {
  val ctx by requireObject<TagessiegContext>()

  inline fun <reified T> echof(message: T, format: EchoFormat = ctx.format) = when (format) {
    PLAIN -> echo(message = format.encode(message))
    ERROR -> echo(message = format.encode(message), err = true)
    QUIET -> Unit

    JSON, CSV -> {
      try {
        echo(format.encode(message))
      } catch (e: Exception) {
        echo(e.message ?: "Serialization error", err = true)
      }
    }
  }

  fun dryRun(description : String?, block: () -> Unit) {
    if (!ctx.dryRun) block() else echo("Dry run: $description")
  }

  override fun help(context: Context): String = context.theme.info(help)
}
