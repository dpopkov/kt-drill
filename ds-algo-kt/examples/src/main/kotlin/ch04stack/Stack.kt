package learn.algo.dsalgokt.ch04stack

class Stack<E> : IStack<E> {
    private val storage = arrayListOf<E>()

    override val size: Int
        get() = storage.size

    override fun isEmpty(): Boolean = storage.isEmpty()

    override fun push(element: E) {
        storage.add(element)
    }

    override fun pop(): E = storage.removeLast()

    override fun peek(): E? = storage.lastOrNull()

    override fun toString(): String {
        return buildString {
            appendLine("-top-")
            for (i in (storage.lastIndex downTo 0)) {
                appendLine(storage[i])
            }
            append("-----")
        }
    }

    companion object {
        fun <E> create(items: Iterable<E>) = Stack<E>()
            .apply {
                for (item in items) {
                    push(item)
                }
            }
    }
}
