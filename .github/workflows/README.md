# GitHub Workflows

This directory contains the GitHub Actions workflows for the `tagessieg` project.

## Workflow Overview

### 010_fix-issue-date.yml

**Name:** Fix Issue Date  
**Trigger:** Triggered when an issue with the label `spieltag` is opened or edited.  
**Description:** If the issue title contains the placeholder `dd.mm.yyyy`, this workflow automatically replaces it with the current date in the format `dd.MM.yyyy`. This ensures that match reports have the correct date in their title even when created from a template.

### 020_import-match.yml
**Name:** Import Match  
**Trigger:** Triggered when an issue with the label `spieltag` is closed.  
**Description:** 
1. Fetches the issue content and all its comments.
2. Formats the data into a JSON file (`match.json`).
3. Uses the `tagessieg import` command to parse the data and append it to the appropriate CSV file in `docs/data/`.
4. Commits and pushes the updated CSV files back to the repository.

### 030_create-statistics.yml
**Name:** Create Statistics  
**Trigger:** Triggered on a push to `main` that modifies CSV files in `docs/data/`, or manually via `workflow_dispatch`.  
**Description:** 
1. Detects which CSV files were changed (production vs. test).
2. Runs the `tagessieg reveal` command to generate the `index.html` (Reveal.js presentation) and updated statistics.
3. Commits and pushes the generated `index.html` and assets to the repository.

### 100_deploy-gh-pages.yml
**Name:** Deploy static content to Pages  
**Trigger:** Triggered on any push to the `main` branch.  
**Description:** 
1. Prepares a temporary environment with the necessary data and assets.
2. Re-generates the Reveal.js presentation within this environment.
3. Uploads the generated content as a GitHub Pages artifact.
4. Deploys the artifact to the GitHub Pages environment.
