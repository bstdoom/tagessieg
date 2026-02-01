package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.Fixtures
import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import io.github.bstdoom.tagessieg.infrastructure.SerializationFormat
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Test

class LeagueTableTest {

  @Test
  fun name() {
    val matches = MatchesCsv(Fixtures.properties.testCsv).matches
    val table = LeagueTable(matches = matches)

    println(SerializationFormat.JSON.encodeToString<LeagueTable>(table))
  }
}
