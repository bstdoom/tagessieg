package io.github.bstdoom.tagessieg.infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.writeText

class IoTest {
  private val source = Path.of("_data")

  @Test
  fun `copy all files`(@TempDir tempDir: Path) {
    source.copyTo(tempDir)

    assertThat(tempDir.resolve("assets/kick-off-2-screen.jpg")).isRegularFile()
    assertThat(tempDir.resolve("matches.csv")).isRegularFile()
  }

  @Test
  fun `create if not exists`(@TempDir tempDir: Path) {
    source.copyTo(tempDir)
    val target = tempDir.resolve("new_dir")

    source.copyTo(target)
    assertThat(target.resolve("assets/kick-off-2-screen.jpg")).isRegularFile()
  }

  @Test
  fun `do not overwrite git dir`(@TempDir tempDir: Path) {
    tempDir.resolve(".git").writeText("")
    assertThatThrownBy {
      source.copyTo(tempDir)
    }.hasMessageContaining("Target directory must not be a git repository")
  }

  @Test
  fun `delete non-empty dir`(@TempDir tempDir: Path) {
    tempDir.resolve("dummy").writeText("foo")
    source.copyTo(tempDir)
    assertThat(tempDir.resolve("dummy")).doesNotExist()
  }
}
