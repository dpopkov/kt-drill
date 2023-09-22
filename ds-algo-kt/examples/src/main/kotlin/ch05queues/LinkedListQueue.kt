package learn.algo.dsalgokt.ch05queues

class LinkedListQueue<T> : IQueue<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    override var size: Int = 0
        private set

    override fun dequeue(): T {
        val value = firstNodeValue()
        head = head?.next
        head?.prev = null
        size--
        if (isEmpty) {
            tail = null
        }
        return value
    }

    override fun peek(): T = firstNodeValue()

    private fun firstNodeValue(): T = head?.value ?: throw IllegalStateException("The queue is empty")

    override fun enqueue(element: T): Boolean {
        val node = Node(element, prev = tail)
        if (isEmpty) {
            head = node
        } else {
            tail!!.next = node
        }
        tail = node
        size++
        return true
    }

    private class Node<E>(
        val value: E,
        var prev: Node<E>? = null,
        var next: Node<E>? = null,
    )
}