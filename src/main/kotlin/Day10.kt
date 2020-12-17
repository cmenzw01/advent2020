class Day10 {
    private lateinit var nums: IntArray
    private var paths = 0L
    private val cache = hashMapOf<Int,Long>()

    fun main() {

        var part1 = 0L
        var part2 = 0L

        this::class.java.getResource("day10.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    nums = r.readLines().map { it.toInt() }.sorted().toIntArray()
                    part1 = findDiff()
                    part2 = findCount()
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun findDiff() : Long {
        val counts = hashMapOf<Int,Int>()
        counts[nums[0]] = 1

        for (i in 1 until nums.size) {
            val diff = nums[i] - nums[i - 1]
            if (!counts.containsKey(diff)) {
                counts[diff] = 0
            }
            counts[diff] = counts[diff]!! + 1
        }

        if (!counts.containsKey(3)) {
            counts[3] = 0
        }
        counts[3] = counts[3]!! + 1

        return counts[1]!!.toLong() * counts[3]!!
    }

    private fun findCount() : Long {
        for (start in nums.indices) {
            if (nums[start] > 3) {
                continue
            }

            paths += traverse(start)
        }

        return paths
    }

    private fun traverse(index: Int) : Long {
        var total = 0L
        if (index == nums.size - 1) {
            return 1
        }

        for (next in index + 1 until nums.size) {
            if (nums[next] - nums[index] <= 3) {
                if (cache.containsKey(next)) {
                    total += cache[next]!!
                } else {
                    val count = traverse(next)
                    cache[next] = count
                    total += count
                }
            } else {
                break
            }
        }

        return total
    }

}

fun main(args: Array<String>) {
    Day10().main()
}