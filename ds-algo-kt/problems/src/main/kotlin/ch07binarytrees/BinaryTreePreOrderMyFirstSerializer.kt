package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.ch05queues.ArrayListQueue
import learn.algo.dsalgokt.ch05queues.IQueue
import learn.algo.dsalgokt.common.IBinaryNode

/**
 * Реализует сериализацию/десериализацию путем pre-order обхода узлов дерева.
 */
class BinaryTreePreOrderMyFirstSerializer<T> : IBinaryTreeSerializer<T> {
    override fun serialize(treeNode: IBinaryNode<T>): String {
        fun StringBuilder.serializeChild(node: IBinaryNode<T>?) {
            if (node == null) {
                append(", null")
            } else {
                append(", ").append(node.value)
                serializeChild(node.left)
                serializeChild(node.right)
            }
        }
        return buildString {
            append("[")
            append(treeNode.value)
            serializeChild(treeNode.left)
            serializeChild(treeNode.right)
            append("]")
        }
    }

    override fun deserialize(input: String, valueDeserializer: (String) -> T): IBinaryNode<T> {
        require(input[0] == '[' && input.last() == ']') { "Input must be enclosed in brackets"}
        val tokens = input.substring(1, input.lastIndex).split(", ")
        val queue = ArrayListQueue<String>().enqueueAll(tokens)
        val root = buildNodeRec(queue, valueDeserializer)
        return root
    }

    private fun buildNodeRec(values: IQueue<String>, valueDeserializer: (String) -> T): BinaryNode<T> {
        val value: T = valueDeserializer(values.dequeue())
        val node = BinaryNode(value)
        val leftStr = values.peek()
        if (leftStr == "null" ) {
            values.dequeue()
        } else {
            node.left = buildNodeRec(values, valueDeserializer)
        }
        val rightStr = values.peek()
        if (rightStr == "null") {
            values.dequeue()
        } else {
            node.right = buildNodeRec(values, valueDeserializer)
        }
        return node
    }
}
