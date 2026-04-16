package io.github.bstdoom.tagessieg.infrastructure.html

import io.github.bstdoom.tagessieg.infrastructure.Defaults
import kotlinx.html.*
import java.nio.charset.Charset

sealed interface Href {
  companion object {
    const val CDN = "https://cdn.jsdelivr.net/npm"
  }

  val value: String

  data class Script(override val value: String) : Href
  data class Css(override val value: String) : Href
}

fun interface Renderable {
  fun render(receiver: FlowContent)
}


fun HEAD.favicon(symbol: String) = this.link(
  rel = "icon",
  href = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'%3E%3Ctext y='.9em' font-size='90'%3E$symbol%3C/text%3E%3C/svg%3E"
)

fun HEAD.viewport(initialScale: Double = 1.0) = this.meta {
  name = "viewport"
  content = "width=device-width, initial-scale=$initialScale"
}

fun HEAD.charset(cs: Charset = Defaults.CHARSET) = this.meta { charset = cs.name().lowercase() }

fun HEAD.title(title: String, description: String? = null) {
  title { +title }
  meta { name = "title"; content = title }
  description?.let {
    this.meta { name = "description"; content = it }
  }
}

fun HEAD.cssHref(href: Href.Css) = this.link(
  rel = "stylesheet",
  href = href.value
) {}


fun BODY.scriptHref(href: Href.Script) = this.script(
  type = "text/javascript",
  src = href.value
) {}
