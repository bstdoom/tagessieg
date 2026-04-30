package io.github.bstdoom.tagessieg.adapter.out

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.domain.DefaultStatisticData
import io.github.bstdoom.tagessieg.domain.StatisticData
import io.github.bstdoom.tagessieg.model.Matches
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.CleanupMode
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path


class GenerateTablerSiteTest {

  val adapter = GenerateTablerSite()


  @Test
  fun render(@TempDir(factory = Fixtures.BuildHtml::class, cleanup = CleanupMode.NEVER) tempDir: Path) {
    val file = Fixtures.copyTestCsv(tempDir)
    println(file.toAbsolutePath())

    val statisticData = DefaultStatisticData(MatchesCsvRepository(file).load())

    val result = adapter.invoke(statisticData).single()

    val outputFile = result.write(tempDir)


    println(outputFile.toAbsolutePath())
  }
}
