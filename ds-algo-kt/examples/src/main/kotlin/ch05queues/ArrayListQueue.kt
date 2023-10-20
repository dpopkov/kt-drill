package learn.algo.dsalgokt.ch05queues

class ArrayListQueue<T> : IQueue<T> {
    private val storage = arrayListOf<T>()

    override val size: Int
        get() = storage.size

    override fun dequeue(): T = storage.removeFirst()

    override fun peek(): T = storage.first()

    override fun enqueue(element: T): Boolean {
        storage.add(element)
        return true
    }

    fun enqueueAll(elements: Iterable<T>): ArrayListQueue<T> {
        for (element in elements) {
            enqueue(element)
        }
        return this
    }

}
