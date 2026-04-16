package io.github.bstdoom.tagessieg.infrastructure

import java.nio.charset.Charset
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data object Defaults {
  val LOCALE: Locale = Locale.GERMAN
  val CHARSET: Charset = Charsets.UTF_8
  val TIME_ZONE: ZoneId = ZoneId.of("Europe/Berlin")
}

val GERMAN_DATE_TIME: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

val now: () -> ZonedDateTime = { ZonedDateTime.now(Defaults.TIME_ZONE) }

fun germanDateTime(dateTime: ZonedDateTime = now()): String = GERMAN_DATE_TIME.format(dateTime)
