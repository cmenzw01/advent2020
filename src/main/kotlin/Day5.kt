class Day5 {

    fun main() {
        var max = 0
        val takenSeats = mutableListOf<Int>()
        this::class.java.getResource("day5.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                r.readLines()
                .forEach {
                    val seatId = getSeatId(it)
                    takenSeats.add(seatId)
                    if (seatId > max) {
                        max = seatId
                    }
                }
            }
        println("Part1: $max")

        takenSeats.sort()
        for (i in 1 until takenSeats.size - 1) {
            if (takenSeats[i] - 2 == takenSeats[i - 1]) {
                println("Part2: ${takenSeats[i] - 1}")
                break
            }
        }
    }

    private fun getSeatId(pass: String) : Int {
        var rowMin = 0
        var rowMax = 127
        var columnMin = 0
        var columnMax = 7

        pass.toCharArray().slice(IntRange(0,6)).forEach {
            val x = (rowMax - rowMin + 1) / 2
            if (it == 'F') {
                rowMax -= x
            } else if (it == 'B') {
                rowMin += x
            }
        }

        pass.toCharArray().slice(IntRange(7,9)).forEach {
            val x = (columnMax - columnMin + 1) / 2
            if (it == 'L') {
                columnMax -= x
            } else if (it == 'R') {
                columnMin += x
            }
        }

        return (rowMin * 8) + columnMin
    }
}

fun main(args: Array<String>) {
    Day5().main()
}