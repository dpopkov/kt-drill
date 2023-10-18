package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.IBinaryNode
import kotlin.math.max

class BinaryTreeHeightFinder : IBinaryTreeHeightFinder {
    override fun <T> height(node: IBinaryNode<T>?): Int {
        return node?.let {
            1 + max(
                height(it.left),
                height(it.right),
            )
        } ?: -1
    }
}
