package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.runUsingSystemOut
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PostOrderTraversalTest {
    @Test
    fun traverse() {
        val traversal = PostOrderTraversal<Int>(
            visit = { node -> println(node.value) }
        )
        val tree = buildBinaryTreeInLevelOrder(7, 1, 9, 0, 5, 8)
        val initialExpected = """
             ┌──null
            ┌──9
            │ └──8
            7
            │ ┌──5
            └──1
             └──0
        """.trimIndent()
        assertEquals(initialExpected, tree.asDiagram().trimEnd())

        val postOrderExpected = """
            0
            5
            1
            8
            9
            7
        """.trimIndent()
        val actualPostOrder = runUsingSystemOut {
            traversal.traverse(tree)
        }
        assertEquals(postOrderExpected, actualPostOrder.trimEnd())
    }
}
