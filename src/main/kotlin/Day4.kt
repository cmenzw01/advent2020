class Day4 {
    private val pattern = ":([\\w#]*)\\s?"
    private val fields = listOf(
        Pair("byr", true),
        Pair("iyr", true),
        Pair("eyr", true),
        Pair("hgt", true),
        Pair("hcl", true),
        Pair("ecl", true),
        Pair("pid", true),
        Pair("cid", false))

    private val fields2 = listOf(
        Pair("byr:(19[2-9][0-9]|200[0-2])\\b", true),
        Pair("iyr:(201[0-9]|2020)\\b", true),
        Pair("eyr:(202[0-9]|2030)\\b", true),
        Pair("hgt:(59in|6[0-9]in|7[0-6]in)|(1[5-8][0-9]cm|19[0-3]cm)\\b", true),
        Pair("hcl:#[0-9a-f]{6}\\b", true),
        Pair("ecl:(amb|blu|brn|gry|grn|hzl|oth)\\b", true),
        Pair("pid:(\\d{9})\\b", true),
        Pair("cid:([\\w#]*)\\s?", false))

    fun main() {
        var validCount = 0
        var validCount2 = 0

        this::class.java.getResource("day4.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                val lines = r.readLines()
                var passport = ""
                var counter = 0
                while (counter <= lines.size) {
                    if (counter < lines.size && lines[counter].isNotBlank()) {
                        passport += " ${lines[counter]}"
                    } else {
                        if (validate(passport)) {
                            validCount ++
                        }
                        if (validate2(passport)) {
                            validCount2 ++
                        }
                        passport = ""
                    }
                    counter ++
                }
            }

        println("Part1: $validCount")
        println("Part2: $validCount2")
    }

    private fun validate(passport: String) : Boolean {
        fields.forEach {
            val match = Regex("${it.first}$pattern").find(passport)?.groups?.get(1)
            if (match == null && it.second) {
                return false
            }
        }

        return true
    }

    private fun validate2(passport: String) : Boolean {
        fields2.forEach {
            val matches = Regex(it.first).find(passport)?.groups
            val match = matches?.get(0)
            if (match == null && it.second) {
                return false
            }
        }

        println(passport)
        return true
    }
}

fun main(args: Array<String>) {
    Day4().main()
}