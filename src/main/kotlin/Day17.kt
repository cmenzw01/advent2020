class Day17 {
    fun main() {
        var part1 = 0L
        var part2 = 0L

        this::class.java.getResource("day17.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    val active = hashSetOf<String>()
                    val active2 = hashSetOf<String>()
                    var x = 0
                    var y = 0
                    val z = 0
                    val w = 0

                    r.readLines().forEachIndexed { index, line ->
                        y = index
                        line.toCharArray().forEachIndexed{ i, c ->
                            x = i

                            if (c == '#') {
                                active.add("$x,$y,$z")
                                active2.add("$x,$y,$z,$w")
                            }
                        }
                    }

                    part1 = part1(active, 6)
                    part2 = part2(active2, 6)
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1(active: HashSet<String>, cycles: Int) : Long {
        var state = active

        for (i in 0 until cycles) {
            state = cycle(state)
        }

        return state.size.toLong()
    }

    private fun part2(active: HashSet<String>, cycles: Int) : Long {
        var state = active

        for (i in 0 until cycles) {
            state = cycle2(state)
        }

        return state.size.toLong()
    }

    private fun cycle(state: HashSet<String>) : HashSet<String> {
        val newState = hashSetOf<String>()
        val neighbors = hashSetOf<String>()
        state.forEach { neighbors.addAll(getNeighbors(it)) }

        neighbors.forEach {
            val neighborCount = getActiveNeighborCount(state, it)
            if (state.contains(it) && (neighborCount == 2 || neighborCount == 3)) {
                newState.add(it)
            } else if (!state.contains(it) && neighborCount == 3) {
                newState.add(it)
            }
        }

        return newState
    }

    private fun cycle2(state: HashSet<String>) : HashSet<String> {
        val newState = hashSetOf<String>()
        val neighbors = hashSetOf<String>()
        state.forEach { neighbors.addAll(getNeighbors2(it)) }

        neighbors.forEach {
            val neighborCount = getActiveNeighborCount2(state, it)
            if (state.contains(it) && (neighborCount == 2 || neighborCount == 3)) {
                newState.add(it)
            } else if (!state.contains(it) && neighborCount == 3) {
                newState.add(it)
            }
        }

        return newState
    }

    private fun getNeighbors(cube: String) : HashSet<String> {
        val neighbors = hashSetOf<String>()

        val coords = cube.split(",")
        val x = coords[0].toInt()
        val y = coords[1].toInt()
        val z = coords[2].toInt()

        for (zn in IntRange(z - 1, z + 1)) {
            for (yn in IntRange(y - 1, y + 1)) {
                for (xn in IntRange(x - 1, x + 1)) {
                    if (zn == z && yn == y && xn == x) {
                        continue
                    }

                    neighbors.add("$xn,$yn,$zn")
                }
            }
        }

        return neighbors
    }

    private fun getNeighbors2(cube: String) : HashSet<String> {
        val neighbors = hashSetOf<String>()

        val coords = cube.split(",")
        val x = coords[0].toInt()
        val y = coords[1].toInt()
        val z = coords[2].toInt()
        val w = coords[3].toInt()

        for (zn in IntRange(z - 1, z + 1)) {
            for (yn in IntRange(y - 1, y + 1)) {
                for (xn in IntRange(x - 1, x + 1)) {
                    for (wn in IntRange(w - 1, w + 1)) {
                        if (zn == z && yn == y && xn == x && wn == w) {
                            continue
                        }

                        neighbors.add("$xn,$yn,$zn,$wn")
                    }
                }
            }
        }

        return neighbors
    }

    private fun getActiveNeighborCount(state: HashSet<String>, cube: String) : Int {
        var count = 0

        val neighbors = getNeighbors(cube)
        neighbors.forEach {
            if (state.contains(it)) {
                count ++
            }
        }

        return count
    }

    private fun getActiveNeighborCount2(state: HashSet<String>, cube: String) : Int {
        var count = 0

        val neighbors = getNeighbors2(cube)
        neighbors.forEach {
            if (state.contains(it)) {
                count ++
            }
        }

        return count
    }
}

fun main(args: Array<String>) {
    Day17().main()
}
