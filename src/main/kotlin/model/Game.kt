package io.github.bstdoom.tagessieg.model

import io.github.bstdoom.tagessieg.model.Game.Companion.parse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.math.abs

/**
 * Representation of a game result with team scores.
 */
@Serializable(with = GameSerializer::class)
data class Game(val j: Int, val h: Int) {
  companion object {
    /**
     * Parses colon‑separated string into game.
     */
    fun parse(string: String): Game = with(string.split(":")) {
      Game(get(0).trim().toInt(), get(1).trim().toInt())
    }


  }

  init {
    require(h >= 0 && j >= 0) { "Scores cannot be negative: [$h:$j]" }
  }

  @Transient
  val diff = abs(h - j)

  @Transient
  val winner: Winner = when {
    h > j -> J
    h < j -> H
    else -> X
  }

  override fun toString() = "$h:$j[${winner}]"
}

data object GameSerializer : KSerializer<Game> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Game", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: Game) {
    encoder.encodeString("${value.j}:${value.h}")
  }

  override fun deserialize(decoder: Decoder) = parse(decoder.decodeString())
}
