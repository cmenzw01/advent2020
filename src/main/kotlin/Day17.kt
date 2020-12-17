class Day17 {
    fun main() {
        var part1 = 0L
        var part2 = 0L

        this::class.java.getResource("day17.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    val lines = r.readLines()

                    part1 = part1()
                    part2 = part2()
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1() : Long {
        return 0
    }

    private fun part2() : Long {
        return 0
    }
}

fun main(args: Array<String>) {
    Day16().main()
}
