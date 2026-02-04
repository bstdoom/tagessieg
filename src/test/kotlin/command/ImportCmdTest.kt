package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import io.github.bstdoom.tagessieg.TagessiegCli
import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import io.github.bstdoom.tagessieg.infrastructure.SerializationFormat
import kotlinx.serialization.decodeFromString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

@Disabled("Not yet implemented")
class ImportCmdTest {

  private val cli = TagessiegCli(false).subcommands(ImportCmd())

  @Test
  fun `should import match from json`(@TempDir tempDir: Path) {
    val jsonPath = tempDir.resolve("match.json")
    jsonPath.writeText("""
      {
        "id": ${System.currentTimeMillis()},
        "title": "Spieltag: 29.01.2026",
        "body": "### Ergebnis Spiel 1 (J:H)\n\n0:0\n\n### Ergebnis Spiel 2 (J:H)\n\n0:0\n\n### Ergebnis Spiel 3 (J:H)\n\n0:0",
        "labels": ["Scope: Test"]
      }
    """.trimIndent())
    val issueId = SerializationFormat.JSON.decodeFromString<kotlinx.serialization.json.JsonObject>(jsonPath.readText())["id"]?.toString()?.toLong()
    val applicationProps = tempDir.resolve("application.properties")
    applicationProps.writeText("""
            tagessieg.data.matches.main=data/main.csv
            tagessieg.data.matches.test=test.csv
            tagessieg.config.daterange=2023-01-01,2026-12-31
        """.trimIndent())

    val result = cli.test("-w $tempDir -f csv ${ImportCmd.NAME} --input $jsonPath")

    assertThat(result.statusCode).isEqualTo(0)
    assertThat(result.stdout).contains("Match with id $issueId added to matches-test.csv")

    val testCsv = tempDir.resolve("data/matches-test.csv")
    assertThat(testCsv.exists()).isTrue()
    val matches = MatchesCsv(testCsv).matches
    assertThat(matches).anySatisfy { assertThat(it.id).isEqualTo(issueId) }
  }

  @Test
  fun `should respect dry-run`(@TempDir tempDir: Path) {
    val jsonPath = tempDir.resolve("match.json")
    val id = System.currentTimeMillis() + 1
    jsonPath.writeText("""
      {
        "id": $id,
        "title": "Spieltag: 29.01.2026",
        "body": "### Ergebnis Spiel 1 (J:H)\n\n0:0\n\n### Ergebnis Spiel 2 (J:H)\n\n0:0\n\n### Ergebnis Spiel 3 (J:H)\n\n0:0",
        "labels": ["Scope: Test"]
      }
    """.trimIndent())
    val applicationProps = tempDir.resolve("application.properties")
    applicationProps.writeText("""
            tagessieg.data.matches.main=data/main.csv
            tagessieg.data.matches.test=test.csv
            tagessieg.config.daterange=2023-01-01,2026-12-31
        """.trimIndent())

    val result = cli.test("-w $tempDir --dry-run -f csv ${ImportCmd.NAME} --input $jsonPath")

    assertThat(result.statusCode).isEqualTo(0)
    assertThat(result.stdout).contains("Dry run: match with id $id would be added to matches-test.csv")

    val testCsv = tempDir.resolve("data/matches-test.csv")
    assertThat(testCsv.exists()).isFalse()
  }

  @Test
  fun `should respect quiet flag`(@TempDir tempDir: Path) {
    val jsonPath = tempDir.resolve("match.json")
    val id = System.currentTimeMillis() + 2
    jsonPath.writeText("""
      {
        "id": $id,
        "title": "Spieltag: 29.01.2026",
        "body": "### Ergebnis Spiel 1 (J:H)\n\n0:0\n\n### Ergebnis Spiel 2 (J:H)\n\n0:0\n\n### Ergebnis Spiel 3 (J:H)\n\n0:0",
        "labels": ["Scope: Test"]
      }
    """.trimIndent())
    val applicationProps = tempDir.resolve("application.properties")
    applicationProps.writeText("""
            tagessieg.data.matches.main=data/main.csv
            tagessieg.data.matches.test=test.csv
            tagessieg.config.daterange=2023-01-01,2026-12-31
        """.trimIndent())

    val result = cli.test("-w $tempDir --quiet ${ImportCmd.NAME} --input $jsonPath")

    assertThat(result.statusCode).isEqualTo(0)
    assertThat(result.stdout).isEmpty()
  }
}
