package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.model.Matches
import java.nio.file.Path

/**
 * Represents a CSV file with matches and allows interaction with it.
 */
class MatchesCsv(
  val matches: Matches,
  val file: Path
) {

}

