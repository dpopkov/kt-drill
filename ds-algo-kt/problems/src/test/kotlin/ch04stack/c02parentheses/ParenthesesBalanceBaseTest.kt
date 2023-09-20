package learn.algo.dsalgokt.ch04stack.c02parentheses

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

abstract class ParenthesesBalanceBaseTest(
    private val checker: IParenthesesBalance
) {
    @Test
    fun checkBalanced() {
        val s = "h((e))llo(world)()"
        val result = checker.checkBalanced(s)
        assertTrue(result)
    }

    @Test
    fun checkUnbalanced() {
        val s = "(hello world"
        val result = checker.checkBalanced(s)
        assertFalse(result)
    }
}
