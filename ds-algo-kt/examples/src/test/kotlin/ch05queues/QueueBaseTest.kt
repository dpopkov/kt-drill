package learn.algo.dsalgokt.ch05queues

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

abstract class QueueBaseTest(
    private val queue: IQueue<Int>
) {
    @Test
    fun testEnqueueDequeue() {
        assertTrue(queue.isEmpty)
        queue.enqueue(1)
        queue.enqueue(2)
        assertFalse(queue.isEmpty)

        assertEquals(1, queue.peek())
        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.peek())
        assertEquals(2, queue.dequeue())
        assertTrue(queue.isEmpty)
    }

    @Test
    fun testDequeueThenEnqueue() {
        assertTrue(queue.isEmpty)
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.enqueue(4)
        assertFalse(queue.isEmpty)

        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.dequeue())

        queue.enqueue(5)
        queue.enqueue(6)
        assertEquals(3, queue.dequeue())
        assertEquals(4, queue.dequeue())
        assertEquals(5, queue.dequeue())
        assertEquals(6, queue.dequeue())
        assertTrue(queue.isEmpty)
    }

}
