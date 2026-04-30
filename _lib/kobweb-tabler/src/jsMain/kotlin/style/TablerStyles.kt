package io.toolisticon.kobweb.tabler.style

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.attr

data object TablerStyles {

  /** Root page container used by Tabler layouts. */
  val page = cls("page")
  /** Wrapper that anchors page header/body sections inside the Tabler page shell. */
  val pageWrapper = cls("page-wrapper")
  /** Main content area inside the page wrapper. */
  val pageBody = cls("page-body")

  /** Responsive top navigation bar with print suppression. */
  val navbar = cls("navbar", "navbar-expand-md", "d-print-none")
  /** Standard wide content container used across the layout. */
  val containerXl = cls("container-xl")
  /** Navbar brand area with Tabler autolight treatment and responsive padding. */
  val navbarBrand = cls("navbar-brand", "navbar-brand-autodark", "pe-0", "pe-md-3")
  /** Horizontal navbar list aligned to the right on desktop. */
  val navbarNav = cls("navbar-nav", "flex-row", "order-md-last")
  /** Navbar item used for the generated-at timestamp. */
  val navbarGeneratedAt = cls("nav-item", "d-flex", "align-items-center", "text-secondary")
  /** Small left margin for inline text next to an icon or label. */
  val generatedAtText = cls("ms-1")

  /** Header block shown above the main content. */
  val pageHeader = cls("page-header", "d-print-none")
  /** Header row with compact spacing and vertical centering. */
  val pageHeaderRow = cls("row", "g-2", "align-items-center")
  /** Generic header column that expands to available width. */
  val pageHeaderCol = cls("col")
  /** Large title style used in the page header. */
  val pageTitle = cls("page-title")
  /** Secondary text that keeps bottom margin removed. */
  val textSecondaryMb0 = cls("text-secondary", "mb-0")

  /** Card deck row with Tabler spacing between cards. */
  val rowCards = cls("row", "row-deck", "row-cards", "g-3", "mb-4")
  /** Standard Tabler card container. */
  val card = cls("card")
  /** Card container that stretches to full height. */
  val cardH100 = cls("card", "h-100")
  /** Card header section. */
  val cardHeader = cls("card-header")
  /** Card title text style. */
  val cardTitle = cls("card-title")
  /** Card body section. */
  val cardBody = cls("card-body")

  /**
   * Half-width on small screens and one-quarter width on large screens.
   *
   * Equivalent to `col-sm-6 col-lg-3`.
   */
  val colSm6Lg3 = cls("col-sm-6", "col-lg-3")
  /** One-third width column on large screens. */
  val colLg4 = cls("col-lg-4")
  /** Two-thirds width column on large screens. */
  val colLg8 = cls("col-lg-8")
  /** Full-width column. */
  val col12 = cls("col-12")

  /** Heading-sized value with reduced bottom margin. */
  val h1Mb2 = cls("h1", "mb-2")
  /** Small subtitle / section label style. */
  val subheader = cls("subheader")
  /** Secondary text with zero margin. */
  val textSecondaryM0 = cls("text-secondary", "m-0")
  /** Top margin used to separate card sections. */
  val mt3 = cls("mt-3")
  /** Larger top margin used around chart/table blocks. */
  val mt4 = cls("mt-4")
  /** Vertical margin used for horizontal separators and notes. */
  val my4 = cls("my-4")

  /** Compact progress bar container with top spacing. */
  val progress = cls("progress", "progress-sm", "mt-3")
  /** Base progress bar element without variant color. */
  val progressBar = cls("progress-bar")
  /** Progress bar element with an optional Tabler background class. */
  fun progressBar(variantClass: String? = null): Modifier = cls("progress-bar", variantClass)

  /** Flush list group used for stacked summary rows. */
  val listGroup = cls("list-group", "list-group-flush")
  /** List group row styled as a single summary line. */
  val listGroupItem = cls("list-group-item", "d-flex", "justify-content-between", "align-items-center", "px-0")
  /** Flex row for the left side of a list item, usually badge plus label. */
  val listGroupRow = cls("d-flex", "align-items-center", "gap-2")
  /** Semibold text for the list label. */
  val fwSemibold = cls("fw-semibold")
  /** Secondary text color used for numeric values or hints. */
  val textSecondary = cls("text-secondary")
  /** Base badge class. Use the overload for a contextual variant. */
  val badge = cls("badge")
  /** Badge class with an optional contextual background variant. */
  fun badge(variantClass: String? = null): Modifier = cls("badge", variantClass)

  /** Danger alert shown when chart rendering fails. */
  val alertDangerMb0 = cls("alert", "alert-danger", "mb-0")
  /** Small, muted helper text. */
  val smallTextSecondary = cls("text-secondary", "small")

  /** Wrapper that enables horizontal scrolling for wide tables. */
  val tableResponsive = cls("table-responsive")
  /** Tabler table with card styling and vertical cell alignment. */
  val table = cls("table", "card-table", "table-vcenter")

  private fun cls(vararg names: String): Modifier = Modifier.attrsModifier {
    attr("class", names.joinToString(" "))
  }

  private fun cls(base: String, variant: String?): Modifier =
    if (variant.isNullOrBlank()) {
      cls(base)
    } else {
      cls(base, variant)
    }
}
