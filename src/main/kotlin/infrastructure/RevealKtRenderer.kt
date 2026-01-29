package io.github.bstdoom.tagessieg.infrastructure

import dev.limebeck.revealkt.core.RevealKt
import dev.limebeck.revealkt.dsl.RevealKtBuilder
import io.github.bstdoom.tagessieg.HtmlString
import io.github.bstdoom.tagessieg.JsonString
import io.github.bstdoom.tagessieg.infrastructure.RevealKtRenderer.Companion.Ext.metaCharset
import io.github.bstdoom.tagessieg.infrastructure.RevealKtRenderer.Companion.Ext.metaDescription
import io.github.bstdoom.tagessieg.infrastructure.RevealKtRenderer.Companion.Ext.metaTitle
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import java.nio.charset.Charset
import java.nio.file.Path
import kotlin.io.path.writeText

fun RevealKtRenderer.writeToPath(path: Path) = path.writeText(
  this.invoke(), charset = Charsets.UTF_8
)

data class RevealKtRenderer(val presentation: RevealKt) : () -> HtmlString {
  companion object {
    const val CDN_REVEAL_HREF = "https://cdn.jsdelivr.net/npm/reveal.js@5"
    fun RevealKt.render(): HtmlString = RevealKtRenderer(this).invoke()
    fun RevealKtBuilder.render(): HtmlString = RevealKtRenderer(this).invoke()

    fun FlowOrMetaDataOrPhrasingContent.styleSheet(_href: String) = this.link {
      rel = "stylesheet"; href = _href
    }

    fun RevealKt.Configuration.toJson(): JsonString = RevealConfiguration(this).toJson()

    val RevealKt.Configuration.Theme.CDN: String
      get() {
        require(this is RevealKt.Configuration.Theme.Predefined) { "Only predefined themes are supported." }
        return "$CDN_REVEAL_HREF/dist/theme/${this.name.lowercase()}.css"
      }

    data object Ext {
      fun FlowOrMetaDataOrPhrasingContent.metaCharset(cs: Charset = Charsets.UTF_8) = this.meta { charset = cs.name().lowercase() }
      fun FlowOrMetaDataOrPhrasingContent.metaTitle(title: String) = this.meta { name = "title"; content = title }
      fun FlowOrMetaDataOrPhrasingContent.metaDescription(description: String?) = description?.let {
        this.meta { name = "description"; content = it }
      }
    }
  }

  constructor(builder: RevealKtBuilder) : this(builder.build())

  private val html: HtmlString by lazy {
    "<!DOCTYPE html>\n" + createHTML().html {
      lang = presentation.meta.language
      head {
        metaCharset()
        meta {
          name = "viewport"
          content = "width=device-width, initial-scale=1.0"
        }
        metaTitle(presentation.title)
        metaDescription(presentation.meta.description)

        title {
          +presentation.title
        }

        styleSheet("$CDN_REVEAL_HREF/dist/reveal.css")
        styleSheet(presentation.configuration.appearance.theme.CDN)
        styleSheet("$CDN_REVEAL_HREF/plugin/highlight/monokai.css")

        // copy additional css if present
        presentation.configuration.appearance.additionalCssStyle.takeIf(String::isNotBlank)?.let {
          style {
            unsafe { +it }
          }
        }

        presentation.headModifier(this)
      }
      body {
        div("reveal") {
          div("slides") {
            presentation.slides.forEach {
              it.render(this)
            }
          }
        }

        script {
          unsafe {
            raw(
              """
            const configurationJson = ${presentation.configuration.toJson()}
            """.trimIndent()
            )
          }
        }

        script(src = "https://cdn.jsdelivr.net/npm/reveal.js@5/dist/reveal.js") {}
        script(src = "https://cdn.jsdelivr.net/npm/reveal.js@5/plugin/highlight/highlight.js") {}
        script {
          unsafe {
            +"""
                    Reveal.initialize({
                      hash: true,
                      plugins: [ RevealHighlight ]
                    });
                    """.trimIndent()
          }
        }
      }
    }
  }

  override fun invoke(): HtmlString = html
}
