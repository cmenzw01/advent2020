class Day9 {
    fun main() {

        var part1 = 0L
        var part2 = 0L

        this::class.java.getResource("day9.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                val nums = r.readLines().map { it.toLong() }.toLongArray()
                part1 = findFirstInvalid(nums, 25)
                part2 = findContiguous(nums, part1)
            }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun findFirstInvalid(nums: LongArray, preamble: Int) : Long {
        for (testIndex in preamble until nums.size) {
            var valid = false
            for (firstIndex in testIndex - preamble until testIndex) {
                for (secondIndex in firstIndex + 1 until testIndex) {
                    if (nums[firstIndex] + nums[secondIndex] == nums[testIndex]) {
                        valid = true
                        break
                    }
                }
                if (valid) {
                    break
                }
            }
            if (!valid) {
                return nums[testIndex]
            }
        }

        throw Exception("Should not get here - must be a bug")
    }

    private fun findContiguous(nums: LongArray, sum: Long) : Long {
        var correctRange: LongArray = longArrayOf()
        for (startIndex in nums.indices) {
            var testSum = nums[startIndex]
            var testIndex: Int = startIndex + 1

            while (testSum < sum) {
                testSum += nums[testIndex]
                testIndex ++
            }

            if (testSum == sum) {
                correctRange = nums.sliceArray(IntRange(startIndex, testIndex - 1))
                break
            }
        }

        correctRange.sort()

        return correctRange[0] + correctRange[correctRange.size - 1]
    }

}

fun main(args: Array<String>) {
    Day9().main()
}