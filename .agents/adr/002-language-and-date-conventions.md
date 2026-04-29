# ADR 002: Language and Date Format Conventions

## Status
Accepted

## Context
The project serves German-speaking users but is developed and maintained in an English-centric environment (GitHub). Consistency in language and date formatting is crucial for both user experience and technical reliability.

## Decision
- **User-facing components** (forms, templates, sites) will use the **German** language.
- **Internal elements** (issues, documentation, README, bot communication, and code) will use **English**.
- **Dates** will follow these conventions:
    - User-facing: `dd.MM.yyyy` (German locale).
    - Internal (code, data, filenames): `yyyy-MM-dd` (ISO-8601 variant).

## Consequences
- Clear distinction between user-facing and developer-facing content.
- Avoids ambiguity in date parsing across different system components.
- Ensures a consistent experience for the target audience while maintaining standard development practices.
