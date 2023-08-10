package learn.algo.dsalgokt.examples.ch03linkedlist

import learn.algo.dsalgokt.ch03linkedlist.Node
import learn.algo.dsalgokt.common.runUsingSystemOut
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class NodeTest {
    @Test
    fun `creating and linking 3 nodes`() {
        val n1 = Node(value = 1)
        val n2 = Node(value = 2)
        val n3 = Node(value = 3)
        n1.next = n2
        n2.next = n3

        runUsingSystemOut {
            print(n1)
        }.also {
            assertEquals("1 -> 2 -> 3", it)
        }
    }
}
