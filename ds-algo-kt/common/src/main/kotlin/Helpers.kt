package learn.algo.dsalgokt.common

import java.io.ByteArrayOutputStream
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

/**
 * Печатает в system out вызывая print и возвращает результат вывода.
 */
fun printAndReturn(obj: Any): String =
    runUsingSystemOut { print(obj) }

/**
 * Печатает в system out строку-receiver в кач-ве заголовока и выполняет переданный блок кода.
 */
infix fun String.example(block: () -> Unit) {
    println("--- Example of $this ---")
    block()
}
