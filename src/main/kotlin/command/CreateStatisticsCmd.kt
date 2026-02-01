package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import io.github.bstdoom.tagessieg.model.statistic.StatisticFactories
import java.nio.file.Path

class CreateStatisticsCmd : SubCommand(NAME) {

  companion object {
    const val NAME = "create-statistics"
  }

  val inputFile: Path? by option(
    "-i", "--input",
    help = "Input file"
  ).path(mustExist = true, canBeDir = false)

  override fun run() {
    val csv = MatchesCsv.invoke(inputFile?:ctx.csvPath)

    val stats = StatisticFactories.ALL(csv.matches)

    echof(stats)
  }
}
