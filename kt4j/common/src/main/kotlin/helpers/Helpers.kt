package learn.kt4j.common.helpers

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream

/**
 * Исполняет переданный блок кода и возвращает строковый результат
 * его вывода в system out.
 */
fun runUsingSystemOut(block: () -> Unit): String {
    val standardOut: PrintStream = System.out
    val outputStreamCaptor = ByteArrayOutputStream()
    System.setOut(PrintStream(outputStreamCaptor))

    block()

    val result = outputStreamCaptor.toString()
    System.setOut(standardOut)
    return result
}

fun runUsingSystemInOut(input: String, block: () -> Unit): String {
    val standardOut: PrintStream = System.out
    val standardIn: InputStream = System.`in`
    val outputStreamCaptor = ByteArrayOutputStream()
    System.setOut(PrintStream(outputStreamCaptor))
    System.setIn(input.byteInputStream(Charsets.UTF_8))

    block()

    val result = outputStreamCaptor.toString()
    System.setOut(standardOut)
    System.setIn(standardIn)
    return result
}

/**
 * Печатает в system out вызывая print и возвращает результат вывода.
 */
fun printAndReturn(obj: Any): String =
    runUsingSystemOut { print(obj) }
