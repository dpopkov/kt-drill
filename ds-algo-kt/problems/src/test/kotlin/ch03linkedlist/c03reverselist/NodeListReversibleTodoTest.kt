package learn.algo.dsalgokt.ch03linkedlist.c03reverselist

class NodeListReversibleTodoTest: NodeListReversibleBaseTest(
    node = NodeReversibleTodo(1).apply {
        val n3 = NodeReversibleTodo(3)
        val n2 = NodeReversibleTodo(2, n3)
        this.next = n2
    }
)
