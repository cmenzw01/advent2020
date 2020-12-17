class Day15 {
    private val pattern = Regex("mem\\[(\\d+)\\]\\s=\\s(\\d+)")

    fun main() {
        var part1 = 0
        var part2 = 0

        this::class.java.getResource("day15.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    val input = r.readLines()[0].split(",").map { it.toInt() }.toIntArray()
                    part1 = part1(input, 2020)
                    part2 = part1(input, 30000000)
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1(numArray: IntArray, target: Int) : Int {
        val nums2 = arrayOfNulls<Int?>(target)
        numArray.forEachIndexed { index, i -> nums2[i] = index + 1 }

        var counter = numArray.size
        var next = 0
        var isFirst = false

        while (counter < target - 1) {
            counter ++

            isFirst = nums2[next] == null

            val num: Int = if (isFirst) {
                0
            } else {
                counter - nums2[next]!!
            }

            nums2[next] = counter
            next = num

//            println("Number ${counter + 1} is $next")
        }

        return next
    }
}

fun main(args: Array<String>) {
    Day15().main()
}