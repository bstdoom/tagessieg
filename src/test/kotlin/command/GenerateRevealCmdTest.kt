package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import io.github.bstdoom.tagessieg.command.GenerateRevealCmd.Companion.NAME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GenerateRevealCmdTest {

  private val cli = RootCmd().subcommands(GenerateRevealCmd())

  @Test
  fun `run cmd`() {
    val result = cli.test("-n", NAME)
    assertThat(result.statusCode).isEqualTo(0)
  }
}
