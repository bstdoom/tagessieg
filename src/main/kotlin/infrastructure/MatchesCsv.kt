package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.model.Match
import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import java.nio.file.Path
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

/**
 * Represents a CSV file with matches and allows interaction with it.
 */
class MatchesCsv(
  val file: Path,
  val matches: Matches
) {

  companion object {
    private val serializer = ListSerializer(Match.serializer())

    operator fun invoke(file: Path, createIfMissing: Boolean = true): MatchesCsv {
      if (!file.exists()) {
        require(createIfMissing) { "File does not exist and createIfMissing is false" }
        if (file.parent != null) {
          file.createParentDirectories()
        }
        file.writeText("")
      }

      val content = file.readText()
      return MatchesCsv(
        file = file,
        matches = if (content.isBlank()) {
          Matches()
        } else {
          Matches(decode(content))
        }
      )
    }

    fun encode(matches: Iterable<Match>): String = SerializationFormat.CSVH.encodeToString(serializer, matches.toList()) + "\n"
    fun decode(content: String): List<Match> = SerializationFormat.CSVH.decodeFromString(serializer, content)
  }

  fun save(): MatchesCsv {
    file.writeText(encode(matches.toList()), Charsets.UTF_8)
    return this
  }

  operator fun plus(match: Match): MatchesCsv {
    return MatchesCsv(file, matches + match).save()
  }

  override fun toString(): String {
    return "MatchesCsv(file=$file, matches=$matches)"
  }

  operator fun get(range: LocalDateRange): Matches = Matches(matches.toList(), range)
}

