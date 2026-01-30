#!/usr/bin/env just --justfile

# private recipe to run gradle with common flags
_run-gradle args:
  @./gradlew run --quiet --console=plain --no-daemon --args="{{args}}"

# import match from json file
[group('tagessieg')]
import-matches json_file:
  @just _run-gradle "import-match --file "{{json_file}}""

# runs `tagessieg info`
[group('tagessieg')]
info +ARGS="":
  @just _run-gradle info {{ARGS}}


build:
  ./gradlew -x test build

dependencies:
  ./gradlew -q dependencies --configuration compileClasspath > ./_tmp/dependencies.txt

# Report up-to-date dependencies by com.github.ben-manes.versions
updates:
  ./gradlew dependencyUpdates > ./_tmp/updates.txt

wrapper:
  ./gradlew wrapper --gradle-version=9.3.0

cli_run:
  java -jar ./cli/build/libs/cli.jar

git-push:
  git fetch origin main
  git add .
  git commit -m "Auto commit"
  git push
