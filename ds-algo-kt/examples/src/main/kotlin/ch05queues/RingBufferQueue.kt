package learn.algo.dsalgokt.ch05queues

class RingBufferQueue<T>(capacity: Int) : IQueue<T> {
    private val buffer = RingBuffer<T>(capacity)

    override val size: Int get() = buffer.size

    override val isEmpty: Boolean
        get() = buffer.isEmpty()

    override fun dequeue(): T {
        return buffer.read()
    }

    override fun peek(): T {
        return buffer.peekFirst()
    }

    override fun enqueue(element: T): Boolean {
        if (buffer.isFull()) {
            return false
        } else {
            buffer.write(element)
            return true
        }
    }

    private class RingBuffer<T>(
        private val capacity: Int
    ) {
        private val storage: ArrayList<T?> = ArrayList(capacity)
        private var readIdx = 0
        private var writeIdx = 0
        private var hitCapacity = false

        var size: Int = 0
            private set

        fun write(value: T) {
            if (hitCapacity) {
                storage[writeIdx] = value
            } else {
                storage.add(value)
                if (storage.size == capacity) {
                    hitCapacity = true
                }
            }
            writeIdx = (writeIdx + 1) % capacity
            size++
        }

        fun read(): T {
            val value = getFirstValue()
            storage[readIdx] = null
            readIdx = (readIdx + 1) % capacity
            size--
            return value
        }

        fun peekFirst(): T = getFirstValue()

        fun isFull() = size == capacity

        fun isEmpty() = size == 0

        private fun getFirstValue() = storage[readIdx] ?: throw IllegalStateException("RingBuffer is empty")
    }
}
