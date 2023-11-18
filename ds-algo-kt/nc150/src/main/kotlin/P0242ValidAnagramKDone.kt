package learn.nc150

class P0242ValidAnagramKDone : P0242ValidAnagram {
    override fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) return false
        val counts = IntArray(26)
        for (i in s.indices) {
            counts[s[i] - 'a']++
            counts[t[i] - 'a']--
        }
        return counts.all { it == 0 }
    }
}
