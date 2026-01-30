#!/usr/bin/env just --justfile


# runs the tagessieg cli with args, e.g. `tagessieg -q import-matches --file matches.json`
[group('tagessieg')]
tagessieg +args:
  @./gradlew run --quiet --console=plain --no-daemon --args="{{args}}"

# import match from json file
[group('tagessieg')]
import-matches json_file:
  @just tagessieg "import-match --file "{{json_file}}""

# runs `tagessieg info`
[group('tagessieg')]
info +ARGS="":
  @just tagessieg info {{ARGS}}

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
