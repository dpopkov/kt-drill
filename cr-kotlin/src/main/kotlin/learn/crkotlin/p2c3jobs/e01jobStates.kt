package learn.crkotlin.learn.crkotlin.p2c3jobs

import kotlinx.coroutines.*

fun main(): Unit = runBlocking {
    val job = Job()
    println(job)    // Active
    job.complete()
    println(job)    // Completed

    // launch is initially active by default
    val activeJob = launch {
        delay(1000L)
        println(coroutineContext.job.isActive)  // true
    }
    println(activeJob)  // Active
    activeJob.join()
    println(activeJob)  // Completed

    // launch started lazily is in New state
    val lazyJob = launch(start = CoroutineStart.LAZY) {
        delay(1000L)
    }
    println(lazyJob)    // New
    lazyJob.start()
    println(lazyJob)    // Active
    lazyJob.join()
    println(lazyJob)    // Completed
    println(lazyJob.isActive)       // false
    println(lazyJob.isCompleted)    // true
    println(lazyJob.isCancelled)    // false
}
