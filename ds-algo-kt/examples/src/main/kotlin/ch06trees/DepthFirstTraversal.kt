package learn.algo.dsalgokt.ch06trees

/**
 * Реализует обход в глубину.
 */
class DepthFirstTraversal<T>(
    val visit: NodeVisitor<T> = { node -> println(node.value) }
) : ITraversal<T> {
    override fun traverse(treeNode: TreeNode<T>) {
        visit(treeNode)
        treeNode.children.forEach(::traverse)
    }
}
