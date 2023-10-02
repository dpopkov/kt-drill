package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.IBinaryNode

interface IBinaryTraversal<T> {
    /**
     * Обходит узлы двоичного дерева согласно алгоритму реализованному в имплементации интерфейса.
     */
    fun traverse(node: IBinaryNode<T>)
}
