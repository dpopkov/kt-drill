package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.ch05queues.ArrayListQueue
import learn.algo.dsalgokt.common.IBinaryNode

class BinaryNode<T>(
    override val value: T,
    override var left: IBinaryNode<T>? = null,
    override var right: IBinaryNode<T>? = null,
) : IBinaryNode<T> {
    override fun toString(): String = asDiagram()
}

typealias Visitor<T> = (IBinaryNode<T>) -> Unit

/**
 * Строит двоичное дерево послойно из переданных значений.
 */
fun <T> buildBinaryTreeInLevelOrder(vararg values: T): BinaryNode<T> {
    val queue = ArrayListQueue<BinaryNode<T>>()
    val root = BinaryNode(values[0])
    queue.enqueue(root)
    var childValueIdx = 1
    while(childValueIdx < values.size) {
        val parent = queue.dequeue()
        val left = BinaryNode(values[childValueIdx++])
        parent.left = left
        queue.enqueue(left)
        if (childValueIdx < values.size) {
            val right = BinaryNode(values[childValueIdx++])
            parent.right = right
            queue.enqueue(right)
        }
    }
    return root
}
