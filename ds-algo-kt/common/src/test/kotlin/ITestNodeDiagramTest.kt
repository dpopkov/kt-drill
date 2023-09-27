package learn.algo.dsalgokt.common

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ITestNodeDiagramTest {
    @Test
    fun testDiagram() {
        val n0 = TestNode(0)
        val n1 = TestNode(1)
        val n2 = TestNode(2)
        val n5 = TestNode(5)
        val n6 = TestNode(6)
        val n7 = TestNode(7)
        val n8 = TestNode(8)
        val n9 = TestNode(9)
        val n10 = TestNode(10)
        val n11 = TestNode(11)
        n1.left = n0
        n1.right = n5
        n5.left = n2
        n5.right = n6
        n9.left = n8
        n9.right = n11
        n11.left = n10
        n7.left = n1
        n7.right = n9

        val expected = """
              ┌──null
             ┌──11
             │ └──10
            ┌──9
            │ └──8
            7
            │  ┌──6
            │ ┌──5
            │ │ └──2
            └──1
             └──0
        """.trimIndent()
        val actual = runUsingSystemOut {
            print(n7)
        }.trimEnd()
        assertEquals(expected, actual)
    }
}

private class TestNode<T>(
    override val value: T,
    override var left: IBinaryNode<T>? = null,
    override var right: IBinaryNode<T>? = null,
) : IBinaryNode<T> {
    override fun toString(): String = asDiagram()
}