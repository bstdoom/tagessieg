package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.CSV
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.ERROR
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.JSON
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.PLAIN
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.QUIET

abstract class SubCommand(name: String) : CliktCommand(name) {
  val ctx by requireObject<RootCtx>()

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
}
