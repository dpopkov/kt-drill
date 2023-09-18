package learn.algo.dsalgokt.ch03linkedlist.c04merging

import learn.algo.dsalgokt.common.buildNodeLinkedList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

abstract class NodeListMergerBaseTest(
    private val merger: INodeListMerger
) {
    @Test
    fun merge() {
        val n1 = buildNodeLinkedList(Node::class, 1, 4, 10, 11)
        val n2 = buildNodeLinkedList(Node::class, -1, 2, 3, 6)

        val result = merger.mergeSorted(n1, n2)

        assertEquals("-1 -> 1 -> 2 -> 3 -> 4 -> 6 -> 10 -> 11", result.toString())
    }

    @Test
    fun mergeShortAndLong() {
        val n1 = buildNodeLinkedList(Node::class, 1)
        val n2 = buildNodeLinkedList(Node::class, -1, 2, 3, 6)

        val result = merger.mergeSorted(n1, n2)

        assertEquals("-1 -> 1 -> 2 -> 3 -> 6", result.toString())
    }

    @Test
    fun mergeLongAndShort() {
        val n1 = buildNodeLinkedList(Node::class, 1, 4, 10, 11)
        val n2 = buildNodeLinkedList(Node::class, -1)

        val result = merger.mergeSorted(n1, n2)

        assertEquals("-1 -> 1 -> 4 -> 10 -> 11", result.toString())
    }
}
