package learn.crkotlin.learn.crkotlin.p2c2coroutinecontext

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import learn.crkotlin.learn.common.printMillisInBackground
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

fun main() {
    printMillisInBackground()

    f01usingCustomContext()
}

private suspend fun printNext() {
    coroutineContext[CounterPrintingContext]?.printNext()
}

private fun f01usingCustomContext() = runBlocking {
    withContext(CounterPrintingContext("Outer")) {
        printNext() // Outer: 0
        launch {
            printNext() // Outer: 1
            launch {
                printNext() // Outer: 2
            }
            launch(CounterPrintingContext("Inner")) {
                printNext() // Inner: 0
                printNext() // Inner: 1
                launch {
                    printNext() // Inner: 2
                }
            }
        }
        printNext() // Outer: 3
    }
}

/*
Общей практикой является использовать companion object в качестве key.
 */
private class CounterPrintingContext(
    private val name: String
) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key
    private var nextNumber = 0

    fun printNext() {
        println("$name: $nextNumber")
        nextNumber++
    }
    companion object Key : CoroutineContext.Key<CounterPrintingContext>
}
