package learn.crkotlin.learn.crkotlin.p2c5exceptionhandling

import kotlinx.coroutines.*
import learn.crkotlin.learn.common.logNamed
import learn.crkotlin.learn.common.printMillisInBackground
import kotlin.RuntimeException

fun main() {
    printMillisInBackground()

    // f01errorCancelsParent()
    // f02wrappingIsNotHelpful()
    // f03usingSupervisorJob()
    f04incorrectUsingSupervisorJob()
}

/*
В случае исключения coroutine builders отменяют своих родителей, и каждый отмененный родитель,
в свою очередь отменяет всех children. Исключение поднимается к корневой корутине runBlocking
и это останавливает программу.
Если распространение исключения не останавливать, то отменяется вся иерархия полностью.
 */
private fun f01errorCancelsParent() = runBlocking {
    launch {
        launch {
            delay(1000L)
            throw RuntimeException("Some Exception")
        }
        launch {
            delay(2000L)
            println("Will not be printed")
        }
        launch {
            delay(500L)
            println("Faster - Will be printed after 500")
        }
    }
    launch {
        delay(2000L)
        println("Will not be printed")
    }
    launch {
        delay(200L)
        println("Faster - Will be printed after 200")
    }
    /*
Output:
0 Faster - Will be printed after 200
250 500 Faster - Will be printed after 500
750 1000 Exception in thread "main" java.lang.RuntimeException: Some Exception
     */
}

/*
Отлавливание exception до того как оно поломает корутину помогает, но если позднее, то это уже бесполезно.
Коммуникация происходит через job, поэтому оборачивание coroutine builder-а блоком try-catch не поможет.
 */
private fun f02wrappingIsNotHelpful() = runBlocking(CoroutineName("Outer")) {
    logNamed("Starting")
    try {
        launch(CoroutineName("Throwing-Exception")) {
            delay(1000L)
            val name = coroutineContext[CoroutineName]
            throw RuntimeException("Some error in coroutine $name")
        }
    } catch (e: Throwable) {
        println("Will not be printed")
    }
    launch(CoroutineName("cr-2")) {
        logNamed("starting delay 2000ms")
        delay(2000L)
        println("Will not be printed")
    }
}

/*
Самый важный способ предотвратить поломку корутины это использование SupervisorJob.
Это специальный job, который игнорирует все исключения в children.
Обычно он используется как часть scope, внутри которого мы стартуем множество корутин.
 */
private fun f03usingSupervisorJob() = runBlocking(CoroutineName("Outer")) {
    logNamed("Starting")
    val scope = CoroutineScope(SupervisorJob())
    scope.launch(CoroutineName("Throwing-Exception")) {
        delay(1000L)
        val name = coroutineContext[CoroutineName]
        throw RuntimeException("Some error in coroutine $name")
    }
    launch(CoroutineName("cr-2")) {
        logNamed("starting delay 2000ms")
        delay(2000L)
        println("Will be printed")
    }
    /*
Output:
0 [main] [Outer] Starting
[main] [cr-2] starting delay 2000ms
250 500 750 1000 Exception in thread "DefaultDispatcher-worker-2" java.lang.RuntimeException: Some error in coroutine CoroutineName(Throwing-Exception)
	at learn.crkotlin.learn.crkotlin.p2c5exceptionhandling.E01exceptionsKt$f03usingSupervisorJob$1$1.invokeSuspend(e01exceptions.kt:81)
	...
1250 1500 1750 2000 Will be printed
     */
}

/*
Общей ошибкой является использование SupervisorJob в качестве аргумента родительской корутины.
Это не поможет в обработке исключений, так как в этом случае SupervisorJob имеет только одного прямого
потомка, а именно тот launch, аргументом которого он является. Так что у такого использования нет преимуществ
по сравнению с просто Job (в обоих случаях исключение не поднимется в runBlocking потому что его job не используется.
 */
private fun f04incorrectUsingSupervisorJob() = runBlocking(CoroutineName("Outer")) {
    logNamed("Starting")
    // Don't do that, SupervisorJob with one child
    // and no parent works similar to just Job
    launch(SupervisorJob()) {
        launch(CoroutineName("Throwing-Exception")) {
            delay(1000L)
            val name = coroutineContext[CoroutineName]
            throw RuntimeException("Some error in coroutine $name")
        }
        launch(CoroutineName("cr-2")) {
            logNamed("starting delay 2000ms")
            delay(2000L)
            println("Will not be printed")
        }
    }
    delay(3000L)
    println("end of outer")
    /*
Output:
0 [main] [Outer] Starting
[main] [cr-2] starting delay 2000ms
250 500 750 1000 Exception in thread "main" java.lang.RuntimeException: Some error in coroutine CoroutineName(Throwing-Exception)
	at learn.crkotlin.learn.crkotlin.p2c5exceptionhandling.E01exceptionsKt$f04incorrectUsingSupervisorJob$1$1$1.invokeSuspend(e01exceptions.kt:119)
	...
1250 1500 1750 2000 2250 2500 2750 end of outer
     */
}