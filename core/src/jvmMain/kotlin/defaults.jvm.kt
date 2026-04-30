package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import java.time.ZoneId
import java.time.ZonedDateTime

data object JvmDefaults {
  val LOCALE: java.util.Locale = java.util.Locale.GERMAN
  val CHARSET: java.nio.charset.Charset = Charsets.UTF_8
}

actual fun now(): LocalDateTime {
  val zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Berlin"))
  return LocalDateTime(
    year = zonedDateTime.year,
    month = Month(zonedDateTime.monthValue),
    day = zonedDateTime.dayOfMonth,
    hour = zonedDateTime.hour,
    minute = zonedDateTime.minute,
    second = zonedDateTime.second,
    nanosecond = zonedDateTime.nano
  )
}
