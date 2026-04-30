package io.github.bstdoom.tagessieg.application.port.out

import io.github.bstdoom.tagessieg.domain.StatisticData
import io.github.bstdoom.tagessieg.infrastructure.FileContent

/**
 * Generates a static site from the given [StatisticData]. The static site may contain multiple files.
 * All throws files are relative to each other and might be written to FileSystem under a common
 * parent directory.
 */
interface GenerateStaticSite : (StatisticData) -> List<FileContent>
