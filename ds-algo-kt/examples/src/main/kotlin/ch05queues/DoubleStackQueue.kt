package learn.algo.dsalgokt.ch05queues

class DoubleStackQueue<T> : IQueue<T> {
    private val input = InnerStack<T>()
    private val output = InnerStack<T>()

    override val size: Int
        get() = input.size + output.size

    override val isEmpty: Boolean
        get() = input.isEmpty && output.isEmpty

    override fun dequeue(): T {
        if (output.isEmpty) {
            transfer()
        }
        return output.pop()
    }

    private fun transfer() {
        while (input.isNotEmpty) {
            output.push(input.pop())
        }
    }

    override fun peek(): T {
        return if (output.isNotEmpty) {
            output.peekLast()
        } else {
            input.peekFirst()
        }
    }

    override fun enqueue(element: T): Boolean {
        input.push(element)
        return true
    }

    private class InnerStack<E> {
        private val storage = mutableListOf<E>()

        val isEmpty: Boolean get() = storage.isEmpty()
        val isNotEmpty: Boolean get() = storage.isNotEmpty()
        val size: Int get() = storage.size

        fun push(e: E) {
            storage.add(e)
        }

        fun pop(): E = storage.removeLast()

        fun peekLast() = storage.last()

        fun peekFirst() = storage.first()
    }
}
