package learn.algo.dsalgokt.ch06trees

import learn.algo.dsalgokt.ch05queues.ArrayListQueue

/**
 * Реализует обход в ширину.
 */
class LevelOrderTraversal<T>(
    val visit: NodeVisitor<T> = { node -> println(node.value) }
) : ITraversal<T> {
    override fun traverse(treeNode: TreeNode<T>) {
        val queue = ArrayListQueue<TreeNode<T>>()
        queue.enqueue(treeNode)
        while (!queue.isEmpty) {
            val node = queue.dequeue().also(visit)
            node.children.forEach { queue.enqueue(it) }
        }
    }
}
