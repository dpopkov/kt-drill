package learn.crkotlin.p2c1coroutinebuilders

import kotlinx.coroutines.*
import learn.crkotlin.learn.common.log
import learn.crkotlin.learn.common.logNamed
import learn.crkotlin.learn.common.printMillisInBackground
import kotlin.system.measureTimeMillis


fun main() {
    printMillisInBackground()

    /* Без использования Structured Concurrency */
    // async01SimpleUsage()
    // async02StartAndAwait()
    // async03DoNotUseAsyncInsteadOfLaunch()
    // async04ExampleOfObtainingData()

    /* С использованием Structured Concurrency */
    // async05exampleGlobalScope()
    async06exampleStructured()
}

/**
 async coroutine builder похож на launch, но он создан, чтобы возвращать значение.
 Это значение возвращается лямбда выражением, а если точнее, то аргументом функционального типа,
 помещенным в последнюю позицию параметров.
 Функция async возвращает объект типа Deferred<T>, где T это тип производимого значения.
 Deferred имеет suspending метод await, который возвращает это значение когда оно готово.
 */
private fun async01SimpleUsage() = runBlocking {
    val resultDeferred: Deferred<Int> = GlobalScope.async {
        delay(1000L)
        42
    }
    log("Do something else")
    val result: Int = resultDeferred.await()
    log(result)
    /*
Output:
0 [main] Do something else
250 500 750 1000 [main] 42
     */
}

/**
 Как и launch, async стартует coroutine сразу же, как только вызвана.
 То есть это способ стартовать несколько "процессов" одновременно и затем ожидать от них результатов.
 Возвращаемый Deferred хранит в себе значение, когда оно произведено и, как только оно готово,
 то сразу же может быть возвращено методом await.
 Однако если мы вызовем await до того, как значение произведено, то будем suspended пока значение не готово.
 */
private fun async02StartAndAwait() = runBlocking {
    val res1: Deferred<String> = GlobalScope.async {
        delay(1000L)
        "Text 1"
    }
    val res2: Deferred<String> = GlobalScope.async {
        delay(4000L)
        "Text 2"
    }
    val res3: Deferred<String> = GlobalScope.async {
        delay(2000L)
        "Text 3"
    }
    log(res1.await())
    log(res2.await())
    log(res3.await())
    /*
Output:
0 250 500 750 1000 [main] Text 1
1250 1500 1750 2000 2250 2500 2750 3000 3250 3500 3750 [main] Text 2
[main] Text 3
     */
}

/**
 То как работает async это очень похоже на launch, но asycn имеет дополнительную поддержку
 для возвращения значения. Если бы все функции launch были бы заменены на async, то
 код продолжил бы нормально работать. Но так делать не следует.
 async предназначен для того, чтобы производить значение, то есть если нам не нужно
 значение, то мы должны использовать launch.
 */
private fun async03DoNotUseAsyncInsteadOfLaunch() {
    runBlocking {
        GlobalScope.async {
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        delay(2000L)
    }
    /*
Output:
0 Hello,
250 500 750 World!
1000 1250 1500 1750
     */
}

/**
 Функция async часто используется для параллельной обработки,
 такой как получение данных из нескольких источников, чтобы позже скомбинировать.
 */
private fun async04ExampleOfObtainingData() {
    runBlocking {
        val newsRepo = NewsRepo(
            newsDelay = 1000L,
            summaryDelay = 3000L,
        )
        val news = GlobalScope.async {
            newsRepo.getNews()
                .sortedByDescending { it }
        }
        println("Getting news...")
        // Можно было бы завернуть newsSummary в await тоже, но это излишне
        val newsSummary = newsRepo.getNewsSummary()
        println("News: ${news.await()}")
        println("News summary: $newsSummary")
    }
    /*
Output:
0 Getting news...
250 500 750 1000 1250 1500 1750 2000 2250 2500 2750 News: [news2, news1]
News summary: News Summary
     */
}

private class NewsRepo(val newsDelay: Long, val summaryDelay: Long) {
    suspend fun getNews(): List<String> {
        delay(newsDelay)
        return listOf("news1", "news2")
    }

    suspend fun getNewsSummary(): String {
        delay(summaryDelay)
        return "News Summary"
    }
}

/*
Structured Concurrency
----------------------

Если корутина стартует на GlobalScope, то программа не будет ее ждать.
Корутины не блокируют потоков, и ничто не предотвратит программу от завершения.
Поэтому в следующем примере в конце runBlocking нужно поместить дополнительный delay,
чтобы увидеть напечатанным финальный "World!"
 */
private fun async05exampleGlobalScope() = runBlocking {
    GlobalScope.launch {
        delay(1000L)
        logNamed("World!")
    }
    GlobalScope.launch {
        delay(1000L)
        logNamed("World!")
    }
    log("Hello,")
    // delay(2000L)
    /*
Output:
0 [main] Hello,
     */
}

/*
И launch, и async имеют ресивером CoroutineScope, и, следовательно, оба могут вызываться
на ресивере предоставляемом runBlocking, так что launch становится потомком runBlocking.
Как родитель runBlocking будет ждать потомков, пока все потомки не завершатся.
Родитель предоставляет scope своим потомкам, и они вызываются в этом scope.
runBlocking может быть использован как root корутина.
 */
private fun async06exampleStructured() = runBlocking { // this: CoroutineScope
    launch { // this: CoroutineScope
        delay(3000L)
        logNamed("World-1!")
    }
    launch { // this: CoroutineScope
        delay(1000L)
        logNamed("World-2!")
    }
    log("Hello,")
    /*
Output:
0 [main] Hello,
250 500 750 1000 [main] [null] World-2!
1250 1500 1750 2000 2250 2500 2750 [main] [null] World-1!
     */
}