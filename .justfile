#!/usr/bin/env just --justfile

# runs `tagessieg info`
[group('tagessieg')]
info +ARGS="":
  @./gradlew run --quiet --console=plain --no-daemon --args="info {{ARGS}}"


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
