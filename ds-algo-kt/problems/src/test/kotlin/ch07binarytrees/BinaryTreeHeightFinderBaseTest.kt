package learn.algo.dsalgokt.ch07binarytrees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

abstract class BinaryTreeHeightFinderBaseTest(
    private val finder: IBinaryTreeHeightFinder
) {
    companion object {
        @JvmStatic
        fun data(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(0, buildBinaryTreeInLevelOrder(1)),
                Arguments.of(1, buildBinaryTreeInLevelOrder(1, 2)),
                Arguments.of(1, buildBinaryTreeInLevelOrder(1, 2, 3)),
                Arguments.of(2, buildBinaryTreeInLevelOrder(1, 2, 3, 4)),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun test(expected: Int, tree: BinaryNode<Int>) {
        val actual = finder.height(tree)
        assertEquals(expected, actual)
    }

}
