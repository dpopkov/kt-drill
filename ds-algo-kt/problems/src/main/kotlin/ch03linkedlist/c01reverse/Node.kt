package ch03linkedlist.c01reverse

open class Node(
    val value: Int,
    var next: Node? = null
) {
    override fun toString(): String {
        return if (next == null) "$value" else "$value -> $next"
    }
}
