package learn.nc150

class P0217ContainsDuplicateKDone : P0217ContainsDuplicate {
    override fun containsDuplicate(nums: IntArray): Boolean {
        val seenNumbers = hashSetOf<Int>()
        for (number in nums) {
            if (seenNumbers.contains(number)) {
                return true
            }
            seenNumbers.add(number)
        }
        return false
    }
}
