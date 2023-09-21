package learn.crkotlin.learn.crkotlin.p2c1coroutinebuilders

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import learn.crkotlin.learn.common.log
import learn.crkotlin.learn.common.logNamed
import learn.crkotlin.learn.common.printMillisInBackground
import kotlin.concurrent.thread

fun main() {
    printMillisInBackground()

    // f01launchBuilder()
    // f02launchIsSimilarToDaemonThreads()
    // f03runBlocking()
    f04runBlockingStructured()
}

/*
The way launch works is conceptually similar to starting a new thread using thread function.
We just start a coroutine, and it will run independently.
 */
private fun f01launchBuilder() {
    GlobalScope.launch {
        delay(1000L)
        logNamed("World!")
    }
    GlobalScope.launch {
        delay(1000L)
        logNamed("World!")
    }
    GlobalScope.launch {
        delay(1000L)
        logNamed("World!")
    }
    log("Hello,")
    /*
    At the end of the function we need to call Thread.sleep in order to not allow
    this function end immediately after launching the coroutines.
     */
    Thread.sleep(2000L)
    /*
Output:
[main] Hello,
0 250 500 750 [DefaultDispatcher-worker-3] [null] World!
[DefaultDispatcher-worker-1] [null] World!
[DefaultDispatcher-worker-2] [null] World!
1000 1250 1500 1750
    */
}

/*
How launch works is similar to a daemon thread but much cheaper.
 */
private fun f02launchIsSimilarToDaemonThreads() {
    thread(isDaemon = true) {
        Thread.sleep(1000L)
        log("World!")
    }
    thread(isDaemon = true) {
        Thread.sleep(1000L)
        log("World!")
    }
    thread(isDaemon = true) {
        Thread.sleep(1000L)
        log("World!")
    }
    log("Hello,")
    Thread.sleep(2000L)
    /*
Output:
0 [main] Hello,
250 500 750 [Thread-2] World!
[Thread-3] World!
[Thread-4] World!
1000 1250 1500 1750
     */
}

/*
runBlocking runs a new coroutine and blocks the current thread interruptibly
until its completion.
 */
private fun f03runBlocking() {
    runBlocking {
        delay(1000L)
        logNamed("World!")
    }
    runBlocking {
        delay(1000L)
        logNamed("World!")
    }
    runBlocking {
        delay(1000L)
        logNamed("World!")
    }
    log("Hello,")
    /*
Output:
0 250 500 750 [main] [null] World!
1000 1250 1500 1750 2000 [main] [null] World!
2250 2500 2750 3000 [main] [null] World!
[main] Hello,
     */
}

private fun f04runBlockingStructured() = runBlocking {
    launch {
        delay(1000L)
        logNamed("World!")
    }
    launch {
        delay(1000L)
        logNamed("World!")
    }
    launch {
        delay(1000L)
        logNamed("World!")
    }
    log("Hello,")
    /*
Output:
0 [main] Hello,
250 500 750 1000 [main] [null] World!
[main] [null] World!
[main] [null] World!
     */
}