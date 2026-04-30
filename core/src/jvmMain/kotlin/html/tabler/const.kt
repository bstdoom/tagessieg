package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.html.Href

private val TABLER_VERSION = "1.4.0"

val TABLER_CSS = Href.Css("${Href.CDN}/@tabler/core@$TABLER_VERSION/dist/css/tabler.min.css")
val TABLER_JS = Href.Script("${Href.CDN}/@tabler/core@$TABLER_VERSION/dist/js/tabler.min.js")
val APEXCHARTS_JS = Href.Script("${Href.CDN}/apexcharts")


enum class TablerColor(val value: String) {
  PRIMARY("primary"),
  SECONDARY("secondary"),
  TERTIARY("tertiary"),
  SUCCESS("success"),
  WARNING("warning"),
  ERROR("error"),
  INFO("info"),
}
