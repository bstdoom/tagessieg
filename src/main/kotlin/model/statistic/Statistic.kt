package io.github.bstdoom.tagessieg.model.statistic

import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("name")
sealed interface Statistic {
  val range: LocalDateRange

}
