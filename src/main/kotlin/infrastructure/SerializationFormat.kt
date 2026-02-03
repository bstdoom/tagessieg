@file:OptIn(ExperimentalSerializationApi::class)

package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import kotlinx.serialization.*
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.*
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
      contextual(LocalDateRangeSerializer)
    }
  }),
  CSVH(format = Csv {
    hasHeaderRecord = true
    serializersModule = SerializersModule {
      contextual(PathSerializer)
      contextual(LocalDateRangeSerializer)
    }
  }),
  JSON(format = Json {
    useArrayPolymorphism = true
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {
      contextual(PathSerializer)
      contextual(LocalDateRangeSerializer)
    }
  }),
  ;

  companion object {

    data object PathSerializer : KSerializer<Path> {
      override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.nio.Path", PrimitiveKind.STRING)
      override fun serialize(encoder: Encoder, value: Path) = encoder.encodeString(value.toString())
      override fun deserialize(decoder: Decoder): Path = Path(decoder.decodeString())
    }

    data object LocalDateRangeSerializer : KSerializer<LocalDateRange> {
      private val serializer = LocalDate.serializer()

      override val descriptor: SerialDescriptor = buildClassSerialDescriptor("kotlinx.datetime.LocalDateRange") {
        element("start", serializer.descriptor)
        element("endInclusive", serializer.descriptor)
      }

      override fun serialize(encoder: Encoder, value: LocalDateRange) {
        encoder.encodeStructure(descriptor) {
          encodeSerializableElement(descriptor, 0, serializer, value.start)
          encodeSerializableElement(descriptor, 1, serializer, value.endInclusive)
        }
      }

      override fun deserialize(decoder: Decoder): LocalDateRange = decoder.decodeStructure(descriptor) {
        var start: LocalDate? = null
        var end: LocalDate? = null

        while (true) {
          when (val index = decodeElementIndex(descriptor)) {
            0 -> start = decodeSerializableElement(descriptor, 0, serializer)
            1 -> end = decodeSerializableElement(descriptor, 1, serializer)
            CompositeDecoder.DECODE_DONE -> break
            else -> throw SerializationException("Unknown index $index")
          }
        }

        LocalDateRange(requireNotNull(start) { "Missing start" }, requireNotNull(end) { "Missing endInclusive" })
      }
    }

    fun fromString(format: String): SerializationFormat? {
      return entries.find { it.name.equals(format, ignoreCase = true) }
    }
  }


}
