package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import io.github.bstdoom.tagessieg.TagessiegCli
import io.github.bstdoom.tagessieg.command.GenerateRevealCmd.Companion.NAME
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class GenerateRevealCmdTest {

  private val cli = TagessiegCli().subcommands(GenerateRevealCmd())

  @Test
  fun `run cmd`() {
    val result = cli.test(NAME)
  }
}
