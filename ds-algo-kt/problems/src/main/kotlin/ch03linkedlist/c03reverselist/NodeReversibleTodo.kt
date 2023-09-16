package learn.algo.dsalgokt.ch03linkedlist.c03reverselist

class NodeReversibleTodo(
    value: Int,
    next: Node? = null,
) : Node(value, next), INodeListReversible {
    override fun reverse(): Node {
        TODO()
    }
}
