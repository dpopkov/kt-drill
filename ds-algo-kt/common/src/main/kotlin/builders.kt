package learn.algo.dsalgokt.common

import kotlin.reflect.KClass

/**
 * Строит связный список из узлов указанного класса [nodeClass] используя переданные [values].
 * Класс узла должен конструктор принимающей 2 параметра: value и next node.
 */
fun <T : Any, V : Any> buildNodeLinkedList(nodeClass: KClass<T>, vararg values: V): T {
    if (values.isEmpty()) {
        throw IllegalArgumentException("Cannot build linked list from empty numbers")
    }
    val constructor = nodeClass.constructors.first()
    var last: T? = null
    for (i in (values.lastIndex downTo 0)) {
        val node = constructor.call(values[i], last)
        last = node
    }
    return last ?: throw IllegalStateException("Cannot build any nodes from numbers")
}
