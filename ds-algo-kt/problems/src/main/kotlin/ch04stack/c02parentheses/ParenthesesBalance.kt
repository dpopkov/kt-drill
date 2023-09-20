package learn.algo.dsalgokt.ch04stack.c02parentheses

object ParenthesesBalance : IParenthesesBalance {
    override fun checkBalanced(s: String): Boolean {
        val st = CharStack()
        for(ch in s) {
            when(ch) {
                '(' -> st.push(ch)
                ')' -> if (st.isEmpty()) return false else st.pop()
            }
        }
        return st.isEmpty()
    }

    private class CharStack {
        private val storage = arrayListOf<Char>()

        fun push(ch: Char) {
            storage.add(ch)
        }

        fun pop(): Char = storage.removeLast()

        fun isEmpty() = storage.isEmpty()
    }
}
