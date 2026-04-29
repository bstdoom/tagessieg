package org.jetbrains.compose.web.dom

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLHRElement
import org.w3c.dom.HTMLLIElement
import org.w3c.dom.HTMLParagraphElement
import org.w3c.dom.HTMLSpanElement
import org.w3c.dom.HTMLTableElement
import org.w3c.dom.HTMLTableRowElement
import org.w3c.dom.HTMLTableSectionElement
import org.w3c.dom.HTMLTableCellElement
import org.w3c.dom.HTMLUListElement

@Composable
fun Div(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLDivElement>? = null,
  content: ContentBuilder<HTMLDivElement>? = null,
) {
  Div(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun Span(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLSpanElement>? = null,
  content: ContentBuilder<HTMLSpanElement>? = null,
) {
  Span(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun P(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLParagraphElement>? = null,
  content: ContentBuilder<HTMLParagraphElement>? = null,
) {
  P(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun H1(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLHeadingElement>? = null,
  content: ContentBuilder<HTMLHeadingElement>? = null,
) {
  H1(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun H3(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLHeadingElement>? = null,
  content: ContentBuilder<HTMLHeadingElement>? = null,
) {
  H3(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun Hr(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLHRElement>? = null,
) {
  Hr(attrs = modifier.toAttrs(attrs))
}

@Composable
fun Ul(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLUListElement>? = null,
  content: ContentBuilder<HTMLUListElement>? = null,
) {
  Ul(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun Li(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLLIElement>? = null,
  content: ContentBuilder<HTMLLIElement>? = null,
) {
  Li(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun Table(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLTableElement>? = null,
  content: ContentBuilder<HTMLTableElement>? = null,
) {
  Table(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun Thead(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLTableSectionElement>? = null,
  content: ContentBuilder<HTMLTableSectionElement>? = null,
) {
  Thead(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun Tbody(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLTableSectionElement>? = null,
  content: ContentBuilder<HTMLTableSectionElement>? = null,
) {
  Tbody(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun Tr(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLTableRowElement>? = null,
  content: ContentBuilder<HTMLTableRowElement>? = null,
) {
  Tr(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun Td(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLTableCellElement>? = null,
  content: ContentBuilder<HTMLTableCellElement>? = null,
) {
  Td(attrs = modifier.toAttrs(attrs), content = content)
}

@Composable
fun Th(
  modifier: Modifier,
  attrs: AttrBuilderContext<HTMLTableCellElement>? = null,
  content: ContentBuilder<HTMLTableCellElement>? = null,
) {
  Th(attrs = modifier.toAttrs(attrs), content = content)
}
