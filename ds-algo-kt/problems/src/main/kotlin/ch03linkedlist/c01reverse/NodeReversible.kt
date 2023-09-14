package ch03linkedlist.c01reverse

import learn.algo.dsalgokt.ch03linkedlist.c01reverse.IReversible

class NodeReversible(
    value: Int,
    next: Node? = null
) : Node(value, next), IReversible {
    override fun toStringReversed(): String = buildString {
        fun appendRecursive(node: Node) {
            node.next?.let {
                appendRecursive(it)
                append(" -> ")
            }
            append(node.value)
        }
        appendRecursive(this@NodeReversible)
    }
}
