package learn.algo.dsalgokt.ch03linkedlist.c04merging

object NodeListMerger : INodeListMerger {
    override fun mergeSorted(head1: Node, head2: Node): Node {
        var n1: Node? = head1
        var n2: Node? = head2
        val newHead: Node = if (head1.value < head2.value) {
            n1 = head1.next
            head1
        } else {
            n2 = head2.next
            head2
        }
        var current: Node = newHead
        while (n1 != null && n2 != null) {
            if (n1.value < n2.value) {
                n1 = current.append(n1)
            } else {
                n2 = current.append(n2)
            }
            current = current.next!!
        }
        while (n1 != null) {
            n1 = current.append(n1)
            current = current.next!!
        }
        while (n2 != null) {
            n2 = current.append(n2)
            current = current.next!!
        }
        return newHead
    }

    private fun Node.append(n: Node): Node? {
        this.next = n
        return n.next
    }
}
