package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import io.github.bstdoom.tagessieg.TagessiegCli
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.*

@Disabled("Not yet implemented")
class InitWorkDirCmdTest {

    private val cli = TagessiegCli(false).subcommands(InitWorkDirCmd())

    @Test
    fun `should fail if workDir is current directory`() {
        val result = cli.test("-w . ${InitWorkDirCmd.NAME}")
        assertThat(result.statusCode).isNotEqualTo(0)
        assertThat(result.stderr).contains("Cannot initialize workDir in current directory")
    }

    @Test
    fun `should initialize workDir`(@TempDir tempDir: Path) {
        val workDir = tempDir.resolve("work")
        val result = cli.test("-w $workDir ${InitWorkDirCmd.NAME}")

        assertThat(result.statusCode).isEqualTo(0)
        assertThat(result.stdout).contains("Initialized workDir")

        assertThat(workDir.resolve("assets")).isDirectory()
        assertThat(workDir.resolve("assets/kick-off-2-screen.jpg")).isRegularFile()
        assertThat(workDir.resolve("index.html")).isRegularFile()
        assertThat(workDir.resolve("data/matches.csv")).isRegularFile()
    }

    @Test
    fun `should clear workDir if not empty`(@TempDir tempDir: Path) {
        val workDir = tempDir.resolve("work")
        workDir.createDirectories()
        workDir.resolve("old-file.txt").writeText("old content")

        val result = cli.test("-w $workDir ${InitWorkDirCmd.NAME}")

        assertThat(result.statusCode).isEqualTo(0)
        assertThat(workDir.resolve("old-file.txt")).doesNotExist()
        assertThat(workDir.resolve("index.html")).isRegularFile()
    }

    @Test
    fun `should respect dry-run`(@TempDir tempDir: Path) {
        val workDir = tempDir.resolve("work")
        val result = cli.test("-w $workDir --dry-run ${InitWorkDirCmd.NAME}")

        assertThat(result.statusCode).isEqualTo(0)
        assertThat(result.stdout).contains("Dry run:")
        assertThat(workDir).doesNotExist()
    }
}
