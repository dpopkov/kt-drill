package learn.algo.dsalgokt.ch03linkedlist.c03reverselist

class NodeListReversibleTest: NodeListReversibleBaseTest(
    node = NodeReversible(1).apply {
        val n3 = NodeReversible(3)
        val n2 = NodeReversible(2, n3)
        this.next = n2
    }
)
