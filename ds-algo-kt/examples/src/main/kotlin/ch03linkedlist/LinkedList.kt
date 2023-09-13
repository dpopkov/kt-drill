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

    private inner class InnerIterator : MutableIterator<T> {
        private var nextNode: Node<T>? = this@LinkedList.head
        private var returnedNode: Node<T>? = null
        private var preReturnedNode: Node<T>? = null

        override fun hasNext(): Boolean = nextNode != null

        override fun next(): T {
            if (nextNode == null) throw NoSuchElementException()
            val result: T = nextNode!!.value
            if (returnedNode != null) {
                preReturnedNode = returnedNode
            }
            returnedNode = nextNode
            nextNode = nextNode?.next
            return result
        }

        override fun remove() {
            if (isEmpty()) throw IllegalStateException("List is empty")
            if (returnedNode == null) throw IllegalStateException("next() was not called")
            if (returnedNode === head) { // удаление 1-го элемента
                pop()
            }
            else if (returnedNode === tail) { // удаление последнего
                removeLast()
            }
            else {  // удаление промежуточного
                removeAfter(preReturnedNode!!)
            }
            returnedNode = null
        }
    }

    override fun iterator(): MutableIterator<T> = InnerIterator()

    override fun contains(element: T): Boolean {
        for (item in this) {
            if (item == element) return true
        }
        return false
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            if (!contains(element)) return false
        }
        return true
    }

    override fun toString(): String =
        if (isEmpty()) {
            "Empty list"
        } else {
            head.toString()
        }

    override fun clear() {
        head = null
        tail = null
        size = 0
    }

    override fun retainAll(elements: Collection<T>): Boolean = removeAllIf { it !in elements }

    override fun removeAll(elements: Collection<T>): Boolean = removeAllIf { it in elements }

    private inline fun removeAllIf(predicate: (T) -> Boolean): Boolean {
        val i = iterator()
        var modified = false
        while (i.hasNext()) {
            if (predicate(i.next())) {
                i.remove()
                modified = true
            }
        }
        return modified
    }

    override fun remove(element: T): Boolean {
        val i = iterator()
        while (i.hasNext()) {
            if (i.next() == element) {
                i.remove()
                return true
            }
        }
        return false
    }

    override fun addAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            append(element)
        }
        return true
    }

    override fun add(element: T): Boolean {
        append(element)
        return true
    }
}
