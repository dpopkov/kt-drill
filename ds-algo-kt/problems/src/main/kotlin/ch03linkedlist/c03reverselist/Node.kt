package learn.algo.dsalgokt.ch03linkedlist.c03reverselist

open class Node(
    val value: Int,
    var next: Node? = null
) {
    override fun toString(): String {
        return if (next == null) "$value" else "$value -> $next"
    }
}
