---
id: github-gh-auth-bootstrap
title: GitHub CLI auth bootstrap

tags:
  - github
  - gh
  - auth
  - ci

agents:
  - codex

prio: SHOULD

refs:
  - title: GitHub CLI auth login
    url: https://cli.github.com/manual/gh_auth_login
    type: public-doc
    check: periodic
  - title: GitHub CLI environment variables
    url: https://cli.github.com/manual/gh_help_environment
    type: public-doc
    check: periodic

status: active
---

# GitHub CLI auth bootstrap

Use a single, repo-local convention for making `gh` work in non-interactive Codex shells without repeated permission prompts.

## Summary

- Prefer `gh` for Actions runs and workflow inspection when the connector is not enough.
- Do not rely on interactive `gh auth login` state inside agent shells.
- Load `GH_TOKEN` from a local ignored file before running `gh`.
- Keep the token file outside git, for example `.env.agent` or `.env.local`.

## Details

- If the token is missing, fall back to the GitHub connector or ask for the minimal required approval once.
- Reuse the same bootstrap pattern across consumer repos so Codex behavior stays consistent.
- Create an ignored `.env.agent` file in the repo root.
- Store `GH_TOKEN=...` there.
- Optionally store `GH_HOST=github.com` if needed.
- Use a small wrapper script or shell alias that sources the file and then runs `gh`.

## Typical prefixes

- `gh run list`
- `gh run view`
- `gh run watch`
- `gh workflow run`
- `gh auth token`

## Notes

- This is a convention, not a secret store.
- Consumer repos should import the convention and provide their own ignored token file locally.
