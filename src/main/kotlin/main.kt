package io.github.bstdoom.tagessieg

import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import io.github.bstdoom.tagessieg.command.ImportMatchCmd
import io.github.bstdoom.tagessieg.command.InfoCmd
import io.github.bstdoom.tagessieg.command.RootCmd

fun main(vararg args: String) = RootCmd()
  .subcommands(
    InfoCmd(),
    ImportMatchCmd()
  )
  .main(args)

typealias HtmlString = String
typealias JsonString = String
