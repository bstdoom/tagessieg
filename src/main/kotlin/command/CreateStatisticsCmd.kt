package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

class CreateStatisticsCmd : CliktCommand(NAME) {

  companion object {
    const val NAME = "create-statistics"
  }

  val inputFile: File by option(help = "Input file")
    .file(mustExist = true, canBeDir = false)
    .required()

  override fun run() {
    TODO("Not yet implemented")
  }
}
