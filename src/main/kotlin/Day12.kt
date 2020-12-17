import kotlin.math.abs

class Day12 {
    fun main() {

        var part1 = 0
        var part2 = 0

        this::class.java.getResource("day12.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    val directions = r.readLines()
                    part1 = part1(directions)
                    part2 = part2(directions)
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1(directions: List<String>) : Int {
        var east = 0
        var north = 0
        var dir = Direction.EAST

        directions.forEach {
            var action = it.substring(0, 1)
            val value = it.substring(1, it.length).toInt()

            if (action == "F") {
                action = dir.name.substring(0, 1)
            }

            when(action) {
                "N" -> north += value
                "S" -> north -= value
                "E" -> east += value
                "W" -> east -= value
                "L" -> dir = turn(dir, "L", value)
                "R" -> dir = turn(dir, "R", value)
            }
        }

        return abs(east) + abs(north)
    }

    private fun part2(directions: List<String>) : Int {
        var shipEast = 0
        var shipNorth = 0

        var waypointEast = 10
        var waypointNorth = 1

        directions.forEach {
            val action = it.substring(0, 1)
            val value = it.substring(1, it.length).toInt()

            when(action) {
                "N" -> waypointNorth += value
                "S" -> waypointNorth -= value
                "E" -> waypointEast += value
                "W" -> waypointEast -= value
                "R" -> {
                    val pair = rotateWaypoint("R", value, waypointEast, waypointNorth)
                    waypointEast = pair.first
                    waypointNorth = pair.second
                }
                "L" -> {
                    val pair = rotateWaypoint("L", value, waypointEast, waypointNorth)
                    waypointEast = pair.first
                    waypointNorth = pair.second
                }
                "F" -> {
                    shipEast += (value * waypointEast)
                    shipNorth += (value * waypointNorth)
                }
            }
        }

        return abs(shipEast) + abs(shipNorth)
    }

    private fun turn(current: Direction, action: String, degrees: Int) : Direction {
        val turns = degrees / 90
        val dir = current.value

        var newDirVal = if (action == "R") {
            dir + turns
        } else {
            dir - turns
        }

        if (newDirVal < 0) {
            newDirVal += 4
        } else if (newDirVal > 3) {
            newDirVal -= 4
        }

        return Direction.from(newDirVal)
    }

    private fun rotateWaypoint(dir: String, degrees: Int, currentEast: Int, currentNorth: Int) : Pair<Int,Int> {
        var newEast = currentEast
        var newNorth = currentNorth
        val times = degrees / 90

        for (i in 1..times) {
            if (dir == "R") {
                val east = newNorth
                val north = -newEast
                newEast = east
                newNorth = north
            } else {
                val east = -newNorth
                val north = newEast
                newEast = east
                newNorth = north
            }
        }

        return Pair(newEast, newNorth)
    }
}

fun main(args: Array<String>) {
    Day12().main()
}

enum class Direction(val value: Int) {
    EAST(0),
    SOUTH(1),
    WEST(2),
    NORTH(3);

    companion object {
        fun from(findValue: Int): Direction = Direction.values().first { it.value == findValue }
    }
}