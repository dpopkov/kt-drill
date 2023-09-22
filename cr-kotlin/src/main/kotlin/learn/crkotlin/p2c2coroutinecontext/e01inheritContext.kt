package learn.crkotlin.learn.crkotlin.p2c2coroutinecontext

import kotlinx.coroutines.*
import learn.crkotlin.learn.common.logNamed
import learn.crkotlin.learn.common.printMillisInBackground
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun main()  {
    printMillisInBackground()

    // f01findingElementsInCoroutineContext()
    // f02addingContextsWithDifferentKeys()
    // f03addingContextsWithSameKeys()
    // f04emptyCoroutineContext()
    // f05subtractingElements()
    // f06foldingContext()
    // f07contextAndBuilders()
    f08childrenWithSpecificContext()
}

/*
Поскольку CoroutineContext похож на коллекцию, мы можем найти элемент по ключу используя
вызов get. Другой вариант, это использовать квадратные скобки, что одно и то же, так как
get это операция.
Как и в Map, если элемент присутствует, то будет возвращен, в противном случае возвращается null.
Чтобы найти CoroutineName, мы используем CoroutineName являющийся в данном случае companion object-ом.
В Kotlin имя класса служит ссылкой на companion object,
поэтому ctx[CoroutineName] это лишь shortcut к ctx[CoroutineName.Key].
Общей практикой в пакете kotlinx.coroutines является использовать companion object как ключ
к элементу с таким же именем.
 */
private fun f01findingElementsInCoroutineContext() {
    val ctx: CoroutineContext = CoroutineName("cr-1")
    val coroutineName: CoroutineName? = ctx[CoroutineName]
    println(coroutineName?.name)
    val job: Job? = ctx[Job]
    println(job)
    /*
Output:
cr-1
null
     */
}

/*
Что делает CoroutineContext полезным, так это возможность складывать два контекста вместе.
Когда складываются контексты с разными ключами, то результирующий контекст содержит оба ключа
 */
private fun f02addingContextsWithDifferentKeys() {
    val ctx1: CoroutineContext = CoroutineName("cr-1")
    println(ctx1[CoroutineName]?.name)  // cr-1
    println(ctx1[Job]?.isActive)        // null

    val ctx2: CoroutineContext = Job()
    println(ctx2[CoroutineName]?.name)  // null
    println(ctx2[Job]?.isActive)        // true

    val ctx3 = ctx1 + ctx2
    println(ctx3[CoroutineName]?.name)  // cr-1
    println(ctx3[Job]?.isActive)        // true
}

/*
Еси в добавляемом контексте содержит такой же ключ, то, как и в Map,
новый элемент заменяет собой предыдущий.
 */
private fun f03addingContextsWithSameKeys() {
    val ctx1: CoroutineContext = CoroutineName("cr-1")
    println(ctx1[CoroutineName]?.name)  // cr-1

    val ctx2: CoroutineContext = CoroutineName("cr-2")
    println(ctx2[CoroutineName]?.name)  // cr-2

    val ctx3 = ctx1 + ctx2
    println(ctx3[CoroutineName]?.name)  // cr-2
}

/*
Поскольку CoroutineContext подобен коллекции, то имеется также пустой контекст.
Сам по себе он не возвращает никаких элементов.
Если добавить его к другому контексту, то результат будет вести себя как этот другой
исходный контекст. И от перемены слагаемых местами сумма не меняется.
 */
private fun f04emptyCoroutineContext() {
    val empty: CoroutineContext = EmptyCoroutineContext
    println(empty[CoroutineName])   // null
    println(empty[Job])             // null

    val ctxName: CoroutineContext = CoroutineName("cr-1") + empty
    println(ctxName[CoroutineName])         // CoroutineName(cr-1)
    println(ctxName[CoroutineName]?.name)   // cr-1
}

/*
Элементы могут быть удалены из контекста по ключу функцией minusKey.
 */
private fun f05subtractingElements() {
    val ctx: CoroutineContext = CoroutineName("cr-1") + Job()
    println(ctx[CoroutineName]?.name)   // cr-1
    println(ctx[Job]?.isActive)         // true

    val ctx2 = ctx.minusKey(CoroutineName)
    println(ctx2[CoroutineName]?.name)  // null
    println(ctx2[Job]?.isActive)        // true

    val ctx3 = (ctx + CoroutineName("cr-2")).minusKey(CoroutineName)
    println(ctx3[CoroutineName]?.name)  // null
    println(ctx3[Job]?.isActive)        // true
}

/*
Если нужно сделать что-то с каждым элементом контекста, можно использовать метод fold,
похожий на метод fold для других коллекций.
 */
private fun f06foldingContext() {
    val ctx: CoroutineContext = CoroutineName("cr-1") + Job()

    ctx.fold("") { acc, element ->
        "$acc$element "
    }.also {
        println(it)
    } // CoroutineName(cr-1) JobImpl{Active}@4cc77c2e

    val empty = mutableListOf<CoroutineContext>()
    ctx.fold(empty) { acc, element ->
        acc.add(element)
        acc
    }
        .joinToString()
        .also {
            println(it)
        } // CoroutineName(cr-1), JobImpl{Active}@4cc77c2e
}

/*
CoroutineContext is just a way to hold and pass data.
By default, the parent passes its context to the child,
which is one of the parent-child relationship effects.
We say that the child inherits context from its parent.
 */
private fun f07contextAndBuilders() = runBlocking(CoroutineName("cr-1")) {
    logNamed("Started")
    val v1 = async {
        delay(1000)
        logNamed("Running async")
        42
    }
    launch {
        delay(2000)
        logNamed("Running launch")
    }
    logNamed("The answer is ${v1.await()}")
    /*
Output:
0 [main] [cr-1] Started
250 500 750 [main] [cr-1] Running async
1000 [main] [cr-1] The answer is 42
1250 1500 1750 [main] [cr-1] Running launch
     */
}

/*
Каждый child может иметь контекст указанный в аргументах.
Этот контекст переопределяет родительский.
 */
private fun f08childrenWithSpecificContext() = runBlocking(CoroutineName("cr-0")) {
    logNamed("Started")
    val v1 = async(CoroutineName("cr-1")) {
        delay(1000)
        logNamed("Running async")
        42
    }
    launch(CoroutineName("cr-2")) {
        delay(2000)
        logNamed("Running launch")
    }
    logNamed("The answer is ${v1.await()}")
    /*
Output:
0 [main] [cr-0] Started
250 500 750 1000 [main] [cr-1] Running async
[main] [cr-0] The answer is 42
1250 1500 1750 [main] [cr-2] Running launch
2000
     */
}

private fun f09accessingContext() = runBlocking {
    withContext(CoroutineName("cr")) {

    }
}