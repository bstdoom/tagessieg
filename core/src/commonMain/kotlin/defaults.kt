package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

data object Defaults {
  val TIME_ZONE: TimeZone = TimeZone.of("Europe/Berlin")
}

expect fun now(): LocalDateTime

fun germanDateTime(dateTime: LocalDateTime = now()): String = buildString {
  append(dateTime.day.toString().padStart(2, '0'))
  append('.')
  append((dateTime.month.ordinal + 1).toString().padStart(2, '0'))
  append('.')
  append(dateTime.year.toString().padStart(4, '0'))
  append(' ')
  append(dateTime.hour.toString().padStart(2, '0'))
  append(':')
  append(dateTime.minute.toString().padStart(2, '0'))
}
