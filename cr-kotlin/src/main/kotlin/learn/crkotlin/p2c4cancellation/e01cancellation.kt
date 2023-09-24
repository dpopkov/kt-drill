package learn.crkotlin.learn.crkotlin.p2c4cancellation

import kotlinx.coroutines.*
import learn.crkotlin.learn.common.logNamedJob
import learn.crkotlin.learn.common.printMillisInBackground
import javax.tools.Tool
import kotlin.random.Random

fun main() {
    printMillisInBackground()

    runBlocking {
        // f01cancelAndJoin()
        // f02cancelMany()
        // f03howDoesCancellationWork()
        // f04finally()
        // f05justOneMoreCall()
        // f06suspendAfterCancel()
        // f07invokeOnCompletion()
        // f08cannotStopWhenNoSuspension()
        // f09stopUsingYield()
        f10stopUsingStateCheck()
    }
}

/*
Вызов cancel приводит к следующим эффектам:
- корутина завершает работу на первой suspension point;
- если у корутины есть потомки, то они тоже cancelled, но родитель не трогается;
- после cancel job не может быть использован как родитель для новых корутин.
 */
private suspend fun f01cancelAndJoin(): Unit = coroutineScope {
    logNamedJob("Start")
    val job = launch(CoroutineName("Inner")) {
        repeat(1_000) { i ->
            delay(200L)
            logNamedJob("Printing $i")
        }
    }
    delay(1100L)
    job.cancel()
    job.join() // suspend до завершения корутины job
    // OR use job.cancelAndJoin()
    logNamedJob("Cancelled successfully")
    /*
Output:
0 [main] [Outer] [BlockingCoroutine{Active}@e73f9ac] Start
[main] [Inner] [StandaloneCoroutine{Active}@64b8f8f4] Printing 0
250 [main] [Inner] [StandaloneCoroutine{Active}@64b8f8f4] Printing 1
500 [main] [Inner] [StandaloneCoroutine{Active}@64b8f8f4] Printing 2
750 [main] [Inner] [StandaloneCoroutine{Active}@64b8f8f4] Printing 3
1000 [main] [Inner] [StandaloneCoroutine{Active}@64b8f8f4] Printing 4
[main] [Outer] [BlockingCoroutine{Active}@e73f9ac] Cancelled successfully
     */
}

/*
Job созданный фабричной ф-ей Job() может быть отменена и это используется
для отмены группы корутин за раз.
 */
private suspend fun f02cancelMany(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        repeat(1_000) { i ->
            delay(200L)
            println("Printing $i")
        }
    }
    launch(job) {
        repeat(1_000) { i ->
            delay(200L)
            println("Printing ${i * 10_000}")
        }
    }
    delay(1100L)
    job.cancelAndJoin()
    println("Cancelled successfully")
}

/*
Когда job отменяется, его состояние меняется на "Cancelled", затем выбрасывается CancellationException
на первой suspension point. Это исключение может быть поймано в try-catch, но рекомендуется пробрасывать его.
Важно, что корутина не просто останавливается - она отменяется внутренне с выбрасыванием исключения.
Поэтому мы можем в блоке finally сделать все необходимые действия (закрыть файл или соединение с бд).
Поскольку большинство закрывающих ресурсы механизмов полагаются на блок finally (например чтение файла с использованием
useLines), то можно просто об этом не беспокоиться.
 */
private suspend fun f03howDoesCancellationWork(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        try {
            repeat(1_000) { i ->
                delay(200L)
                println("Printing $i")
            }
        } catch (e: CancellationException) {
            println(e)
            throw e
        }
    }
    delay(1100L)
    job.cancelAndJoin()
    println("Cancelled successfully")
    delay(1000L)
    /*
Output:
0 Printing 0
250 Printing 1
500 Printing 2
750 Printing 3
1000 Printing 4
kotlinx.coroutines.JobCancellationException: Job was cancelled; job=JobImpl{Cancelling}@29444d75
Cancelled successfully
1250 1500 1750 2000
     */
}

/*
Блок finally будет отрабатывать всегда, в том числе и при отмене корутины.
 */
private suspend fun f04finally(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        try {
            val millis = Random.nextLong(10000L)
            println("Delay for $millis ms")
            delay(millis)
            println("Done")
        } finally {
            println("Will always be printed")
        }
    }
    delay(1000L)
    job.cancelAndJoin()
    println("Cancelled successfully")
}

/*
Корутина может выполняться столько сколько нужно для очистки ресурсов.
Однако suspension больше невозможно. Job уже в состоянии "Cancelling", в котором ни suspension,
ни старт новой корутины уже невозможны. Попытка стартовать корутину будет проигнорирована.
Попытка suspend приведет к CancellationException.
 */
private suspend fun f05justOneMoreCall(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        try {
            delay(2000L)
            println("Job is done")
        } finally {
            println("Finally")
            launch {
                println("Will be ignored")
            }
            try {
                delay(1000L)
            } catch (e: CancellationException) {
                println("delay -> CancellationException")
            }
            println("Will not be printed")
        }
    }
    delay(1000L)
    job.cancelAndJoin()
    println("Cancel done")
    /*
Output:
0 250 500 750 1000 Finally
delay -> CancellationException
Will not be printed
Cancel done
     */
}

/*
Иногда нам очень нужно сделать suspend вызов после того, как корутина уже отменена.
Например, откатить изменения в БД. В этом случае нужно использовать оборачивание в
withContext(NonCancellable) - внутри него job in active state, и можно вызывать suspend ф-ии.
 */
private suspend fun f06suspendAfterCancel(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        try {
            delay(200L)
            println("Coroutine finished")
        } finally {
            println("Finally")
            withContext(NonCancellable) {
                delay(1000L)
                println("Cleanup done")
            }
        }
    }
    delay(100L)
    job.cancelAndJoin()
    println("Cancel done")
    /*
Output:
0 Finally
250 500 750 1000 Cleanup done
Cancel done
     */
}

/*
Другой механизм для освобождения ресурсов это ф-я job.invokeOnCompletion.
В обработчике могут быть аргументами:
- null если job завершилась без исключений;
- CancellationException если корутина была отменена;
- исключение завершившее корутину.
 */
private suspend fun f07invokeOnCompletion(): Unit = coroutineScope {
    val job = launch {
        delay(1000L)
    }
    job.invokeOnCompletion { exception: Throwable? ->
        println("Finished with $exception")
    }
    delay(400L)
    job.cancelAndJoin()
    /*
Output:
0 250 Finished with kotlinx.coroutines.JobCancellationException: StandaloneCoroutine was cancelled; job=StandaloneCoroutine{Cancelled}@5f5a92bb
     */
}

/*
Отмена корутины не произойдет в случае отсутствия suspension point.
В примере это имитируется заменой delay на Thread.sleep.
Корутина отработает 4000 ms, хотя должна была остановиться через 500 ms
 */
private suspend fun f08cannotStopWhenNoSuspension(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        repeat(20) { i ->
            Thread.sleep(200L) // Thread используется только для симуляции
            // Тут могут быть сложные и долгие операции без suspension point,
            // например чтение файлов или вычисления.
            println("printing $i")
        }
    }
    println("After launch")
    delay(500L)
    println("Cancelling job...")
    job.cancelAndJoin()
    println("Cancelled successfully")
    delay(500L)
    /*
Output:
0 After launch
printing 0
250 printing 1
500 printing 2
750 printing 3
1000 printing 4
printing 5
1250 printing 6
1500 printing 7
1750 printing 8
2000 printing 9
printing 10
2250 printing 11
2500 printing 12
2750 printing 13
printing 14
3000 printing 15
3250 printing 16
3500 printing 17
3750 printing 18
printing 19
4000 Cancelling job...
Cancelled successfully
4250 4500
     */
}

/*
Одним из решений может быть использование время от времени ф-ии yield,
которая suspends и сражу же resumes корутину. Это дает возможность делать то, что должно
быть сделано в suspension point, включая отмену корутины или смену thread через dispatcher.
Использование yield в промежутках между блоками non-suspended CPU-intensive or time-intensive
операций является хорошей практикой.
 */
private suspend fun f09stopUsingYield(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        repeat(20) { i ->
            Thread.sleep(200L) // Thread используется только для симуляции
            yield() // suspends и сразу resumes
            println("printing $i")
        }
    }
    println("After launch")
    delay(500L)
    println("Cancelling job...")
    job.cancelAndJoin()
    println("Cancelled successfully")
    delay(500L)
    /*
Output:
0 After launch
printing 0
250 printing 1
500 printing 2
750 Cancelling job...
Cancelled successfully
1000 1250
     */
}

/*
Другой способ это проверка состояния job через this.coroutineContext.job.isActive
или ф-ю CoroutineScope.isActive, либо ф-ю ensureActive.
СПОСОБ НЕ РАБОТАЕТ!!!
 */
private suspend fun f10stopUsingStateCheck(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        var count = 0
        do {
            Thread.sleep(200L) // Thread используется только для симуляции
            ensureActive()
            println("printing $count")
            count++
        } while(/*this.coroutineContext.job.isActive &&*/ count < 30)
    }
    println("After launch")
    delay(500L)
    println("Cancelling job...")
    job.cancelAndJoin()
    println("Cancelled successfully")
    delay(500L)
}

/*
Способ с suspendCancellableCoroutine
*/
private suspend fun f11(): Unit = coroutineScope {
    TODO()
}
