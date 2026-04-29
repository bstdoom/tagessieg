# ADR 007: Prefer a Silk-First Kobweb Mental Model for `kobweb-tabler`

Date: 2026-04-29

## Status

Accepted

## Context

We are designing `kobweb-tabler` as a support library for building Tabler-styled Kobweb sites.

Earlier, we approached the library from the raw Compose HTML / DOM angle, which made the public API feel too close to HTML (`Div`, `Span`, `attrs`, `toAttrs`, nested `Div`s). That is technically possible, but it is not the simplest mental model for consumers of an opinionated Kobweb support library.

Kobweb’s Silk layer is intentionally designed to provide a higher-level API built on top of Compose HTML. The docs describe it as a UI layer that gives you “Rows, Columns, Boxes, and Modifiers” and note that Silk and Compose HTML can be interleaved.

For `kobweb-tabler`, that is the better abstraction boundary:

- public API should feel like a widget library, not a DOM library
- Tabler layout patterns should be represented with Kobweb/Silk primitives where possible
- raw Compose HTML should remain an implementation detail or escape hatch

## Decision

We will work in the Kobweb/Silk world wherever possible when building `kobweb-tabler`.

That means:

- prefer Kobweb layout primitives like `Box`, `Row`, and `Column`
- prefer Silk widgets and Silk-style composition for reusable UI pieces
- use `Modifier` as the primary styling boundary
- keep Tabler CSS classes encapsulated in `TablerStyles`
- use raw Compose HTML only when it gives us a clearer or more semantic result, especially for tables, form semantics, or special attributes

The goal is to represent a Tabler concept such as a `Card` or `Header` as a simple Kobweb widget, not as a pile of nested DOM tags in the public API.

## Consequences

- The public API becomes easier to reason about.
- Call sites stay closer to the Kobweb/Silk examples in the docs.
- `TablerStyles` remains useful as the style-token layer.
- We may still need some Compose HTML in implementation details, but consumers should not have to think in terms of `attrs`, `toAttrs`, or raw DOM tags.
- Some widgets will naturally map better to Silk/Kobweb primitives than others; this ADR establishes the direction, not an all-or-nothing rule.

## Current Kobweb/Silk Component Inventory

The following components are available in the current Kobweb 0.24.0 artifacts and are candidates for reuse in `kobweb-tabler`.

### Layout primitives from `com.varabyte.kobweb:kobweb-compose-js`

- `Box` - generic container with `Modifier`; good fit for Tabler cards, shells, and panels.
- `Row` - horizontal layout primitive; good fit for navbar rows, card header rows, and inline controls.
- `Column` - vertical layout primitive; good fit for stacked card content and page sections.
- `Spacer` - spacing helper; useful when a layout needs explicit gaps.

### Silk foundation widgets from `com.varabyte.kobweb:silk-foundation-js`

- `SpanText` - helper span for text that should still accept `Modifier`; useful for inline labels and small text tokens.

### Silk widgets from `com.varabyte.kobweb:silk-widgets-js`

- `Button` - primary button widget; useful for tab actions, filters, and navigation actions.
- `Checkbox` - form control; useful if we add interactive filters or settings.
- `Input` - text input widget; useful for search and filtering.
- `Label` - form label widget; useful for accessible form groups.
- `Switch` - toggle control; useful for settings or display modes.
- `Divider` - structural separator; useful for section breaks.
- `SimpleGrid` - grid-style layout helper; useful for responsive card dashboards.
- `Surface` - elevated/contained panel; potentially useful as an alternative to a Tabler card shell.
- `Toc` - table-of-contents widget; relevant for documentation-style pages, not the scoreboard UI.
- `Popover` - floating panel; useful for contextual menus or details.
- `Popup` - generic overlay; useful for modal-style interactions.
- `Tooltip` - hover hint; useful for compact dashboard labels.
- `Overlay` - overlay layer for popups/dialogs.
- `KeepPopupOpenStrategy` - overlay interaction strategy.
- `OpenClosePopupStrategy` - overlay interaction strategy.

### Silk widgets from `com.varabyte.kobweb:silk-widgets-kobweb-js`

- `Link` - Kobweb-aware navigation link; a good fit for navigation items or external references.
- `Image` - Silk image widget; useful for avatars, logos, and decorative assets.
- `Canvas` - drawing surface; useful for custom chart or visualization work.
- `Tabs` - tabbed navigation widget; useful if Tabler-style pages get multiple dashboard views.
- `Toc` - the Kobweb-specific TOC integration when used with Kobweb page routing and docs patterns.

### Silk icon components

The current artifact set also includes many ready-made icons. These are useful when `kobweb-tabler` wants a small, dependency-free icon API without bringing in a separate icon pack.

Available icons include:

- `ArrowBackIcon`
- `ArrowDownIcon`
- `ArrowForwardIcon`
- `ArrowUpIcon`
- `AttachmentIcon`
- `CalendarIcon`
- `CheckCircleIcon`
- `CheckIcon`
- `ChevronDownIcon`
- `ChevronLeftIcon`
- `ChevronRightIcon`
- `ChevronUpIcon`
- `CircleIcon`
- `ClipboardIcon`
- `CloseIcon`
- `CodeIcon`
- `DownloadIcon`
- `EditIcon`
- `ExclaimIcon`
- `EyeIcon`
- `EyeOffIcon`
- `HamburgerIcon`
- `IndeterminateIcon`
- `InfoIcon`
- `LightbulbIcon`
- `LockIcon`
- `MinusIcon`
- `MoonIcon`
- `PlusIcon`
- `QuestionIcon`
- `QuoteIcon`
- `SearchIcon`
- `SettingsIcon`
- `SquareIcon`
- `StopIcon`
- `SunIcon`
- `TrashIcon`
- `UploadIcon`
- `UserIcon`
- `WarningIcon`

## Notes For Tabler Mapping

These are the first candidates for mapping Tabler concepts to Kobweb/Silk primitives:

- `Page` -> `Box` or a very small Silk shell widget
- `NavBar` -> `Row` plus nested `Box` and `Link`
- `Header` -> `Column` or `Box`
- `Card` -> `Box`
- `CardHeader` -> `Row` or `Box`
- `CardBody` -> `Column` or `Box`
- `StatCard` -> `Box`
- `RowList` -> `Column` with row items
- `ChartCard` -> `Box`
- `TableCard` -> likely still needs Compose HTML table semantics, but should be wrapped by a higher-level Tabler widget

## Related Decisions

- ADR 006 defines the single `kobweb-tabler` support library and its repository layout.
- This ADR defines the rendering and abstraction strategy inside that library.
