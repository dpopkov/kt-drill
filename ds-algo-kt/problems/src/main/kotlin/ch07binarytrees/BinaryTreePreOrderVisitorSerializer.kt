package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.IBinaryNode

class BinaryTreePreOrderVisitorSerializer<T> : IBinaryTreeSerializer<T> {

    private fun IBinaryNode<T>.preOrderTraverseWithNull(visit: (T?) -> Unit) {
        visit(value)
        this.left?.preOrderTraverseWithNull(visit) ?: visit(null)
        this.right?.preOrderTraverseWithNull(visit) ?: visit(null)
    }

    override fun serialize(treeNode: IBinaryNode<T>): String {
        return buildList {
            treeNode.preOrderTraverseWithNull {
                add(it.toString())
            }
        }.joinToString(", ", "[", "]")
    }

    override fun deserialize(input: String, valueDeserializer: (String) -> T): IBinaryNode<T> {
        val values = input.trim('[', ']')
            .split(", ")
            .map { if (it == "null") null else valueDeserializer(it) }
        val queue = ArrayDeque<T?>().apply { addAll(values) }
        val firstValue: T = queue.removeFirst() ?: throw IllegalArgumentException("Root node cannot contain null")
        val root = BinaryNode(firstValue)

        fun buildNode(): BinaryNode<T>? {
            return queue.removeFirst()?.let {
                val n: BinaryNode<T> = BinaryNode(it)
                n.left = buildNode()
                n.right = buildNode()
                n
            }
        }
        root.left = buildNode()
        root.right = buildNode()
        return root
    }
}
