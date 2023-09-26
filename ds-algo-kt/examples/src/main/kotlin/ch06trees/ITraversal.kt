package learn.algo.dsalgokt.ch06trees

interface ITraversal<T> {
    /**
     * Обходит все узлы дерева согласно алгоритму
     * реализованному в имплементирующем классе.
     */
    fun traverse(treeNode: TreeNode<T>)
}
