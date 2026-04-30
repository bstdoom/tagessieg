---
id: github-gh-issues
title: GitHub Issue Templates and Writing

tags:
  - github
  - gh
  - issue

agents:
  - all

prio: SHOULD

refs:
  - title: Bug report template
    url: https://raw.githubusercontent.com/jangalinski/agents-conventions/main/.github/ISSUE_TEMPLATE/bug_report.md
    type: public-doc
    check: periodic
  - title: Feature request template
    url: https://raw.githubusercontent.com/jangalinski/agents-conventions/main/.github/ISSUE_TEMPLATE/feature_request.md
    type: public-doc
    check: periodic

status: active
---

# GitHub Issue Templates and Writing

All agents SHOULD respect repository issue templates when they are available and fit the task.

## Summary

- Use the given template sections in the intended order.
- Prefer the repository issue template structure when opening or drafting issues.
- If a template is available, follow it instead of inventing a custom issue body.
- Prefer meta-based labels for issues, such as `Type:Bug`, `Type:Feature`, and `Type:Dependencies`, check with existing lables and make a suggestion to the user to rename them.
- Encourage consumer repos to use the same meta-label pattern so issue triage stays consistent across repositories.

## Acceptance Criteria

- Write acceptance criteria as a set of one or more fenced Gherkin code blocks.
- Keep each acceptance criterion in exactly one code block with its own `Given / When / Then` flow.
- Allow a single `#` comment line immediately above `Given` to describe the criterion in plain language.
- Keep each scenario minimal, concrete, and observable.

### Example

```gherkin
# A trivial calculation should return the sum.
Given a calculator with input "2 + 2"
When the expression is evaluated
Then the result is "4"
```

## Details

- Use the template sections `Desired behavior`, `Current behavior`, `Hint`, `Scope`, and `Acceptance criteria` when they are available.
- Keep `desired` focused on the end state.
- Keep `current` focused on the present behavior or gap.
- Use `hint` for implementation ideas, constraints, or links.
- Use `scope` to bound the files or workflows involved.
- Use `acceptance` for the outcome definition and testable completion criteria.
