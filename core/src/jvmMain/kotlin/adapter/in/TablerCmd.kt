package io.github.bstdoom.tagessieg.command

import io.github.bstdoom.tagessieg.adapter.out.GenerateTablerSite
import io.github.bstdoom.tagessieg.adapter.out.MatchesCsvRepository
import io.github.bstdoom.tagessieg.domain.DefaultStatisticData
import kotlin.io.path.exists

class TablerCmd : SubCommand(name = NAME, help = "Generate charts.") {

  companion object {
    const val NAME = "tabler"
  }

  override fun run() {
    val matchesCsv = ctx.workDir.resolve("matches.csv")
    if (!matchesCsv.exists()) {
      echo("No matches.csv found in workDir. Please run 'init' first.", err = true)
      return
    }
    val matches = MatchesCsvRepository(matchesCsv).load()
    val data = DefaultStatisticData(matches)

    val result = GenerateTablerSite()(data).single()

    result.write(ctx.workDir)
    echo("Tabler file written to ${ctx.workDir.resolve(result.path)}")
  }
}
