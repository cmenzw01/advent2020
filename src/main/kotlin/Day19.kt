class Day19 {
    private val rules = mutableMapOf<String,String>()
    private val messages = mutableListOf<String>()

    fun main() {
        var part1 = 0
        var part2 = 0
        var isRule = true

        this::class.java.getResource("day19.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                val lines = r.readLines().forEach {
                    if (it.isBlank()) {
                        isRule = false
                    } else {
                        if (isRule) {
                            val rule = it.split(": ")
                            rules[rule[0]] = rule[1]
                        } else {
                            messages.add(it)
                        }
                    }
                }

                part1 = part1()
//                part2 = part2(lines)
            }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1() : Int {
        var total = 0

        val regexString = buildRegex(rules["0"]!!)
        val regex = Regex(regexString)
        messages.forEach {
            val match = regex.matches(it)
            if (match) {
                total ++
            }
        }

        return total
    }

    private fun buildRegex(rule: String) : String {
        var regex = ""

        if (rule.startsWith("\"")) {
            regex = rule.trim('"')
        } else if (rule.contains(" | ")) {
            val parts = rule.split(" | ")
            val part1 = parts[0].split(" ")
            val part2 = parts[1].split(" ")
            regex = "(" + buildRegex(rules[part1[0]]!!)

            if (part1.size > 1) {
                regex += buildRegex(rules[part1[1]]!!)
            }

            regex += "|" + buildRegex(rules[part2[0]]!!)

            if (part2.size > 1) {
                regex += buildRegex(rules[part2[1]]!!)
            }

            regex += ")"
        } else {
            rule.split(" ").forEach {
                regex += buildRegex(rules[it]!!)
            }
        }

        return regex
    }

    private fun part2(lines: List<String>) : Int {
        var total = 0


        return total
    }
}

fun main(args: Array<String>) {
    Day19().main()
}
