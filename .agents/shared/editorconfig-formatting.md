---
id: editorconfig-formatting
title: EditorConfig Formatting

tags:
  - code
  - style
  - format

agents:
  - all

prio: MUST

refs:
  - title: EditorConfig
    url: https://editorconfig.org/
    type: public-doc
    check: periodic

status: active
---

# EditorConfig Formatting

Use `.editorconfig` as the central coding style declaration for all repositories that consume this convention.

## Details

- Respect the formatting rules defined in `.editorconfig`.
- Use 2 spaces for indentation when the editorconfig settings require it.
- Keep `insert_final_newline = true` so files always end with a newline.
- Trim trailing whitespace.
- Do not duplicate formatting rules in other convention files unless they cannot be expressed in `.editorconfig`.
