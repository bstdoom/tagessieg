package io.github.bstdoom.tagessieg.shared

import io.github.bstdoom.tagessieg.model.Match
import io.github.bstdoom.tagessieg.model.Matches
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.csv.Csv
import java.io.RandomAccessFile
import java.nio.file.Path
import kotlin.io.path.appendText
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

@OptIn(ExperimentalSerializationApi::class)
data object CsvSerialization {
  fun encode(match: Match): String = encode(listOf(match))
  fun encode(matches: Iterable<Match>): String = Csv.Default.encodeToString(ListSerializer(Match.Companion.serializer()), matches.toList()) + "\n"

  fun decode(input: String): List<Match> = Csv.Default.decodeFromString(ListSerializer(Match.Companion.serializer()), input)

  fun decodeFromPath(path: Path): Matches = Matches(
      decode(path.readText(Charsets.UTF_8))
  )

  fun encodeToPath(path: Path, matches: Matches): Path {
    path.createParentDirectories()
    path.writeText(encode(matches.toList()), Charsets.UTF_8)
    return path
  }

  fun appendToPath(path: Path, matches: Matches): Path {
    val csv = encode(matches)
    if (!path.exists()) {
      path.createParentDirectories()
      path.writeText(csv, Charsets.UTF_8)
      return path
    }

    val lastByte = RandomAccessFile(path.toFile(), "r").use { raf ->
      if (raf.length() > 0) {
        raf.seek(raf.length() - 1)
        raf.read().toByte()
      } else {
        null
      }
    }

    if (lastByte != null && lastByte != '\n'.code.toByte()) {
      path.appendText("\n", Charsets.UTF_8)
    }
    path.appendText(csv, Charsets.UTF_8)
    return path
  }
}
