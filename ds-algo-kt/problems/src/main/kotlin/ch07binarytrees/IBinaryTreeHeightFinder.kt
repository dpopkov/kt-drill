package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.IBinaryNode

interface IBinaryTreeHeightFinder {
    /**
     * Находит высоту двоичного дерева.
     * За высоту принимается расстояние между корневым узлом и самым дальним листом дерева.
     * Высота дерева состоящего из одного узла таким образом равна 0.
     */
    fun <T> height(node: IBinaryNode<T>?): Int
}
