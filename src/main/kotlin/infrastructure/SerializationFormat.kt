@file:OptIn(ExperimentalSerializationApi::class)

package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.datetime.LocalDateRange
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.StringFormat
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.nio.file.Path
import kotlin.io.path.Path

@Serializable
enum class SerializationFormat(private val format: StringFormat) : StringFormat by format {

  CSV(format = Csv {
    hasHeaderRecord = false
    serializersModule = SerializersModule {
      contextual(PathSerializer)
    }
  }),
  JSON(format = Json {
    useArrayPolymorphism = true
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {
      contextual(PathSerializer)
    }
  }),
  ;

  companion object {

    data object PathSerializer : KSerializer<Path> {
      override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Path", PrimitiveKind.STRING)
      override fun serialize(encoder: Encoder, value: Path) = encoder.encodeString(value.toString())
      override fun deserialize(decoder: Decoder): Path = Path(decoder.decodeString())
    }

//    data object LocalDateRangeSerializer : KSerializer<LocalDateRange> {
//      override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Path", PrimitiveKind.STRING)
//      override fun serialize(encoder: Encoder, value: Path) = encoder.encodeString(value.toString())
//      override fun deserialize(decoder: Decoder): Path = Path(decoder.decodeString())
//    }

    fun fromString(format: String): SerializationFormat? {
      return entries.find { it.name.equals(format, ignoreCase = true) }
    }
  }


}
