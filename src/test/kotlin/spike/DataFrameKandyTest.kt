package io.github.bstdoom.tagessieg.spike

import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import io.github.bstdoom.tagessieg.model.H
import io.github.bstdoom.tagessieg.model.J
import io.github.bstdoom.tagessieg.model.X
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.kandy.dsl.invoke
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.layers.points
import org.junit.jupiter.api.Test
import java.nio.file.Path

class DataFrameKandyTest {
  @Test
  fun demo() {
    val simpleDataset = mapOf(
      "time, ms" to listOf(12, 87, 130, 149, 200, 221, 250),
      "relativeHumidity" to listOf(0.45, 0.3, 0.21, 0.15, 0.22, 0.36, 0.8),
      "flowOn" to listOf(true, true, false, false, true, false, false),
    )
    // 1. Using the `column()` function with the specified column type and name:
    val timeMs = column<Int>("time, ms")
// 2. Utilizing the String API, similar to the method above, but using String invocation:
    val humidity = "relativeHumidity"<Double>()
// 3. Delegating an unnamed column - its name will be derived from the variable name:
    val flowOn by column<Boolean>()
//    val rand = java.util.Random(503)
//    val n = 1000
//    val data = mapOf(
//      "rating" to List(n / 2) { rand.nextGaussian() } + List(n / 2) { rand.nextGaussian() * 1.5 + 1.5 },
//      "cond" to List(n / 2) { "H" } + List(n / 2) { "J" }
//    )
//
//    val p = letsPlot(data) +
//      geomDensity { x = "rating"; color = "cond" } + ggsize(500, 250)
//
//    ggsave(p, "density.svg")

    plot(simpleDataset) {
      points {
        // Maps values from the "time, ms" column to the X-axis
        x(timeMs)
        // Maps values from the "relativeHumidity" column to the Y-axis
        y(humidity)
        // Sets the size of the points to 4.5
        size = 4.5
        // Maps values from the "flowOn" column to the color attribute
        color(flowOn)
      }
    }.save("foo.svg")
  }

  @Test
  fun `read dataFrame`() {
    // 1. Read the CSV using the existing MatchesCsv infrastructure, which handles the custom Game serialization.
    val csv = MatchesCsv(Path.of("./_data/matches.csv"))

    // 2. Convert the List<Match> to a typed DataFrame.
    // This gives you access to all Match properties (date, game1, winner, grandSlam, etc.)
    val df = csv.toDataFrame()

    df.schema().print()
    println(df)


    // Example of accessing typed properties:
    df.forEach {
      val w = it.winner // Accessing the 'winner' property of Match
      val d = it.date   // Accessing the 'date' property of Match
    }

    // --- Statistics as DataFrames (DataFrame API only) ---

    // 1. TagessiegCount
    val tagessiegCount = df.groupBy { winner }.count()
    println("TagessiegCount:")
    println(tagessiegCount)

    // 2. GrandSlamCount
    val grandSlamCount = df.filter { grandSlam }.groupBy { winner }.count()
    println("GrandSlamCount:")
    println(grandSlamCount)

    // 3. LeagueTable
    // Use simple DataFrame operations to compute the table.
    val gamesJ = df.add("player") { "J" }
      .add("goalsFor") { it.game1.j + it.game2.j + it.game3.j }
      .add("goalsAgainst") { it.game1.h + it.game2.h + it.game3.h }
      .add("S") { listOf(it.game1.winner, it.game2.winner, it.game3.winner).count { w -> w == J } }
      .add("U") { listOf(it.game1.winner, it.game2.winner, it.game3.winner).count { w -> w == X } }
      .add("N") { listOf(it.game1.winner, it.game2.winner, it.game3.winner).count { w -> w == H } }
      .groupBy("player")
      .sum() // Automatically sums all numeric columns: goalsFor, goalsAgainst, S, U, N

    val gamesH = df.add("player") { "H" }
      .add("goalsFor") { it.game1.h + it.game2.h + it.game3.h }
      .add("goalsAgainst") { it.game1.j + it.game2.j + it.game3.j }
      .add("S") { listOf(it.game1.winner, it.game2.winner, it.game3.winner).count { w -> w == H } }
      .add("U") { listOf(it.game1.winner, it.game2.winner, it.game3.winner).count { w -> w == X } }
      .add("N") { listOf(it.game1.winner, it.game2.winner, it.game3.winner).count { w -> w == J } }
      .groupBy("player")
      .sum()

    val leagueTable = (gamesJ.concat(gamesH))
      .rename("goalsFor").into("T+")
      .rename("goalsAgainst").into("T-")
      .add("Diff") { "T+"<Int>() - "T-"<Int>() }
      .add("P") { "S"<Int>() * 3 + "U"<Int>() }
      .sortByDesc("P", "Diff", "T+")

    println("LeagueTable:")
    println(leagueTable)
  }
}
