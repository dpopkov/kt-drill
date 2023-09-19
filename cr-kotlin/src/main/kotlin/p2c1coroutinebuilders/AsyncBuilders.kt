package learn.crkotlin.p2c1coroutinebuilders

import kotlinx.coroutines.*


fun main() {
    printMillisInBackground()

    /* Без использования Structured Concurrency */
    // async01SimpleUsage()
    // async02StartAndAwait()
    // async03DoNotUseAsyncInsteadOfLaunch()
    // async04ExampleOfObtainingData()

    /* С использованием Structured Concurrency */
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
    println("Do something else")
    val result: Int = resultDeferred.await()
    println(result)
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
    println(res1.await())
    println(res2.await())
    println(res3.await())
    /*
. . . . Text 1
. . . . . . . . . . . . Text 2
Text 3
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
. Hello,
. . . World!
. . . .
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

/**
 * Печатает прошедшие миллисекунды с интервалом в четверть секунды, чтобы визуализировать задержки, вставленные в основные ф-ии.
 * Должна запускаться без блокировки, то есть не внутри runBlocking, чтобы не мешать завершению основной программы.
 */
private fun printMillisInBackground() {
    var currentMillis = 0L
    GlobalScope.launch {
        while (true) {
            print("$currentMillis ")
            delay(250L)
            currentMillis += 250L
        }
    }
}
