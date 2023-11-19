package learn.nc150

class P0001TwoSumKDone : P0001TwoSum {
    override fun twoSum(nums: IntArray, target: Int): IntArray {
        val complementingIndexes = hashMapOf<Int, Int>()
        for (idx in nums.indices) {
            val firstNumber = nums[idx]
            val foundIdx = complementingIndexes[firstNumber]
            if (foundIdx == null) {
                complementingIndexes[target - firstNumber] = idx
            } else {
                return intArrayOf(foundIdx, idx )
            }
        }
        throw IllegalStateException("This must not happen according to requirements")
    }
}
