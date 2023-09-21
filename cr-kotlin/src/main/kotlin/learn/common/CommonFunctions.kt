package learn.crkotlin.learn.common

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

/** Печатает текст с указанием имени потока */
fun log(msg: Any) {
    println("[${Thread.currentThread().name}] $msg")
}

/** Печатает текст с указанием имени потока и имени корутины */
suspend fun logNamed(msg: String) {
    val name = coroutineContext[CoroutineName]?.name
    println("[${Thread.currentThread().name}] [$name] $msg")
}

suspend fun logNamedJob(msg: String) {
    val name = coroutineContext[CoroutineName]?.name
    val job = coroutineContext.job
    println("[${Thread.currentThread().name}] [$name] [$job] $msg")
}

/**
 * Печатает в фоне прошедшие миллисекунды с интервалом в четверть секунды, чтобы визуализировать задержки, вставленные в основные ф-ии.
 * Должна запускаться без блокировки, то есть не внутри runBlocking, чтобы не мешать завершению основной программы.
 */
@OptIn(DelicateCoroutinesApi::class)
fun printMillisInBackground() {
    var currentMillis = 0L
    GlobalScope.launch {
        while (true) {
            print("$currentMillis ")
            delay(250L)
            currentMillis += 250L
        }
    }
}
