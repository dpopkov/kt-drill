package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.IBinaryNode

class PreOrderTraversal<T>(
    val visit: Visitor<T>
) : IBinaryTraversal<T> {
    override fun traverse(node: IBinaryNode<T>) {
        visit(node)
        node.left?.let { traverse(it) }
        node.right?.let { traverse(it) }
    }
}
