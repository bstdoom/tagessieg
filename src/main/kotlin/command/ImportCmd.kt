package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat
import io.github.bstdoom.tagessieg.infrastructure.EchoFormat.ERROR
import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import io.github.bstdoom.tagessieg.infrastructure.SerializationFormat
import io.github.bstdoom.tagessieg.model.Game
import io.github.bstdoom.tagessieg.model.Match
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.nio.file.Path
import kotlin.io.path.readText

class ImportCmd : SubCommand(name = NAME, help = "Import a match from a json file.") {

  val file: Path by option(
    "-i", "--input",
    metavar = "FILE",
    help = "The input json file"
  ).convert { Path.of(it) }.required()

  companion object {
    const val NAME = "import"

    private val DATE_FORMAT = LocalDate.Format {
      day()
      char('.')
      monthNumber()
      char('.')
      year()
    }

    private val TITLE_REGEX = Regex("""\d{2}\.\d{2}\.\d{4}""")
    private val GAME_REGEX = Regex("""### Ergebnis Spiel \d \(J:H\)\s*\n\s*\n(\d+:\d+)""")

    @Serializable
    private data class ImportIssue(
      val id: Long,
      val title: String,
      val body: String,
      val labels: List<String> = emptyList(),
      val comments: List<String> = emptyList()
    )
  }

  override fun run() {
    val issue = SerializationFormat.JSON.decodeFromString<ImportIssue>(file.readText(ctx.cs))

    val dateStr = issue.title.let { TITLE_REGEX.find(it)?.value }
      ?: throw IllegalArgumentException("Could not find date in title: ${issue.title}")
    val date = LocalDate.parse(dateStr, DATE_FORMAT)

    val games = issue.body.let { body ->
      GAME_REGEX.findAll(body).map { it.groupValues[1] }.toList()
    }

    if (games.size != 3) {
      throw IllegalArgumentException("Expected 3 games in body, but found ${games.size}")
    }

    val match = Match(
      id = issue.id,
      date = date,
      game1 = Game.parse(games[0]),
      game2 = Game.parse(games[1]),
      game3 = Game.parse(games[2]),
      comment = issue.comments.map { it.trim() }.filter { it.isNotBlank() }.joinToString(", ").takeIf { it.isNotBlank() }
    )

    val matchString = SerializationFormat.CSV.encodeToString<Match>(match)
    val target = ctx.dataDir.resolve("matches.csv")

    dryRun("Dry run: would add '$matchString' to '$target'.") {
      try {
        (MatchesCsv(target) + match).save()
        echof("Match with id ${match.id} added to '$target'.")
      } catch (e: Exception) {
        echo("Error: ${e.message}", err = true)
      }
    }
  }
}
