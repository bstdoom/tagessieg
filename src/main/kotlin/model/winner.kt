package io.github.bstdoom.tagessieg.model

/**
 * Marks a human player.
 */
sealed interface Player : Winner

/**
 * Shows who won the game. `J` and `H` are Players, `X` is a tie.
 */
sealed interface Winner {
  companion object {
    fun valueOf(value: String): Winner = when (value.uppercase()) {
      "J" -> J
      "H" -> H
      "X" -> X
      else -> throw IllegalArgumentException("Unknown winner: $value")
    }
  }
}

data object J : Player
data object H : Player
data object X : Winner

/**
 * Reduction rules, see test.
 */
operator fun Winner.plus(other: Winner) = when {
  this == other -> this
  other == X -> this
  this == X -> other
  else -> X
}
