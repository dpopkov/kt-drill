package learn.algo.dsalgokt.ch04stack.c02parentheses

interface IParenthesesBalance {
    /**
     * Проверяет что все открывающие и закрывающие круглые скобки в строке сбалансированы.
     */
    fun checkBalanced(s: String): Boolean
}
