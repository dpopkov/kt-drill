package learn.nc150

class P0049GroupAnagramsKDone : P0049GroupAnagrams {
    override fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val groups = mutableMapOf<String, MutableList<String>>()
        for (word in strs) {
            val freq = word.calculateFrequency()
            val group = groups[freq]
            if (group != null) {
                group.add(word)
            } else {
                groups[freq] = mutableListOf(word)
            }
        }
        return groups.entries.map { it.value }
    }

    private fun String.calculateFrequency() = IntArray(26).apply {
        this@calculateFrequency.forEach { this@apply[it - 'a']++ }
    }.contentToString()
}
