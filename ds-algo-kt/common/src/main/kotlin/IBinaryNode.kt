package learn.algo.dsalgokt.common

interface IBinaryNode<T> {
    val value: T
    var left: IBinaryNode<T>?
    var right: IBinaryNode<T>?

    fun asDiagram(): String {
        return diagram(this)
    }
}

private fun <T> diagram(
    node: IBinaryNode<T>?,
    top: String = "",
    root: String = "",
    bottom: String = ""
): String {
    return node?.let {
        if (node.left == null && node.right == null) {
            "$root${node.value}\n"
        } else {
            diagram(node.right, top = "$top ", root = "$top┌──", bottom = "$top│ ") +
                    root + "${node.value}\n" +
                    diagram(node.left, top = "$bottom│ ", root = "$bottom└──", bottom = "$bottom ")
        }
    } ?: "${root}null\n"
}
