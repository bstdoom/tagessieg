package io.github.bstdoom.tagessieg.adapter.out

import io.github.bstdoom.tagessieg.application.port.out.MatchesRepository
import io.github.bstdoom.tagessieg.infrastructure.MatchesCsv
import io.github.bstdoom.tagessieg.model.Match
import io.github.bstdoom.tagessieg.model.Matches
import java.nio.file.Path

/**
 * Implementation of [MatchesRepository] that uses a CSV file to store and retrieve match data.
 */
class MatchesCsvRepository(
  private val file: Path,
  private val createIfMissing: Boolean = false
) : MatchesRepository {

  override fun load(): Matches = MatchesCsv(file, createIfMissing)
    .matches

  override fun append(match: Match): Matches = MatchesCsv(file, createIfMissing)
    .plus(match)
    .save()
    .matches
}
