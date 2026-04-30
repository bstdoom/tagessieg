---
id: general
title: General Convention Usage

tags:
  - general
  - convention

agents:
  - all

prio: MUST

refs:
  - title: MoSCoW Prioritisation
    url: https://www.agilebusiness.org/page/ProjectFramework_10_MoSCoWPrioritisation
    type: public-doc
    check: periodic

status: active
---

# General Convention Usage

Use this convention to read, interpret, and apply all files under `.agents/**/*.md`.

## Summary

- Treat the convention markdown file as the primary source of truth.
- Use the `refs` section for supporting background and external detail.
- If a reference and a convention file disagree, the convention file wins.
- Apply the tags, agents, and priority values exactly as defined by frontmatter.
- When a repository has both shared `.agents/shared/` files and local `.agents/**/*.md` files, read them together as one catalogue.

## Frontmatter Semantics

- `tags` classify the topic or domain of the convention.
- `agents` identify which agent families must follow the convention.
- `prio` states how strongly the convention applies.
- `refs` provide supporting documentation and background material.

## Allowed Tags

- `general`: cross-cutting convention guidance and catalogue policy.
- `convention`: rules about conventions, their structure, and how to use them.
- `code`: source-code conventions and code-shape rules.
- `style`: stylistic guidance that affects how code or docs should look.
- `format`: whitespace, indentation, and formatting rules.
- `github`: GitHub platform, repository, PR, issue, and workflow topics.
- `gh`: GitHub CLI usage and automation.
- `issue`: GitHub issue templates, triage, and issue-writing conventions.
- `auth`: authentication and bootstrap concerns.
- `ci`: continuous integration and workflow automation.
- `kotlin`: Kotlin language conventions and Kotlin-specific code organization.

## Allowed Agents

- `all`: applies to every agent family.
- `codex`: applies to Codex agents in this repository.

## Allowed Priorities

- `MUST`: mandatory, follow unless the convention explicitly narrows the scope.
- `SHOULD`: expected by default, deviate only with a reason.
- `COULD`: optional guidance that is useful but not required.
- `WONT`: intentionally out of scope for the current convention or timebox.

## Notes

- Use MoSCoW as the priority vocabulary for new convention files.
- Keep the catalogue consistent by updating this file when new allowed values are introduced.
- Use [../.catalog.yml](../.catalog.yml) when tooling or consumers need a machine-readable copy of the allowed values.
- When in doubt, prefer the convention markdown over any supporting reference.
