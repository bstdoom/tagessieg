package io.github.bstdoom.tagessieg.adapter.out

import io.github.bstdoom.tagessieg.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class MatchesCsvRepositoryTest {

  @Test
  fun `create and append`(@TempDir tempDir: Path) {
    val file = tempDir.resolve("matches.csv")

    val repository = MatchesCsvRepository(file, true)

    var matches = repository.load()

    assertThat(matches).isEmpty()

    val m = Fixtures.match("2026-01-01","0:0","0:0","0:0")

    matches = repository.append(m)
    assertThat(matches).containsExactly(m)
  }
}
