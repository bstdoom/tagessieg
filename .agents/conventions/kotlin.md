# Kotlin coding conventions

We follow the Kotlin coding conventions: https://kotlinlang.org/docs/coding-conventions.html

Especially tha directory structure.

> In pure Kotlin projects, the recommended directory structure follows the package structure with the common root package omitted. For example, if all the code in the project is in the org.example.kotlin package and its subpackages, files with the org.example.kotlin package should be placed directly under the source root, and files in org.example.kotlin.network.socket should be in the network/socket subdirectory of the source root.

So never(!) create any directories with the root package name. Never create any directories with name "net".
