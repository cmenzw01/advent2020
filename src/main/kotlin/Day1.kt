import java.io.File

class Day1 {
//    private val inputs = listOf<Long>(
//        1721,
//        979,
//        366,
//        299,
//        675,
//        1456)

    fun main() : Long {
        val inputs = this::class.java.getResource("day1.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                r.readLines()
                .map { it.toLong() }
            }

        var solution = -1L
        for (i in 0 until inputs.size - 2) {
            for (j in 1 until inputs.size - 1) {
                for (k in 2 until inputs.size - 2) {
                    if (inputs[i] + inputs[j] + inputs[k] == 2020L) {
                        solution = inputs[i] * inputs[j] * inputs[k]
                        break
                    }
                }
            }
        }
        return solution
    }
}

fun main(args: Array<String>) {
    println(Day1().main())
}