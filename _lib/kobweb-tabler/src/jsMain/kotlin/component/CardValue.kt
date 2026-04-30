package io.toolisticon.kobweb.tabler.component

import androidx.compose.runtime.Composable
import io.toolisticon.kobweb.tabler.style.TablerStyles
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Composable
fun CardValue(text: String) {
  H1(TablerStyles.h1Mb2) {
    Text(text)
  }
}
