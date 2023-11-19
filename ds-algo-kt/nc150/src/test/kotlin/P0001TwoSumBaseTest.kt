package learn.nc150

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertContentEquals

abstract class P0001TwoSumBaseTest(
    private val underTest: P0001TwoSum
) {
    companion object {
        @JvmStatic
        fun testData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(intArrayOf(2, 7, 11, 15), 9, intArrayOf(0, 1)),
                Arguments.of(intArrayOf(3, 2, 4), 6, intArrayOf(1, 2)),
                Arguments.of(intArrayOf(3, 3), 6, intArrayOf(0, 1)),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    fun test(nums: IntArray, target: Int, expected: IntArray) {
        val actual = underTest.twoSum(nums, target)
        assertContentEquals(expected, actual)
    }
}
