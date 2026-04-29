package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlin.js.Date

actual fun now(): LocalDateTime {
  val date = Date()
  return LocalDateTime(
    year = date.getFullYear(),
    month = Month(date.getMonth() + 1),
    day = date.getDate(),
    hour = date.getHours(),
    minute = date.getMinutes(),
    second = date.getSeconds(),
    nanosecond = date.getMilliseconds() * 1_000_000
  )
}
