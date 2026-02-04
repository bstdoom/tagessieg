package io.github.bstdoom.tagessieg.command

import io.github.bstdoom.tagessieg.infrastructure.copyToRecursive
import kotlin.io.path.copyTo

class InitCmd : SubCommand(name = NAME, help = "Initialize the working directory (copies csv and assets/).") {
  companion object {
    const val NAME = "init"
  }

  override fun run() {
    dryRun("would initialize workDir, copy from source='${ctx.dataDir} to target='${ctx.workDir}'") {
      ctx.dataDir.copyToRecursive(ctx.workDir)
      echof("copied from source='${ctx.dataDir} to target='${ctx.workDir}'")
    }
  }
}
