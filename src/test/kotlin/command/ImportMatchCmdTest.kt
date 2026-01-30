package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ImportMatchCmdTest {

  private val cli = RootCmd().subcommands(ImportMatchCmd())

  @Test
  fun `should import match from json`() {
    val jsonPath = "src/test/resources/github/match-3873007996.json"

    val result = cli.test("${ImportMatchCmd.NAME} --file $jsonPath")

    assertThat(result.statusCode).isEqualTo(0)
    assertThat(result.stdout.trim().lines()).hasSize(1)
    assertThat(result.stdout).contains("3873007996,2026-01-29,0:0,0:0,0:0")
    assertThat(result.stdout).contains("\"With comment 1, Now we close\"")
  }

  @Test
  fun `should respect quiet flag`() {
    val jsonPath = "src/test/resources/github/match-3873007996.json"

    val result = cli.test("${ImportMatchCmd.NAME} --file $jsonPath --quiet")

    assertThat(result.statusCode).isEqualTo(0)
    assertThat(result.stdout).isEmpty()
  }
}
