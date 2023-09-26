package learn.algo.dsalgokt.ch06trees

import learn.algo.dsalgokt.common.runUsingSystemOut
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

abstract class TraversalBaseTest(
    private val traversal: ITraversal<Int>,
    private val expected: String
) {
    @Test
    fun testTraverse() {
        val tree: TreeNode<Int> = makeTree()
        val actual = runUsingSystemOut {
            traversal.traverse(tree)
        }
        assertEquals(expected, actual.trimEnd())
    }

    private fun makeTree(): TreeNode<Int> {
        val root = TreeNode(1)

        // tier 2
        val left = TreeNode(2)
        val right = TreeNode(3)
        root.add(left)
        root.add(right)

        // tier 3
        val t4 = TreeNode(4)
        left.add(t4)
        left.add(TreeNode(5))
        right.add(TreeNode(6))
        val t7 = TreeNode(7)
        right.add(t7)

        // tier 4
        t4.add(TreeNode(8))
        t4.add(TreeNode(9))
        t7.add(TreeNode(10))
        t7.add(TreeNode(11))
        return root
    }
}
