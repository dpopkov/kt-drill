package learn.algo.dsalgokt.ch03linkedlist

interface ILinkedList<T> {

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
     * Добавляет элемент после индекса [afterIndex].
     */
    fun insert(value: T, afterIndex: Int)

    /**
     * Добавляет элемент после угла [after]
     */
    fun insert(value: T, after: Node<T>): Node<T>
}
