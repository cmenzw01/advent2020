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

                part1 = eval()

                rules["8"] = "42 | 42 42 | 42 42 42 | 42 42 42 42 | 42 42 42 42 42 | 42 42 42 42 42 42 | 42 42 42 42 42 42 42 | 42 42 42 42 42 42 42 42"
                rules["11"] = "42 31 | 42 42 31 31 | 42 42 42 31 31 31 | 42 42 42 42 31 31 31 31 | 42 42 42 42 42 31 31 31 31 31 | 42 42 42 42 42 42 31 31 31 31 31 31 | 42 42 42 42 42 42 42 31 31 31 31 31 31 31 | 42 42 42 42 42 42 42 42 31 31 31 31 31 31 31 31"
                part2 = eval()
            }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun eval() : Int {
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

            regex = "("

            parts.forEachIndexed {index, part ->
                val nums = part.split(" ")
                nums.forEach { n ->
                    regex += buildRegex(rules[n]!!)
                }

                if (index < parts.lastIndex) {
                    regex += "|"
                }
            }

            regex += ")"
        } else {
            rule.split(" ").forEach {
                regex += buildRegex(rules[it]!!)
            }
        }

        return regex
    }
}

fun main(args: Array<String>) {
    Day19().main()
}
