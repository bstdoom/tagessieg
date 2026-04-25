package io.github.bstdoom.tagessieg.application.port.out

import io.github.bstdoom.tagessieg.model.Match
import io.github.bstdoom.tagessieg.model.Matches

/**
 * Deals with persistence of matches.
 */
interface MatchesRepository {

  /**
   * Loads all matches.
   */
  fun load(): Matches

  /**
   * Appends a match and saves it.
   */
  fun append(match: Match): Matches
}
