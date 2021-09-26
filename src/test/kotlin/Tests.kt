import com.bakin.dev.Parser
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class Tests {

    private fun String.toSpecialFormat() : String{
        return this.replace("\n", "").replace("\r", "")
    }

    @Test
    fun testRefactoredLab1() {
        val file = File("src/test/resources/lab1.txt")
        val parse = Parser(file)
        assertEquals(parse.getRefactoredProgram().toSpecialFormat(),
        File("src/test/resources/correct/lab1.txt").readText().toSpecialFormat())
    }

    @Test
    fun testRefactoredLab2() {
        var file = File("src/test/resources/lab2_1.txt")
        var parse = Parser(file)
        assertEquals(parse.getRefactoredProgram().toSpecialFormat(),
        File("src/test/resources/correct/lab2_1.txt").readText().toSpecialFormat())

        file = File("src/test/resources/lab2_2.txt")
        parse = Parser(file)
        assertEquals(parse.getRefactoredProgram().toSpecialFormat(),
        File("src/test/resources/correct/lab2_2.txt").readText().toSpecialFormat())

        file = File("src/test/resources/lab2_3.txt")
        parse = Parser(file)
        assertEquals(parse.getRefactoredProgram().toSpecialFormat(),
            File("src/test/resources/correct/lab2_3.txt").readText().toSpecialFormat())

        file = File("src/test/resources/lab2_4.txt")
        parse = Parser(file)
        assertEquals(parse.getRefactoredProgram().toSpecialFormat(),
            File("src/test/resources/correct/lab2_4.txt").readText().toSpecialFormat())

        file = File("src/test/resources/lab2_5.txt")
        parse = Parser(file)
        assertEquals(parse.getRefactoredProgram().toSpecialFormat(),
            File("src/test/resources/correct/lab2_5.txt").readText().toSpecialFormat())

        file = File("src/test/resources/lab2_6.txt")
        parse = Parser(file)
        assertEquals(parse.getRefactoredProgram().toSpecialFormat(),
            File("src/test/resources/correct/lab2_6.txt").readText().toSpecialFormat())
    }

    @Test
    fun testRefactoredLab3() {
        val file = File("src/test/resources/lab3.txt")
        val parse = Parser(file)
        assertEquals(parse.getRefactoredProgram().toSpecialFormat(),
            File("src/test/resources/correct/lab3.txt").readText().toSpecialFormat())
    }
}