package learn.algo.dsalgokt.ch07binarytrees

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

abstract class BinaryTreePreOrderSerializerBaseTest(
    private val serializer: IBinaryTreeSerializer<Int>
) {
    @Test
    fun serialize() {
        val tree = buildBinaryTreeInLevelOrder(15, 10, 25, 5, 12, 17)
        val asDiagram = tree.asDiagram()
        assertEquals("""
             ┌──null
            ┌──25
            │ └──17
            15
            │ ┌──12
            └──10
             └──5
        """.trimIndent(), asDiagram.trimEnd())

        val actual = serializer.serialize(tree)

        val expected = "[15, 10, 5, null, null, 12, null, null, 25, 17, null, null, null]"
        assertEquals(expected, actual)
    }

    @Test
    fun `deserialize single leaf node`() {
        val input = "[15, null, null]"
        val expected = """
            15
        """.trimIndent()

        val tree = serializer.deserialize(input) { it.toInt() }

        val actual = tree.asDiagram()
        assertEquals(expected, actual.trimEnd())
    }

    @Test
    fun `deserialize multiple nodes`() {
        val input = "[15, 10, 5, null, null, 12, null, null, 25, 17, null, null, null]"
        val expected = """
             ┌──null
            ┌──25
            │ └──17
            15
            │ ┌──12
            └──10
             └──5
        """.trimIndent()

        val tree = serializer.deserialize(input) { it.toInt() }

        val actual = tree.asDiagram()
        assertEquals(expected, actual.trimEnd())
    }
}
