package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.Fixtures._2026_01_22
import io.github.bstdoom.tagessieg.Fixtures.match
import io.github.bstdoom.tagessieg.model.Matches
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.writeText

class CsvSerializationTest {

  @Test
  fun `encode decode single match`() {
    val match = match(
      date = _2026_01_22,
      game1 = "1:2",
      game2 = "1:2",
      game3 = "1:3",
      comment = "test, with, commas"
    )
    val csv = CsvSerialization.encode(match)
    println("---")
    print(csv)
    println("---")
    assertThat(csv).endsWith("\n")
    assertThat(CsvSerialization.decode(csv).first()).isEqualTo(match)
  }

  @Test
  fun readFile(@TempDir tempDir: Path) {
    if (Fixtures.testData.isEmpty()) {
      println("[DEBUG_LOG] Skipping readFile test because Fixtures.testData is empty")
      return
    }
    val file: Path = tempDir.resolve("test.csv").apply {
      writeText(Fixtures.testData)
    }

    val matches = CsvSerialization.decodeFromPath(file)
    assertThat(matches).isNotEmpty

    assertThat(matches).isNotEmpty
    matches.forEach { println(it) }

  }

  @Test
  fun `write to path`(@TempDir tempDir: Path) {
    val path = tempDir.resolve("output.csv")
    val matches = Matches(
      match(date = _2026_01_22, game1 = "1:2", game2 = "1:2", game3 = "1:3", comment = "comment1"),
      match(date = _2026_01_22, game1 = "3:3", game2 = "4:4", game3 = "5:5", comment = "comment2")
    )

    CsvSerialization.encodeToPath(path, matches)

    val readMatches = CsvSerialization.decodeFromPath(path)
    assertThat(readMatches.toList()).isEqualTo(matches.toList())
  }

  @Test
  fun `append to path`(@TempDir tempDir: Path) {
    val path = tempDir.resolve("append.csv")
    val initialMatches = Matches(
      match(date = _2026_01_22, game1 = "1:2", game2 = "1:2", game3 = "1:3", comment = "initial")
    )
    val additionalMatches = Matches(
      match(date = _2026_01_22, game1 = "3:3", game2 = "4:4", game3 = "5:5", comment = "additional")
    )

    CsvSerialization.encodeToPath(path, initialMatches)
    CsvSerialization.appendToPath(path, additionalMatches)

    val readMatches = CsvSerialization.decodeFromPath(path)
    assertThat(readMatches.toList()).hasSize(2)
    assertThat(readMatches.toList()).isEqualTo(initialMatches.toList() + additionalMatches.toList())
  }

  @Test
  fun `append to non-existing path`(@TempDir tempDir: Path) {
    val path = tempDir.resolve("non-existing.csv")
    val matches = Matches(
      match(date = _2026_01_22, game1 = "1:2", game2 = "1:2", game3 = "1:3", comment = "new")
    )

    CsvSerialization.appendToPath(path, matches)

    val readMatches = CsvSerialization.decodeFromPath(path)
    assertThat(readMatches.toList()).isEqualTo(matches.toList())
  }

  @Test
  fun `append to path in non-existing directory`(@TempDir tempDir: Path) {
    val path = tempDir.resolve("new-dir/append.csv")
    val matches = Matches(
      match(date = _2026_01_22, game1 = "1:2", game2 = "1:2", game3 = "1:3", comment = "new")
    )

    CsvSerialization.appendToPath(path, matches)

    val readMatches = CsvSerialization.decodeFromPath(path)
    assertThat(readMatches.toList()).isEqualTo(matches.toList())
  }
}
