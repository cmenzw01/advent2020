//class Day20 {
//    private val rules = mutableMapOf<String,String>()
//    private val messages = mutableListOf<String>()
//
//    fun main() {
//        var part1 = 0L
//        var part2 = 0L
//
//        this::class.java.getResource("day20.txt")
//            .openStream()
//            .bufferedReader()
//            .use { r ->
//                val lines = r.readLines().forEach {
//                    if (it.isBlank()) {
//                        isRule = false
//                    } else {
//                        if (isRule) {
//                            val rule = it.split(": ")
//                            rules[rule[0]] = rule[1]
//                        } else {
//                            messages.add(it)
//                        }
//                    }
//                }
//
//                part1 = part1()
//
//                part2 = part1()
//            }
//
//        println("Part1: $part1")
//        println("Part2: $part2")
//    }
//
//    private fun part1() : Long {
//        return 0L
//    }
//}
//
//fun main(args: Array<String>) {
//    Day19().main()
//}
