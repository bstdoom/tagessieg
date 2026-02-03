package io.github.bstdoom.tagessieg

import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import io.github.bstdoom.tagessieg.command.*
import io.github.bstdoom.tagessieg.infrastructure.TagessiegProperties

val tagessieg: RootCmd get() = RootCmd()
val properties = TagessiegProperties.load()

fun main(vararg args: String) = tagessieg.subcommands(
  InfoCmd(),
  InitWorkDirCmd(),
  ImportMatchCmd(),
  CreateStatisticsCmd(),
  GenerateRevealCmd()
).main(args)
