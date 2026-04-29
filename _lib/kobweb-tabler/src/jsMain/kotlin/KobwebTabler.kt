package io.toolisticon.kobweb.tabler

import androidx.compose.runtime.Composable
import io.toolisticon.kobweb.tabler.asset.ensureApexChartsLoaded as ensureApexChartsLoadedImpl
import io.toolisticon.kobweb.tabler.asset.ensureTablerCssLoaded as ensureTablerCssLoadedImpl
import io.toolisticon.kobweb.tabler.chart.BarChartCard as BarChartCardImpl
import io.toolisticon.kobweb.tabler.chart.DonutChartCard as DonutChartCardImpl
import io.toolisticon.kobweb.tabler.chart.DonutRow as DonutRowImpl
import io.toolisticon.kobweb.tabler.chart.LineChartCard as LineChartCardImpl
import io.toolisticon.kobweb.tabler.component.CardLabel as CardLabelImpl
import io.toolisticon.kobweb.tabler.component.Cards as CardsImpl
import io.toolisticon.kobweb.tabler.component.CardBodyText as CardBodyTextImpl
import io.toolisticon.kobweb.tabler.component.CardValue as CardValueImpl
import io.toolisticon.kobweb.tabler.component.ProgressBar as ProgressBarImpl
import io.toolisticon.kobweb.tabler.component.RowList as RowListImpl
import io.toolisticon.kobweb.tabler.component.StatCard as StatCardImpl
import io.toolisticon.kobweb.tabler.component.StatCardData as StatCardDataImpl
import io.toolisticon.kobweb.tabler.style.TablerStyles
import com.varabyte.kobweb.compose.ui.modifiers.attr
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

data object KobwebTabler {
  @Composable
  fun Page(content: @Composable () -> Unit) {
    Div(TablerStyles.page) {
      Div(TablerStyles.pageWrapper) {
        content()
      }
    }
  }

  @Composable
  fun Body(content: @Composable () -> Unit) {
    Div(TablerStyles.pageBody) {
      content()
    }
  }

  @Composable
  fun NavBar(
    title: String,
    icon: String? = null,
    generatedAtText: String? = null,
  ) {
    Div(TablerStyles.navbar) {
      Div(TablerStyles.containerXl) {
        Div(TablerStyles.navbarBrand) {
          H1 {
            Text(icon?.let { "$it " } ?: "")
            Text(title)
          }
        }
        Div(TablerStyles.navbarNav) {
          if (!generatedAtText.isNullOrBlank()) {
            Div(TablerStyles.navbarGeneratedAt) {
              Span(TablerStyles.generatedAtText, attrs = {
                attr("id", "generated-at")
              }) {
                Text(generatedAtText)
              }
            }
          }
        }
      }
    }
  }

  @Composable
  fun Header(
    title: String,
    subtitle: String,
  ) {
    Div(TablerStyles.pageHeader) {
      Div(TablerStyles.containerXl) {
        Div(TablerStyles.pageHeaderRow) {
          Div(TablerStyles.pageHeaderCol) {
            H1(TablerStyles.pageTitle) {
              Text(title)
            }
            P(TablerStyles.textSecondaryMb0) {
              Text(subtitle)
            }
          }
        }
      }
    }
  }

  object Assets {
    fun ensureTablerCssLoaded() = ensureTablerCssLoadedImpl()
    suspend fun ensureApexChartsLoaded() = ensureApexChartsLoadedImpl()
  }

  object Styles {
    val page = TablerStyles.page
    val pageWrapper = TablerStyles.pageWrapper
    val pageBody = TablerStyles.pageBody
    val navbar = TablerStyles.navbar
    val containerXl = TablerStyles.containerXl
    val navbarBrand = TablerStyles.navbarBrand
    val navbarNav = TablerStyles.navbarNav
    val navbarGeneratedAt = TablerStyles.navbarGeneratedAt
    val generatedAtText = TablerStyles.generatedAtText
    val pageHeader = TablerStyles.pageHeader
    val pageHeaderRow = TablerStyles.pageHeaderRow
    val pageHeaderCol = TablerStyles.pageHeaderCol
    val pageTitle = TablerStyles.pageTitle
    val textSecondaryMb0 = TablerStyles.textSecondaryMb0
    val rowCards = TablerStyles.rowCards
    val card = TablerStyles.card
    val cardH100 = TablerStyles.cardH100
    val cardHeader = TablerStyles.cardHeader
    val cardTitle = TablerStyles.cardTitle
    val cardBody = TablerStyles.cardBody
    val colSm6Lg3 = TablerStyles.colSm6Lg3
    val colLg4 = TablerStyles.colLg4
    val colLg8 = TablerStyles.colLg8
    val col12 = TablerStyles.col12
    val h1Mb2 = TablerStyles.h1Mb2
    val subheader = TablerStyles.subheader
    val textSecondaryM0 = TablerStyles.textSecondaryM0
    val mt3 = TablerStyles.mt3
    val mt4 = TablerStyles.mt4
    val my4 = TablerStyles.my4
    val progress = TablerStyles.progress
    fun progressBar(variantClass: String? = null) = TablerStyles.progressBar(variantClass)
    val listGroup = TablerStyles.listGroup
    val listGroupItem = TablerStyles.listGroupItem
    val listGroupRow = TablerStyles.listGroupRow
    val fwSemibold = TablerStyles.fwSemibold
    val textSecondary = TablerStyles.textSecondary
    val badge = TablerStyles.badge
    fun badge(variantClass: String? = null) = TablerStyles.badge(variantClass)
    val alertDangerMb0 = TablerStyles.alertDangerMb0
    val smallTextSecondary = TablerStyles.smallTextSecondary
    val tableResponsive = TablerStyles.tableResponsive
    val table = TablerStyles.table
  }

  object Components {
    @Composable
    fun CardLabel(text: String) = CardLabelImpl(text)

    @Composable
    fun Cards(content: @Composable () -> Unit) = CardsImpl(content)

    @Composable
    fun CardBodyText(text: String) = CardBodyTextImpl(text)

    @Composable
    fun CardValue(text: String) = CardValueImpl(text)

    @Composable
    fun ProgressBar(progressPercent: Int, progressClass: String) =
      ProgressBarImpl(progressPercent, progressClass)

    @Composable
    fun RowList(rows: List<DonutRowImpl>) = RowListImpl(rows)

    @Composable
    fun StatCard(card: StatCardDataImpl) = StatCardImpl(card)
  }

  object Charts {
    @Composable
    fun BarChartCard(
      title: String,
      labels: List<String>,
      jensSeries: List<Int>,
      holgerSeries: List<Int>,
      chartHeightPx: Int = 300,
    ) = BarChartCardImpl(title, labels, jensSeries, holgerSeries, chartHeightPx)

    @Composable
    fun DonutChartCard(
      title: String,
      rows: List<DonutRowImpl>,
      note: String,
      chartHeightPx: Int = 280,
    ) = DonutChartCardImpl(title, rows, note, chartHeightPx)

    @Composable
    fun LineChartCard(
      title: String,
      labels: List<String>,
      jensSeries: List<Int>,
      holgerSeries: List<Int>,
      chartHeightPx: Int = 300,
    ) = LineChartCardImpl(title, labels, jensSeries, holgerSeries, chartHeightPx)
  }
}
