package com.bakin.dev

import java.io.File

fun main(args: Array<String>) {
    val file = File("src/main/kotlin/com/bakin/dev/test.txt")
    val parser = Parser(file)
    parser.printRefactoredProgram()
}