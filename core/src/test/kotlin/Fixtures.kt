package io.github.bstdoom.tagessieg

import io.github.bstdoom.tagessieg.infrastructure.TagessiegProperties
import io.github.bstdoom.tagessieg.model.Game
import io.github.bstdoom.tagessieg.model.Match
import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AnnotatedElementContext
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.api.io.TempDirFactory
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.copyTo
import kotlin.io.path.createDirectories
import kotlin.io.path.isDirectory
import kotlin.io.path.readText
import kotlin.io.path.writeText

data object Fixtures {
  val properties = TagessiegProperties.load()
    .copy(
      mainCsv = Path.of("src/main/resources/matches.csv"),
      testCsv = Path.of("src/test/resources/matches-test.csv")
    )
  val _2026_01_22 = "2026-01-22"
  val LD_2026_01_22 = LocalDate.parse(_2026_01_22)

  fun match(
    date: String,
    game1: String, game2: String, game3: String,
    comment: String? = null
  ) = Match(date = LocalDate.parse(date), game1 = Game.parse(game1), game2 = Game.parse(game2), game3 = Game.parse(game3), comment = comment)

  val testData = try {
    properties.testCsv.readText()
  } catch (e: Exception) {
    ""
  }

  fun copyTestCsv(path: Path, overwrite: Boolean = true) : Path {
    require(path.isDirectory()) { "Target path must be a directory" }

    if (!Files.exists(properties.testCsv)) {
      val target = path.resolve(properties.testCsv.fileName)
      Files.writeString(target, "")
      return target
    }

    return properties.testCsv.copyTo(
      target = path.resolve(properties.testCsv.fileName),
      overwrite = true
    )
  }

  class  BuildHtml : TempDirFactory {
    override fun createTempDirectory(
      elementContext: AnnotatedElementContext,
      extensionContext: ExtensionContext
    ): Path {
      val tempDir = Path.of("build","html")
        .createDirectories()
      return tempDir
    }
  }
}

class FixturesTest {
  @Test
  fun `copy test matches to target`(@TempDir tempDir: Path) {
    val copy = Fixtures.copyTestCsv(tempDir)
    assertThat(copy.readText()).isEqualTo(Fixtures.testData)
  }
}
