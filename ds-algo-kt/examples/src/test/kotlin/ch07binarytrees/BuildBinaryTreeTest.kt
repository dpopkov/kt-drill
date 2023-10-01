package learn.algo.dsalgokt.ch07binarytrees

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class BuildBinaryTreeTest {

    @Test
    fun testBuildBinaryTreeInLevelOrder() {
        val expected = """
             ┌──null
            ┌──9
            │ └──8
            7
            │ ┌──5
            └──1
             └──0
        """.trimIndent()
        val tree = buildBinaryTreeInLevelOrder(7, 1, 9, 0, 5, 8)
        assertEquals(expected, tree.asDiagram().trimEnd())
    }
}
