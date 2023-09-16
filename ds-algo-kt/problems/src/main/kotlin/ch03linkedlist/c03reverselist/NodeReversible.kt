package learn.algo.dsalgokt.ch03linkedlist.c03reverselist

class NodeReversible(
    value: Int,
    next: Node? = null,
) : Node(value, next), INodeListReversible {
    override fun reverse(): Node {
        val newHead = reverseRecursive(this, this.next)
        this.next = null
        return newHead
    }

    private fun reverseRecursive(p: Node, n: Node?): Node = if (n == null) p
    else {
        val reversed = reverseRecursive(n, n.next)
        n.next = p
        reversed
    }
}
