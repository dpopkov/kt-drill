package learn.algo.dsalgokt.ch03linkedlist.c01reverse

import ch03linkedlist.c01reverse.Node

class NodeReversibleTodo (
    value: Int,
    next: Node? = null
) : Node(value, next), IReversible {
    override fun toStringReversed(): String {
        TODO("Not yet implemented")
    }
}
