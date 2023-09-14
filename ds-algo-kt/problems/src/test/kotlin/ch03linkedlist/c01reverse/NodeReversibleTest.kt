package learn.algo.dsalgokt.ch03linkedlist.c01reverse

import ch03linkedlist.c01reverse.NodeReversible

class NodeReversibleTest : NodeReversibleBaseTest(
    NodeReversible(1).apply {
        val n4 = NodeReversible(4)
        val n3 = NodeReversible(3, n4)
        val n2 = NodeReversible(2, n3)
        this.next = n2
    }
)
