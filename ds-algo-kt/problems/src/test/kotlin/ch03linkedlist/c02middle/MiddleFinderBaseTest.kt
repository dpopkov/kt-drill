package learn.algo.dsalgokt.ch03linkedlist.c02middle

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

sealed class MiddleFinderBaseTest(
    val finder: IMiddleFinder
) {
    @Test
    fun findMiddle() {
        val n3 = Node(3)
        val n2 = Node(2, n3)
        val n1 = Node(1, n2)

        val middle = finder.findMiddle(n1)
        assertEquals(2, middle.value)

        n3.next = Node(4)
        val middle2 = finder.findMiddle(n1)
        assertEquals(3, middle2.value)
    }
}