package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.model.Match
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

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
        Assertions.assertThat(encoded).contains("2026-01-22", "1:2", "3:1", "0:0", "CSV test")

        val decoded = SerializationFormat.CSV.decodeFromString(Match.Companion.serializer(), encoded)
        Assertions.assertThat(decoded).isEqualTo(match)
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
        Assertions.assertThat(encoded).contains("\"date\":\"2026-01-22\"", "\"game1\":\"1:2\"", "JSON test")

        val decoded = SerializationFormat.JSON.decodeFromString(Match.Companion.serializer(), encoded)
        Assertions.assertThat(decoded).isEqualTo(match)
    }

    @Test
    fun `fromString should be case-insensitive`() {
        Assertions.assertThat(SerializationFormat.fromString("csv")).isEqualTo(SerializationFormat.CSV)
        Assertions.assertThat(SerializationFormat.fromString("JSON")).isEqualTo(SerializationFormat.JSON)
        Assertions.assertThat(SerializationFormat.fromString("unknown")).isNull()
    }
}
