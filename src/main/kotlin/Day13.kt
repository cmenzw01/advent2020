import kotlin.contracts.contract
import kotlin.math.abs

class Day13 {
    fun main() {

        var part1 = 0L
        var part2 = 0L

        this::class.java.getResource("day13.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    val input = r.readLines()
                    val timestamp = input[0].toInt()
                    val busses = input[1].split(",").filter { it != "x" }.map { it.toInt() }
                    val busses2 = input[1].split(",")
                    part1 = part1(timestamp, busses)
                    part2 = part2(busses2)
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1(timestamp: Int, busses: List<Int>) : Long {
        var firstBus = -1L
        var firstTime = -1L
        var time = timestamp

        while (firstBus < 0) {
            for (bus in busses) {
                if (time % bus == 0) {
                    firstBus = bus.toLong()
                    firstTime = time.toLong()
                    break
                }
            }

            time ++
        }

        return firstBus * (firstTime - timestamp)
    }

    private fun part2(busses: List<String>) : Long {
        val firstBus = busses[0].toLong()

        var maxBusId: Long = -1
        var maxIndex: Int = -1
        var max2BusId: Long = -1
        var max2Index: Int = -1

        busses.forEachIndexed { i, it ->
            if (it != "x" && it.toLong() > maxBusId) {
                max2BusId = maxBusId
                max2Index = maxIndex
                maxBusId = it.toLong()
                maxIndex = i
            } else if (it != "x" && it.toLong() > max2BusId) {
                max2BusId = it.toLong()
                max2Index = i
            }
        }

        var start = -1L
        while (true) {
            if ((start + maxIndex) % maxBusId == 0L && (start + max2Index) % max2BusId == 0L) {
                break
            }
            start ++
        }

        var answer = -1L
        var timestamp = start

        while (answer < 0) {
            for (i in busses.indices) {
                val bus = busses[i]
                if (bus == "x") {
                    continue
                }

                val busId = busses[i].toLong()
                if ((timestamp + i) % busId != 0L) {
                    break
                }

                if (i == busses.lastIndex) {
                    answer = timestamp
                }
            }

            timestamp += (maxBusId * max2BusId)
        }

        return answer
    }
}

fun main(args: Array<String>) {
    Day13().main()
}