package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.runUsingSystemOut
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InOrderTraversalTest {

    @Test
    fun traverse() {
        val traversal = InOrderTraversal<Int>(
            visit = {node -> println(node.value) }
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

        val inOrderExpected = """
            0
            1
            5
            7
            8
            9
        """.trimIndent()
        val actualInOrder = runUsingSystemOut {
            traversal.traverse(tree)
        }
        assertEquals(inOrderExpected, actualInOrder.trimEnd())
    }
}