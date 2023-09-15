package learn.algo.dsalgokt.ch03linkedlist.c02middle

class MiddleFinder : IMiddleFinder {
    override fun findMiddle(head: Node): Node {
        var slow = head
        var fast: Node? = head
        while (fast != null) {
            fast = fast.next
            if (fast != null) {
                slow = slow.next!!
                fast = fast.next
            }
        }
        return slow
    }
}
