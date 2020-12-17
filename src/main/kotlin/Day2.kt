class Day2 {

    private val pattern = Regex("(\\d*)-(\\d*)\\s(\\w):\\s(\\w*)")

    fun main() {
        var count = 0L
        var count2 = 0L
        this::class.java.getResource("day2.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                r.readLines()
                    .forEach {
                        val match = pattern.find(it)?.groups!!
                        val min = match[1]!!.value.toInt()
                        val max = match[2]!!.value.toInt()
                        val character = match[3]?.value!!.toCharArray()[0]
                        val password = match[4]?.value!!
                        val passwordArray = password.toCharArray()
                        val charCount = passwordArray.filter { c -> c == character }.count()
                        if (charCount in min..max) {
                            count++
                        }
                        if ((passwordArray[min - 1] == character && passwordArray[max - 1] != character) ||
                            (passwordArray[min - 1] != character && passwordArray[max - 1] == character)) {
                            count2++
                        }
                    }
            }
        println("Part 1: $count")
        println("Part 2: $count2")
    }
}

fun main(args: Array<String>) {
    Day2().main()
}