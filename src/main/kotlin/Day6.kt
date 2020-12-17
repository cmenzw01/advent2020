class Day6 {
    fun main() {
        var total = 0
        var totalAll = 0
        this::class.java.getResource("day6.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                val lines = r.readLines()
                var answers = ""
                var counter = 0
                var personCount = 0
                while (counter <= lines.size) {
                    if (counter < lines.size && lines[counter].isNotBlank()) {
                        answers += lines[counter].trim()
                        personCount ++
                    } else {
                        total += countUnique(answers)
                        totalAll += countUnique(answers, personCount)
                        answers = ""
                        personCount = 0
                    }
                    counter ++
                }
            }

        println("Part1: $total")
        println("Part2: $totalAll")
    }

    private fun countUnique(answers: String) : Int {
        val set: HashSet<Char> = hashSetOf()
        answers.toCharArray().map { set.add(it) }
        return set.size
    }

    private fun countUnique(answers: String, personCount: Int) : Int {
        val map: HashMap<Char,Int> = hashMapOf()
        answers.toCharArray().forEach {
            if (!map.containsKey(it)) {
                map[it] = 1
            } else {
                map[it] = map[it]!! + 1
            }
        }
        return map.filter { it.value == personCount }.size
    }
}

fun main(args: Array<String>) {
    Day6().main()
}