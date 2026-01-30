@file:OptIn(ExperimentalSerializationApi::class)

package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.StringFormat
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.json.Json

@Serializable
enum class SerializationFormat(private val format: StringFormat) : StringFormat by format{

  CSV(format = Csv {
      hasHeaderRecord = false
  }),
  JSON(format = Json {
      useArrayPolymorphism = true
      ignoreUnknownKeys = true
  }),
  ;

  companion object {

    fun fromString(format: String): SerializationFormat? {
      return entries.find { it.name.equals(format, ignoreCase = true) }
    }
  }


}
