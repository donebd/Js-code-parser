package com.bakin.dev

import java.io.File
import kotlin.text.StringBuilder

class Parser(val file : File) {

    //just get a normal view code
    fun parseCode(): List<String>{
        val parsedProgram = mutableListOf<String>()
        val strings = file.readLines()
        for (string in strings.drop(2)) {
            val str = string.trim('\"')
            val newString = StringBuilder()
            for (char in str) {
                if (char == '}') {
                    newString.append('\n')
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
                !skip && !string.contains("var o = [];") &&
                        !string.contains("var pos;") &&
                        !string.contains("var grid;") &&
                        !string.contains("grid = function(p) {") &&
                        !string.contains("o.push(p);") &&
                        !string.contains("pos = function(p) {") &&
                        !string.contains("pos(union(obj));") &&
                        string != "\n" && string != "};\n" -> cleanProgram.add(string)
            }
        }
        cleanProgram.add("} ()")
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
                string.contains("var obj =") -> refactoredProgram.add("var res = ")
                string.contains("obj.push(") && !union -> {
                    refactoredProgram.add(string.removePrefix("obj.push("))
                }
                string.contains("}));") && !union -> {
                    refactoredProgram.add("});\n")
                    union = true
                }
                string.contains("obj.push(") && union -> {
                    refactoredProgram.add("res = union(res, ${string.removePrefix("obj.push(")}")
                }
                string.contains("}));") && union -> {
                    refactoredProgram.add(string)
                }
                string.contains("return o;") -> refactoredProgram.add("return res;\n")
                else -> refactoredProgram.add(string)
            }
        }
        return refactoredProgram
    }

    fun printProgram() {
        val program = cleanCode(parseCode()).joinToString("")
        println(program)
    }

    //EXPEREMENTAL!!
    fun printRefactoredProgram() {
        val program = refactorProgram(cleanCode(parseCode())).joinToString("")
        println(program)
    }
}