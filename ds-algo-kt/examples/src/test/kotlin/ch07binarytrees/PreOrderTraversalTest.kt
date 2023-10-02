package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.runUsingSystemOut
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PreOrderTraversalTest {
    @Test
    fun traverse() {
        val traversal = PreOrderTraversal<Int>(
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

        val preOrderExpected = """
            7
            1
            0
            5
            9
            8
        """.trimIndent()
        val actualPreOrder = runUsingSystemOut {
            traversal.traverse(tree)
        }
        assertEquals(preOrderExpected, actualPreOrder.trimEnd())
    }
}
