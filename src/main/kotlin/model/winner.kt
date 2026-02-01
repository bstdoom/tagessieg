package io.github.bstdoom.tagessieg.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Marks a human player.
 */
@Serializable
sealed interface Player : Winner

/**
 * Shows who won the game. `J` and `H` are Players, `X` is a tie.
 */
@Serializable
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

@Serializable
@SerialName("J")
data object J : Player
@Serializable
@SerialName("H")
data object H : Player
@Serializable
@SerialName("X")
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
