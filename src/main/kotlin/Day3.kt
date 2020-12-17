class Day3 {
    fun main() {
        val map = arrayListOf<CharArray>()

        this::class.java.getResource("day3.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                r.readLines()
                    .forEach {
                        map.add(it.toCharArray())
                    }
            }

        val part1 = countTrees(map,3, 1)
        println("Part1: $part1")

        val inputs = mutableListOf<Pair<Int,Int>>()
        inputs.add(Pair(1,1))
        inputs.add(Pair(3,1))
        inputs.add(Pair(5,1))
        inputs.add(Pair(7,1))
        inputs.add(Pair(1,2))

        var product = 1L

        inputs.forEach {
            val trees = countTrees(map, it.first, it.second)
            product *= trees
        }

        println("Part2: $product")
    }

    private fun countTrees(map: List<CharArray>, right: Int, down: Int) : Int {
        val width = map[0].size
        val height = map.size
        var trees = 0

        var x = right
        var y = down

        while (y < height) {
            if (map[y][x] == '#') {
                trees ++
            }

            y += down
            x = (x + right) % width
        }

        return trees
    }
}

fun main(args: Array<String>) {
    Day3().main()
}