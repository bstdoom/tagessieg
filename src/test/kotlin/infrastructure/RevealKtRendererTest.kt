package io.github.bstdoom.tagessieg.infrastructure

import dev.limebeck.revealkt.core.RevealKt.Configuration.Theme.Predefined
import dev.limebeck.revealkt.dsl.img
import dev.limebeck.revealkt.dsl.revealKt
import dev.limebeck.revealkt.dsl.slides.regularSlide
import dev.limebeck.revealkt.dsl.title
import io.github.bstdoom.tagessieg.infrastructure.RevealKtRenderer.Companion.render
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.writeText


class RevealKtRendererTest {

  @Test
  fun `render html`(@TempDir tempDir: Path) {
    val html = revealKt("MyPresentation") {
      meta {
        title = "My Presentation"
        language = "de"
      }
      configuration {
        theme = Predefined.BEIGE
        additionalCssStyle = """
                    .reveal {
                        background:
                          linear-gradient(
                            rgba(0,0,0,0.55),
                            rgba(0,0,0,0.55)
                          ),
                          url("https://picsum.photos/1920/1080") center / cover no-repeat;
                    }


        .reveal table {
          border-collapse: collapse;
          margin: auto;
          font-size: 0.8em;
          color: #f8fafc; /* ✅ Text explizit weiß */
        }

        .reveal th,
        .reveal td {
          border: 1px solid rgba(255,255,255,0.3);
          padding: 0.4em 0.6em;
          color: #f8fafc; /* ✅ wichtig: nicht erben! */
        }

        .reveal th {
          background: rgba(255,255,255,0.15);
          font-weight: 600;
        }

        .reveal tbody tr:nth-child(even) {
          background: rgba(255,255,255,0.05);
        }
        """.trimIndent()
      }
      slides {
        regularSlide {
          +title { "Hello World" }
          +img("kick-off-2-screen.jpg")
        }
        regularSlide {
          +title { "Hello World" }
        }
        regularSlide {
          +title { "Hello World" }
        }


      }
    }.render()

    println(html)

    tempDir.resolve("index.html").writeText(html)
  }
}
