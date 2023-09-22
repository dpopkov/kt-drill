package learn.algo.dsalgokt.ch05queues

interface IQueue<T> {
    val size: Int

    val isEmpty: Boolean
        get() = size == 0

    /**
     * Вставляет элемент в хвост очереди и возвращает true если операция успешна.
     */
    fun enqueue(element: T): Boolean

    /**
     * Удаляет элемент из головы очереди и возвращает его.
     */
    fun dequeue(): T

    /**
     * Возвращает элемент из головы очереди не удаляя его из очереди.
     */
    fun peek(): T
}
