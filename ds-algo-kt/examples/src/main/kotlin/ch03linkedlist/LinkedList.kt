package learn.algo.dsalgokt.ch03linkedlist

class LinkedList<T> : ILinkedList<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    override var size = 0
        private set

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
        require(index in 0..<size) { "Illegal index $index out of bounds" }
        var currentNode = head
        var currentIndex = 0
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    override fun pop(): T? {
        if (isEmpty()) return null
        val result = head?.value
        head = head?.next
        size--
        if (isEmpty()) tail = null
        return result
    }

    override fun removeLast(): T? {
        if (isEmpty()) return null
        if (size == 1) {
            return pop()
        }
        val result = tail?.value
        var n = head
        while (n?.next !== tail) {
            n = n?.next
        }
        n?.next = null
        tail = n
        size--
        return result
    }

    override fun removeAfter(afterIndex: Int): T? {
        val node = nodeAt(afterIndex)
        return node?.let { removeAfter(it) }
    }

    private fun removeAfter(node: Node<T>): T? {
        if (node.next === tail) {
            return removeLast()
        }
        if (node.next != null) size--
        val result = node.next?.value
        node.next = node.next?.next
        return result
    }

    private inner class InnerIterator : Iterator<T> {
        private var nextNode: Node<T>? = this@LinkedList.head

        override fun hasNext(): Boolean = nextNode != null

        override fun next(): T {
            if (nextNode == null) throw NoSuchElementException()
            val result: T = nextNode!!.value
            nextNode = nextNode?.next
            return result
        }
    }

    override fun iterator(): Iterator<T> = InnerIterator()

    override fun toString(): String =
        if (isEmpty()) {
            "Empty list"
        } else {
            head.toString()
        }
}
