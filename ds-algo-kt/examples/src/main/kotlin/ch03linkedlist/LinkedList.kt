package learn.algo.dsalgokt.ch03linkedlist

class LinkedList<T> : ILinkedList<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    override fun isEmpty(): Boolean = size == 0

    override fun push(value: T): LinkedList<T> {
        head = Node(value = value, next = head)
        if (tail == null) {
            tail = head
        }
        size++
        return this
    }

    override fun append(value: T): LinkedList<T> {
        if (isEmpty()) {
            push(value)
            return this
        }
        val node = Node(value)
        tail?.next = node
        tail = node
        size++
        return this
    }

    override fun insert(value: T, afterIndex: Int) {
        val after = nodeAt(afterIndex)
        if (after != null) {
            insert(value, after)
        }
    }

    override fun insert(value: T, after: Node<T>): Node<T> {
        if (after === tail) {
            append(value)
            return tail!!
        }
        val inserted = Node(value, after.next)
        after.next = inserted
        size++
        return inserted
    }

    fun nodeAt(index: Int): Node<T>? {
        var currentNode = head
        var currentIndex = 0
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    override fun toString(): String =
        if (isEmpty()) {
            "Empty list"
        } else {
            head.toString()
        }
}
