package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.IBinaryNode

class BinaryTreePreOrderSerializerTodo<T> : IBinaryTreeSerializer<T> {
    override fun serialize(treeNode: IBinaryNode<T>): String {
        TODO()
    }

    override fun deserialize(input: String, valueDeserializer: (String) -> T): IBinaryNode<T> {
        TODO()
    }
}
