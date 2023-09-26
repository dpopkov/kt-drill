package learn.algo.dsalgokt.ch06trees

class TreeNode<T>(val value: T) {
    val children: MutableList<TreeNode<T>> = mutableListOf()

    fun add(child: TreeNode<T>) = children.add(child)

}

/**
 * Функция применяемая к каждому узлу дерева.
 */
typealias NodeVisitor<T> = (TreeNode<T>) -> Unit
