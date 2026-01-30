package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.model.Game
import io.github.bstdoom.tagessieg.model.Match
import io.github.bstdoom.tagessieg.model.Matches
import io.github.bstdoom.tagessieg.shared.CsvSerialization
import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText

class MatchesCsvTest {

    @Test
    fun `should read matches from existing testCsv`() {
        val testCsvPath = Fixtures.properties.testCsv
        val matchesCsv = MatchesCsv(testCsvPath)

        assertThat(matchesCsv.matches).hasSizeGreaterThan(100)
    }

    @Test
    fun `should create file if it does not exist`(@TempDir tempDir: Path) {
        val newFile = tempDir.resolve("new_matches.csv")
        assertThat(newFile.exists()).isFalse()

        val matchesCsv = MatchesCsv(newFile)

        assertThat(newFile.exists()).isTrue()
        assertThat(newFile.readText()).isEmpty()
        assertThat(matchesCsv.matches).isEmpty()
    }

    @Test
    fun `should add match and persist to file`(@TempDir tempDir: Path) {
        val file = tempDir.resolve("matches.csv")
        val matchesCsv = MatchesCsv(file)
        val match = Match(
            id = 123,
            date = LocalDate(2026, 1, 30),
            game1 = Game(1, 0),
            game2 = Game(2, 0),
            game3 = Game(3, 0),
            comment = "New match"
        )

        val updatedCsv = matchesCsv + match

        assertThat(updatedCsv.matches).hasSize(1)
        assertThat(updatedCsv.matches.first()).isEqualTo(match)

        val persisted = MatchesCsv(file)
        assertThat(persisted.matches).hasSize(1)
        assertThat(persisted.matches.first()).isEqualTo(match)
    }

    @Test
    fun `should fail when adding match with same id`(@TempDir tempDir: Path) {
        val file = tempDir.resolve("matches.csv")
        val match = Match(
            id = 123,
            date = LocalDate(2026, 1, 30),
            game1 = Game(1, 0),
            game2 = Game(2, 0),
            game3 = Game(3, 0)
        )
        val matchesCsv = MatchesCsv(file) + match

        assertThatThrownBy {
            matchesCsv + match.copy(date = LocalDate(2026, 1, 31))
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("Match with id 123 already exists")
    }

    @Test
    fun `should maintain sort order when adding match in the middle`(@TempDir tempDir: Path) {
        val file = tempDir.resolve("matches_sorted.csv")
        val matchesCsv = MatchesCsv(file)

        val match1 = Match(id = 1, date = LocalDate(2026, 1, 1), game1 = Game(0, 0), game2 = Game(0, 0), game3 = Game(0, 0))
        val match3 = Match(id = 3, date = LocalDate(2026, 1, 3), game1 = Game(0, 0), game2 = Game(0, 0), game3 = Game(0, 0))
        val match2 = Match(id = 2, date = LocalDate(2026, 1, 2), game1 = Game(0, 0), game2 = Game(0, 0), game3 = Game(0, 0))

        val updated = matchesCsv + match1 + match3 + match2

        assertThat(updated.matches.toList()).containsExactly(match1, match2, match3)

        val reloaded = MatchesCsv(file)
        assertThat(reloaded.matches.toList()).containsExactly(match1, match2, match3)
    }

    @Test
    fun `should save matches to file`(@TempDir tempDir: Path) {
        val file = tempDir.resolve("save_test.csv")
        val match = Match(id = 1, date = LocalDate(2026, 1, 1), game1 = Game(0, 0), game2 = Game(0, 0), game3 = Game(0, 0))
        val matchesCsv = MatchesCsv(file, Matches(match))

        matchesCsv.save()

        assertThat(file.readText()).contains("1,2026-01-01,0:0,0:0,0:0")
        val reloaded = MatchesCsv(file)
        assertThat(reloaded.matches.toList()).containsExactly(match)
    }
}
