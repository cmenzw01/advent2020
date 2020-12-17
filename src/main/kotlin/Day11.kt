class Day11 {

    fun main() {

        var part1 = 0
        var part2 = 0

        this::class.java.getResource("day11.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    val seats: CharArray = r.readLines().joinToString(separator = "").toCharArray()
                    part1 = part1(seats, 91)
                    part2 = part2(seats, 91)
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1(seats: CharArray, rowLength: Int) : Int {
        var oldSeats = charArrayOf()
        var newSeats = seats

        while (!oldSeats.contentEquals(newSeats)) {
            oldSeats = newSeats
            newSeats = iterate(oldSeats, rowLength)
//            printSeats(newSeats, rowLength)
        }

        return newSeats.filter { it == '#' }.count()
    }

    private fun part2(seats: CharArray, rowLength: Int) : Int {
        var oldSeats = charArrayOf()
        var newSeats = seats

        while (!oldSeats.contentEquals(newSeats)) {
            oldSeats = newSeats
            newSeats = iterate2(oldSeats, rowLength)
//            printSeats(newSeats, rowLength)
        }

        return newSeats.filter { it == '#' }.count()
    }

    private fun iterate(seats: CharArray, rowLength: Int) : CharArray {
        val newSeats = seats.clone()

        seats.forEachIndexed { i, c ->
            if (c == 'L' && countAdjacentOccupied(i, seats, rowLength) == 0) {
                newSeats[i] = '#'
            } else if (c == '#' && countAdjacentOccupied(i, seats, rowLength) >= 4) {
                newSeats[i] = 'L'
            } else {
                newSeats[i] = seats[i]
            }
        }

        return newSeats
    }

    private fun iterate2(seats: CharArray, rowLength: Int) : CharArray {
        val newSeats = seats.clone()

        seats.forEachIndexed { i, c ->
            if (c == 'L' && countAdjacentOccupied2(i, seats, rowLength) == 0) {
                newSeats[i] = '#'
            } else if (c == '#' && countAdjacentOccupied2(i, seats, rowLength) >= 5) {
                newSeats[i] = 'L'
            } else {
                newSeats[i] = seats[i]
            }
        }

        return newSeats
    }

    private fun countAdjacentOccupied(index: Int, seats: CharArray, rowLength: Int) : Int {
        val above = index - rowLength
        val below = index + rowLength
        val adjacentSeats = intArrayOf(getAboveLeft(index, rowLength), above, getAboveRight(index, rowLength), getLeft(index, rowLength), getRight(index, rowLength), getBelowLeft(index, rowLength), below, getBelowRight(index, rowLength))
        return adjacentSeats.filter { isOccupied(it, seats) }.count()
    }

    private fun countAdjacentOccupied2(index: Int, seats: CharArray, rowLength: Int) : Int {
        val adjacentSeats = intArrayOf(getAboveLeft2(seats, index, rowLength), getAbove2(seats, index, rowLength), getAboveRight2(seats, index, rowLength), getLeft2(seats, index, rowLength), getRight2(seats, index, rowLength), getBelowLeft2(seats, index, rowLength), getBelow2(seats, index, rowLength), getBelowRight2(seats, index, rowLength))
        return adjacentSeats.filter { isOccupied(it, seats) }.count()
    }

    private fun getAboveLeft(i: Int, rowLength: Int) : Int {
        var index = i - rowLength - 1
        if ((i + 1) % rowLength == 1) {
            index = -1
        }

        return index
    }

    private fun getAboveRight(i: Int, rowLength: Int) : Int {
        var index = i - rowLength + 1
        if ((i + 1) % rowLength == 0) {
            index = -1
        }

        return index
    }

    private fun getLeft(i: Int, rowLength: Int) : Int {
        var index = i - 1
        if ((i + 1) % rowLength == 1) {
            index = -1
        }

        return index
    }

    private fun getRight(i: Int, rowLength: Int) : Int {
        var index = i + 1
        if ((i + 1) % rowLength == 0) {
            index = -1
        }

        return index
    }

    private fun getBelowLeft(i: Int, rowLength: Int) : Int {
        var index = i + rowLength - 1
        if ((i + 1) % rowLength == 1) {
            index = -1
        }

        return index
    }

    private fun getBelowRight(i: Int, rowLength: Int) : Int {
        var index = i + rowLength + 1
        if ((i + 1) % rowLength == 0) {
            index = -1
        }

        return index
    }

    private fun getAbove2(seats: CharArray, i: Int, rowLength: Int) : Int {
        var index =  i - rowLength

        while (index >= 0 && seats[index] == '.') {
            index -= rowLength
        }

        return index
    }

    private fun getBelow2(seats: CharArray, i: Int, rowLength: Int) : Int {
        var index = i + rowLength
        while (index < seats.size && seats[index] == '.') {
            index += rowLength
        }
        return index
    }

    private fun getAboveLeft2(seats: CharArray, i: Int, rowLength: Int) : Int {
        var index = i - rowLength - 1
        if ((i + 1) % rowLength == 1) {
            return -1
        }

        while (index >= 0 && seats[index] == '.') {
            if ((index + 1) % rowLength == 1) {
                index = -1
                break
            }
            index = index - rowLength - 1
        }

        return index
    }

    private fun getAboveRight2(seats: CharArray, i: Int, rowLength: Int) : Int {
        var index = i - rowLength + 1
        if ((i + 1) % rowLength == 0) {
            return -1
        }

        while (index >= 0 && seats[index] == '.') {

            if ((index + 1) % rowLength == 0) {
                index = -1
                break
            }
            index = index - rowLength + 1
        }

        return index
    }

    private fun getLeft2(seats: CharArray, i: Int, rowLength: Int) : Int {
        var index = i - 1
        if ((i + 1) % rowLength == 1) {
            return -1
        }

        while (index >= 0 && seats[index] == '.') {

            if ((index + 1) % rowLength == 1) {
                index = -1
                break
            }
            index -= 1
        }

        return index
    }

    private fun getRight2(seats: CharArray, i: Int, rowLength: Int) : Int {
        var index = i + 1
        if ((i + 1) % rowLength == 0) {
            return -1
        }

        while (index < seats.size && seats[index] == '.') {

            if ((index + 1) % rowLength == 0) {
                index = -1
                break
            }
            index += 1
        }

        return index
    }

    private fun getBelowLeft2(seats: CharArray, i: Int, rowLength: Int) : Int {
        var index = i + rowLength - 1
        if ((i + 1) % rowLength == 1) {
            return -1
        }

        while (index < seats.size && seats[index] == '.') {

            if ((index + 1) % rowLength == 1) {
                index = -1
                break
            }
            index = index + rowLength - 1
        }

        return index
    }

    private fun getBelowRight2(seats: CharArray, i: Int, rowLength: Int) : Int {
        var index = i + rowLength + 1
        if ((i + 1) % rowLength == 0) {
            return -1
        }

        while (index < seats.size && seats[index] == '.') {

            if ((index + 1) % rowLength == 0) {
                index = -1
                break
            }
            index += rowLength + 1
        }

        return index
    }

    private fun isOccupied(index: Int, seats: CharArray) : Boolean {
        if (index < 0 || index >= seats.size) {
            return false
        }

        return seats[index] == '#'
    }

    private fun printSeats(seats: CharArray, rowLength: Int) {
        seats.forEachIndexed { index, c ->
            if (index % rowLength == 0) {
                print("\n")
            }
            print(seats[index])
        }
        println()
        println()
        println()
    }
}

fun main(args: Array<String>) {
    Day11().main()
}