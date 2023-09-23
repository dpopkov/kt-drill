package learn.crkotlin.learn.crkotlin.p2c3jobs

import kotlinx.coroutines.*

fun main() {
    // f01jobIsNotInherited()
    // f02referencingChildren()
    // f03newJobBreaksStructuredConcurrency()
    runBlocking {
        println("in runBlocking")
        val job: Job = coroutineContext.job
        println("job = ${job}")
    }
}

/*
Job это единственный контекст, который не наследуется корутиной от корутины.
Каждая корутина создает собственный Job, и если в параметрах передается Job,
то он становится родительским по отношению к новому Job.
 */
private fun f01jobIsNotInherited(): Unit = runBlocking {
    val name = CoroutineName("cr")
    val job = Job()

    launch(name + job) {
        val childName = coroutineContext[CoroutineName]
        println(childName == name)  // true
        val childJob = coroutineContext[Job]
        println(childJob == job)    // false
        println(childJob == job.children.first())   // true
    }
}

/*
parent имеет доступ ко всем своим children, а children могут иметь доступ к parent.
Это отношение parent-child делает возможным cancellation и обработку ошибок внутри
coroutine scope.
 */
private fun f02referencingChildren(): Unit = runBlocking {
    val job: Job = launch {
        delay(1000L)
    }
    val parentJob = coroutineContext.job
    println(job == parentJob)   // false
    val parentChildren: Sequence<Job> = parentJob.children
    println(parentChildren.first() == job)  // true
}

/*
Механизм structured concurrency не будет работать, если новый Job контекст заменяет собой
родительский контекст.
Родитель не будет ждать children, потому что не имеет с ними связи через Job,
поскольку child использует Job из параметра как родительский, таким образом он не имеет
отношения к runBlocking.
 */
private fun f03newJobBreaksStructuredConcurrency(): Unit = runBlocking {
    println("inside runBlocking block")
    launch(CoroutineName("cr-1") + Job()) {
        delay(2000L)
        println("This message will not be printed")
    }
/*
Output:
(prints nothing, finishes immediately)
 */
}
