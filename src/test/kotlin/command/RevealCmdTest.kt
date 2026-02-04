package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import io.github.bstdoom.tagessieg.TagessiegCli
import io.github.bstdoom.tagessieg.command.RevealCmd.Companion.NAME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Not yet implemented")
class RevealCmdTest {

  private val cli = TagessiegCli(false).subcommands(RevealCmd())

  @Test
  fun `run cmd`() {
    val result = cli.test("-n", NAME)
    assertThat(result.statusCode).isEqualTo(0)
  }
}
