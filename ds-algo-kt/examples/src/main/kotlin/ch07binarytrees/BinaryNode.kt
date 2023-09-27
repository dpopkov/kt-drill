package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.IBinaryNode

class BinaryNode<T>(
    override val value: T,
    override var left: IBinaryNode<T>? = null,
    override var right: IBinaryNode<T>? = null,
) : IBinaryNode<T> {
    override fun toString(): String = asDiagram()
}

typealias Visitor<T> = (BinaryNode<T>) -> Unit

