package tagessieg.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun Index() {
  Div(attrs = {
    attr(
      "style",
      """
      min-height: 100vh;
      background:
        radial-gradient(circle at top left, rgba(32, 107, 196, 0.24), transparent 34%),
        radial-gradient(circle at top right, rgba(47, 179, 68, 0.18), transparent 30%),
        linear-gradient(180deg, #0f172a 0%, #111827 52%, #0b1020 100%);
      color: #e5eefc;
      margin: 0;
      font-family: Inter, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
      """
    )
  }) {
    Div(attrs = {
      attr(
        "style",
        """
        max-width: 1120px;
        margin: 0 auto;
        padding: 72px 24px 96px;
        """
      )
    }) {
      Div(attrs = {
        attr(
          "style",
          """
          display: flex;
          align-items: center;
          justify-content: space-between;
          gap: 24px;
          margin-bottom: 48px;
          """
        )
      }) {
        Div {
          Span(attrs = {
            attr(
              "style",
              """
              display: inline-block;
              padding: 8px 12px;
              border-radius: 999px;
              background: rgba(32, 107, 196, 0.16);
              color: #8ab4f8;
              font-size: 12px;
              letter-spacing: 0.08em;
              text-transform: uppercase;
              """
            )
          }) {
            Text("Kobweb init")
          }

          H1(attrs = {
            attr(
              "style",
              """
              margin: 18px 0 12px;
              font-size: clamp(2.6rem, 6vw, 5.2rem);
              line-height: 0.95;
              letter-spacing: -0.05em;
              color: #f8fbff;
              max-width: 10ch;
              """
            )
          }) {
            Text("Tagessieg lands in Kobweb")
          }

          P(attrs = {
            attr(
              "style",
              """
              margin: 0;
              max-width: 62ch;
              color: #b8c4d9;
              font-size: 1.05rem;
              line-height: 1.7;
              """
            )
          }) {
            Text(
              "This is the first Kobweb page in the new site module. The dashboard will move here next, " +
                "while the existing Kotlin domain and CLI continue to live in core."
            )
          }
        }

        Div(attrs = {
          attr(
            "style",
            """
            min-width: 220px;
            padding: 20px 22px;
            border-radius: 20px;
            background: rgba(255, 255, 255, 0.05);
            border: 1px solid rgba(255, 255, 255, 0.08);
            box-shadow: 0 24px 80px rgba(0, 0, 0, 0.28);
            backdrop-filter: blur(12px);
            """
          )
        }) {
          Span(attrs = {
            attr(
              "style",
              """
              display: block;
              color: #8ab4f8;
              font-size: 12px;
              text-transform: uppercase;
              letter-spacing: 0.08em;
              margin-bottom: 10px;
              """
            )
          }) {
            Text("Current state")
          }
          Span(attrs = {
            attr(
              "style",
              """
              display: block;
              color: #ffffff;
              font-size: 1.3rem;
              font-weight: 700;
              margin-bottom: 6px;
              """
            )
          }) {
            Text("Site shell ready")
          }
          Span(attrs = {
            attr(
              "style",
              """
              display: block;
              color: #b8c4d9;
              line-height: 1.6;
              """
            )
          }) {
            Text("Next: port the dashboard cards, charts, and yearly table.")
          }
        }
      }

      Div(attrs = {
        attr(
          "style",
          """
          display: flex;
          flex-wrap: wrap;
          gap: 16px;
          margin-bottom: 40px;
          """
        )
      }) {
        LinkCard("Repository", "https://github.com/bstdoom/tagessieg", "Source and history for the app.")
        LinkCard("GitHub Pages", "https://pages.github.com", "Static deployment target for the site.")
        LinkCard("Kobweb", "https://kobweb.varabyte.com", "The framework now driving the web layer.")
      }
    }
  }
}

@Composable
private fun LinkCard(title: String, hrefValue: String, description: String) {
  A(attrs = {
    href(hrefValue)
    target(ATarget.Blank)
    attr(
      "style",
      """
      flex: 1 1 220px;
      min-width: 220px;
      padding: 18px 20px;
      border-radius: 18px;
      text-decoration: none;
      background: rgba(255, 255, 255, 0.05);
      border: 1px solid rgba(255, 255, 255, 0.08);
      color: inherit;
      transition: transform 120ms ease, border-color 120ms ease, background 120ms ease;
      """
    )
  }) {
    Div {
      Span(attrs = {
        attr(
          "style",
          """
          display: inline-block;
          margin-bottom: 10px;
          color: #8ab4f8;
          font-size: 12px;
          text-transform: uppercase;
          letter-spacing: 0.08em;
          """
        )
      }) {
        Text(title)
      }
      P(attrs = {
        attr(
          "style",
          """
          margin: 0;
          color: #c9d3e5;
          line-height: 1.6;
          """
        )
      }) {
        Text(description)
      }
    }
  }
}
