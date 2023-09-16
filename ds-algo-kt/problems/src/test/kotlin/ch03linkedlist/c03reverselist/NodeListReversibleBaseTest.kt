package learn.algo.dsalgokt.ch03linkedlist.c03reverselist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

abstract class NodeListReversibleBaseTest(
    private val node: INodeListReversible
) {
    @Test
    fun testReverse() {
        assertEquals("1 -> 2 -> 3", node.toString())

        val r = node.reverse()
        assertEquals("3 -> 2 -> 1", r.toString())
    }
}
