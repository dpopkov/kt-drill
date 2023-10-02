package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.IBinaryNode

class InOrderTraversal<T>(
    val visit: Visitor<T>
) : IBinaryTraversal<T> {
    override fun traverse(node: IBinaryNode<T>) {
        node.left?.let { traverse(it) }
        visit(node)
        node.right?.let { traverse(it) }
    }
}
