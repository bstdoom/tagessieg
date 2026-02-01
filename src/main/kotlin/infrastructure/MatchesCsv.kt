package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.model.Match
import io.github.bstdoom.tagessieg.model.Matches
import kotlinx.serialization.builtins.ListSerializer
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
    operator fun invoke(file: Path): MatchesCsv {
      if (!file.exists()) {
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
          Matches(SerializationFormat.CSV.decodeFromString(ListSerializer(Match.serializer()), content))
        }
      )
    }
  }

  fun save(): MatchesCsv {
    CsvSerialization.encodeToPath(file, matches)
    return this
  }

  operator fun plus(match: Match): MatchesCsv {
    return MatchesCsv(file, matches + match).save()
  }

  override fun toString(): String {
    return "MatchesCsv(matches=$matches, file=$file)"
  }
}

