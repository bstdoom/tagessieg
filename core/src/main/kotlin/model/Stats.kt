package io.github.bstdoom.tagessieg.model

import kotlinx.serialization.Serializable

@Deprecated("Refactor this")
@Serializable
data class Stats(
  val totalTagessiege: Map<Player, Int>,
  val yearTagessiege: Map<Int, Map<Player, Int>>,
  val totalGrandSlams: Map<Player, Int>,
  val yearGrandSlams: Map<Int, Map<Player, Int>>,
  val daysSinceLastTagessieg: Map<Player, Int>,
  val daysSinceLastGrandSlam: Map<Player, Int>,
  val longestTagessiegStreak: Map<Player, Int>,
  val longestGrandSlamStreak: Map<Player, Int>
)
