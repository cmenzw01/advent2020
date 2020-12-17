class Day8 {
    private var accumulator = 0
    private lateinit var lines: List<String>
    private val regex = Regex("([a-z]{3})\\s(\\+|-)(\\d+)")

    fun main() {

        var part1 = 0
        var part2 = 0

        this::class.java.getResource("day8.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                lines = r.readLines()
                var line = 0
                val usedLines = hashSetOf<Int>()

                while (usedLines.contains(line).not()) {
                    usedLines.add(line)
                    line = executeLine(line, null)
                }

                part1 = accumulator

                for (i in lines.indices) {
                    var line2 = 0
                    val usedLines2 = hashSetOf<Int>()
                    var success = true
                    accumulator = 0

                    while (line2 < lines.size) {
                        if (usedLines2.contains(line2)) {
                            success = false
                            break
                        }
                        usedLines2.add(line2)
                        line2 = executeLine(line2, i)
                    }

                    if (success) {
                        part2 = accumulator
                        break
                    }
                }
            }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun executeLine(line: Int, lineToChange: Int?) : Int {
        var nextLine = line + 1
        val groups = regex.find(lines[line])!!.groups
        var op = groups[1]!!.value

        if (lineToChange != null && (op == "nop" || op == "jmp") && line == lineToChange) {
            op = if (op == "nop") "jmp" else "nop"
        }

        val dir = groups[2]!!.value
        val value = groups[3]!!.value.toInt()

        when (op) {
            "acc" -> {
                if (dir == "+") {
                    accumulator += value
                } else {
                    accumulator -= value
                }
            }
            "jmp" -> {
                nextLine = if (dir == "+") {
                    line + value
                } else {
                    line - value
                }
            }
        }

        return nextLine
    }
}

fun main(args: Array<String>) {
    Day8().main()
}