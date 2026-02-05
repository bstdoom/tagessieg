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
3. Uses the `tagessieg import` command to parse the data and append it to the appropriate CSV file in `_data/`.
4. Commits and pushes the updated CSV files back to the repository.
   - **Note:** To trigger subsequent workflows (like page deployment), it attempts to use a Personal Access Token (`secrets.TOKEN`) for the push. If not provided, it falls back to the default `GITHUB_TOKEN`.

### 100_deploy-gh-pages.yml
**Name:** Deploy static content to Pages  
**Trigger:** 
- Push to `main` branch.
- Completion of the **Import Match** workflow (`workflow_run`).
- Manual trigger (`workflow_dispatch`).
**Description:** 
1. Prepares the site content by running `tagessieg init` and `tagessieg reveal`.
2. Deploys the generated static site to GitHub Pages.
   - **Note on Triggering:** Since GitHub Actions triggered by `GITHUB_TOKEN` cannot trigger other workflows, this workflow uses `workflow_run` as a secondary trigger to ensure deployment happens automatically after a match is successfully imported, even if the push was made with the default token.
   - It only proceeds if the triggering `workflow_run` was successful.
