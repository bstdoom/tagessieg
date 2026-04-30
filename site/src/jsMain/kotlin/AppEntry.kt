package io.github.bstdoom.tagessieg.site

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.KobwebApp
import kotlinx.browser.document
import io.toolisticon.kobweb.tabler.KobwebTabler.Assets.ensureApexChartsLoaded
import io.toolisticon.kobweb.tabler.KobwebTabler.Assets.ensureTablerCssLoaded

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
  LaunchedEffect(Unit) {
    ensureTablerCssLoaded()
    ensureApexChartsLoaded()
    document.body?.className = "bg-body"
  }

  KobwebApp {
    content()
  }
}
