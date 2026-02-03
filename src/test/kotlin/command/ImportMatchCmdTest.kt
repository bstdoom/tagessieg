package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.writeText

class ImportMatchCmdTest {

  private val cli = RootCmd().subcommands(ImportMatchCmd())

  @Test
  @Disabled
  fun `should import match from json`(@TempDir tempDir: Path) {
    val jsonPath = "src/test/resources/github/match-3873007996.json"
    val applicationProps = tempDir.resolve("application.properties")
    applicationProps.writeText("""
            tagessieg.data.matches.main=main.csv
            tagessieg.data.matches.test=test.csv
            tagessieg.config.daterange=2023-01-01,2026-12-31
        """.trimIndent())

    val result = cli.test("-w $tempDir -f csv ${ImportMatchCmd.NAME} --file $jsonPath")

    assertThat(result.statusCode).isEqualTo(0)
    assertThat(result.stdout).contains("Match with id 3873007996 added to matches-test.csv")
    assertThat(result.stdout).contains("3873007996,2026-01-29,0:0,0:0,0:0")

    val testCsv = tempDir.resolve("test.csv")
    assertThat(testCsv.exists()).isTrue()
    val matches = MatchesCsv(testCsv).matches
    assertThat(matches).hasSize(1)
    assertThat(matches.first().id).isEqualTo(3873007996L)
  }

  @Test
  fun `should respect dry-run`(@TempDir tempDir: Path) {
    val jsonPath = "src/test/resources/github/match-3873007996.json"
    val applicationProps = tempDir.resolve("application.properties")
    applicationProps.writeText("""
            tagessieg.data.matches.main=main.csv
            tagessieg.data.matches.test=test.csv
            tagessieg.config.daterange=2023-01-01,2026-12-31
        """.trimIndent())

    val result = cli.test("-w $tempDir --dry-run -f csv ${ImportMatchCmd.NAME} --file $jsonPath")

    assertThat(result.statusCode).isEqualTo(0)
    assertThat(result.stdout).contains("Dry run: match with id 3873007996 would be added to matches-test.csv")

    val testCsv = tempDir.resolve("matches-test.csv")
    assertThat(testCsv.exists()).isFalse()
  }

  @Test
  fun `should respect quiet flag`(@TempDir tempDir: Path) {
    val jsonPath = "src/test/resources/github/match-3873007996.json"
    val applicationProps = tempDir.resolve("application.properties")
    applicationProps.writeText("""
            tagessieg.data.matches.main=main.csv
            tagessieg.data.matches.test=test.csv
            tagessieg.config.daterange=2023-01-01,2026-12-31
        """.trimIndent())

    val result = cli.test("-w $tempDir --quiet ${ImportMatchCmd.NAME} --file $jsonPath")

    assertThat(result.statusCode).isEqualTo(0)
    assertThat(result.stdout).isEmpty()
  }
}
