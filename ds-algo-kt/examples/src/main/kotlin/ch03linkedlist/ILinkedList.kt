package learn.algo.dsalgokt.ch03linkedlist

interface ILinkedList<T> : Iterable<T> {

    val size: Int

    fun isEmpty(): Boolean

    /**
     * Добавляет элемент в начало списка.
     */
    fun push(value: T): ILinkedList<T>

    /**
     * Добавляет элемент в конец списка.
     */
    fun append(value: T): ILinkedList<T>

    /**
     * Вставляет элемент после индекса [afterIndex].
     */
    fun insert(value: T, afterIndex: Int)

    /**
     * Вставляет элемент после узла [after] и возвращает вставленный узел.
     */
    fun insert(value: T, after: Node<T>): Node<T>

    /**
     * Удаляет и возвращает значение из головы списка, или null если список пуст.
     */
    fun pop(): T?

    /**
     * Удаляет и возвращает значение из конца списка, или null если список пуст.
     */
    fun removeLast(): T?

    /**
     * Удаляет и возвращает значение сразу после указанного индекса [afterIndex].
     */
    fun removeAfter(afterIndex: Int): T?
}
