package learn.algo.dsalgokt.ch03linkedlist.c01reverse

class NodeReversibleToDoTest : NodeReversibleBaseTest(
    NodeReversibleTodo(1).apply {
        val n4 = NodeReversibleTodo(4)
        val n3 = NodeReversibleTodo(3, n4)
        val n2 = NodeReversibleTodo(2, n3)
        this.next = n2
    }
)
