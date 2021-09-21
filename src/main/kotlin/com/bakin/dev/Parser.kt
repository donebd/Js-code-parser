package com.bakin.dev

import java.io.File
import kotlin.text.StringBuilder

class Parser(private val file : File) {

    //just get a normal view code
    private fun parseCode(): List<String>{
        val parsedProgram = mutableListOf<String>()
        val strings = file.readLines()
        for (string in strings.drop(2)) {
            val str = string.trim('\"')
            val newString = StringBuilder()
            for (char in str) {
                if (char == '}') {
                    if (newString.isNotEmpty()) newString.append('\n')
                    parsedProgram.add(newString.toString())
                    newString.clear()
                }
                newString.append(char)
                if (char == '{' || char == ';') {
                    newString.append('\n')
                    parsedProgram.add(newString.toString())
                    newString.clear()
                }
            }
            if (newString.isNotEmpty()) parsedProgram.add(newString.toString())
        }
    return parsedProgram
    }

    //remove unusable code
    private fun cleanCode(strings: List<String>) : List<String> {
        var skip = false
        val cleanProgram = mutableListOf<String>()
        for (string in strings) {
            when {

                string.contains("grid(") -> skip = true
                string.contains(")        );") -> skip = false
                string.contains("var grid;") -> cleanProgram.add(string.replace("var grid;", ""))
                !skip && !string.contains("var o = [];") &&
                        !string.contains("var pos;") &&
                        !string.contains("grid = function(p) {") &&
                        !string.contains("o.push(p);") &&
                        !string.contains("pos = function(p) {") &&
                        !string.contains("pos(union(obj));") &&
                        string != "\n" && string != "};\n" -> cleanProgram.add(string)
            }
        }
        cleanProgram.add(" ()")
        return cleanProgram
    }

    //
    //!!NOT FOR ALL PROGRAMS!!
    //
    private fun refactorProgram(strings: List<String>): List<String> {
        var union = false // for many objects
        val refactoredProgram = mutableListOf<String>()
        for (string in strings) {
            when {
                string.contains("Rez") -> {
                    refactoredProgram.add(string.replace("Rez", "res"))
                }
                string.contains("obj =") -> {
                    if (string.contains("obj = new Array()")) {
                        refactoredProgram.add(string.replace("obj = new Array()", "res"))
                    } else if (string.contains("obj = []")) {
                        refactoredProgram.add(string.replace("obj = []", "res"))
                    } else {
                        refactoredProgram.add(string.replace("obj", "res"))
                    }
                }
                string.contains("obj.push(") && !union -> {
                    refactoredProgram.add("res = " + string.removePrefix("obj.push("))
                }
                string.contains("}));") && !union -> {
                    refactoredProgram.add("});\n")
                    union = true
                }
                string.contains("obj.push(") && union -> {
                    refactoredProgram.add("res = union(res, ${string.removePrefix("obj.push(")}")
                }
                string.contains("return o;") -> refactoredProgram.add("return res;\n")
                else -> refactoredProgram.add(string)
            }
        }
        return refactoredProgram
    }

    fun printSourceCode() {
        val program = parseCode().joinToString("")
        println(program)
    }

    fun printProgram() {
        val program = cleanCode(parseCode()).joinToString("")
        println(program)
    }

    //EXPEREMENTAL!!
    fun printRefactoredProgram() {
        println(getRefactoredProgram())
    }

    fun getRefactoredProgram(): String {
        return refactorProgram(cleanCode(parseCode())).joinToString("")
    }
}