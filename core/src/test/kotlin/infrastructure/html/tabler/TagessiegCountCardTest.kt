package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import kotlinx.html.body
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import org.junit.jupiter.api.Test

class TagessiegCountCardTest {

  val html = createHTML().html {}

  @Test
  fun name() {
    val card = TagessiegCountCard(
      player = "Kermit",
      color = "green",
      count = 42,
      percent = 0.33,
      diff = 2)

    println(createHTML().html {
      body {
        card.render(this)
      }
    })
  }
}
