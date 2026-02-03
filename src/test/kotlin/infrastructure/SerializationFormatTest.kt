package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.model.Match
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.nio.file.Path

class SerializationFormatTest {

  @Test
  fun `CSV format should encode and decode Match`() {
    val match = Fixtures.match(
      date = "2026-01-22",
      game1 = "1:2",
      game2 = "3:1",
      game3 = "0:0",
      comment = "CSV test"
    )

    val encoded = SerializationFormat.CSV.encodeToString(Match.Companion.serializer(), match)
    assertThat(encoded).contains("2026-01-22", "1:2", "3:1", "0:0", "CSV test")

    val decoded = SerializationFormat.CSV.decodeFromString(Match.Companion.serializer(), encoded)
    assertThat(decoded).isEqualTo(match)
  }

  @Test
  fun `JSON format should encode and decode Match`() {
    val match = Fixtures.match(
      date = "2026-01-22",
      game1 = "1:2",
      game2 = "3:1",
      game3 = "0:0",
      comment = "JSON test"
    )

    val encoded = SerializationFormat.JSON.encodeToString(Match.Companion.serializer(), match)
    assertThat(encoded).contains("\"date\":\"2026-01-22\"", "\"game1\":\"1:2\"", "JSON test")

    val decoded = SerializationFormat.JSON.decodeFromString(Match.Companion.serializer(), encoded)
    assertThat(decoded).isEqualTo(match)
  }

  @Test
  fun `fromString should be case-insensitive`() {
    assertThat(SerializationFormat.fromString("csv")).isEqualTo(SerializationFormat.CSV)
    assertThat(SerializationFormat.fromString("JSON")).isEqualTo(SerializationFormat.JSON)
    assertThat(SerializationFormat.fromString("unknown")).isNull()
  }

  @Nested
  inner class PathSerializationTest {

    @Test
    fun `should serialize and deserialize Path as JSON`() {
      val wrapper = PathWrapper(Path.of("some/relative/path"))
      val json = SerializationFormat.JSON.encodeToString(wrapper)

      assertThat(json).contains("\"path\":\"some/relative/path\"")

      val decoded = SerializationFormat.JSON.decodeFromString<PathWrapper>(json)
      assertThat(decoded.path).isEqualTo(wrapper.path)
    }

    @Test
    fun `should serialize and deserialize Path as CSV`() {
      val wrapper = PathWrapper(Path.of("some/relative/path"))
      val csv = SerializationFormat.CSV.encodeToString(wrapper)

      assertThat(csv.trim()).isEqualTo("some/relative/path")

      val decoded = SerializationFormat.CSV.decodeFromString<PathWrapper>(csv)
      assertThat(decoded.path).isEqualTo(wrapper.path)
    }
  }

  @Serializable
  data class PathWrapper(
    @Contextual
    val path: Path
  )

  @Test
  fun `serialize a kotlinx LocalDateRange`() {
    LocalDateRange(LocalDate(2023, 1, 1), LocalDate(2023, 1, 10)).let { range ->
      assertThat(SerializationFormat.CSV.encodeToString(range)).isEqualTo("2023-01-01,2023-01-10")
    }
  }
}
