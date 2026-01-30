package io.github.bstdoom.tagessieg.infrastructure

import com.github.ajalt.clikt.core.BaseCliktCommand
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.serializer

@Serializable
enum class EchoFormat {
  PLAIN,
  ERROR,
  JSON,
  CSV,
  QUIET,
  ;

  companion object {
    fun of(name: String): EchoFormat = EchoFormat.valueOf(name.uppercase())
  }


  @Throws(SerializationException::class)
  inline fun <reified T> encode(any: T): Any? = when (this) {
    JSON, CSV -> {
      val format = SerializationFormat.valueOf(this.name)
      val strategy = serializer<T>()
      format.encodeToString(strategy, any)
    }

    else -> any
  }

}
