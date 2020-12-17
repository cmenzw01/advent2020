import java.math.BigInteger
import kotlin.math.pow

class Day14 {
    private val pattern = Regex("mem\\[(\\d+)\\]\\s=\\s(\\d+)")

    fun main() {
        var part1 = BigInteger.ZERO
        var part2 = BigInteger.ZERO

        this::class.java.getResource("day14.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    val input = r.readLines()
                    part1 = part1(input)
                    part2 = part2(input)
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1(lines: List<String>) : BigInteger {
        var mask = ""
        val map = hashMapOf<Long,BigInteger>()

        lines.forEach {
            if (it.startsWith("mask")) {
                mask = it.slice(7..42)
            } else {
                val match = pattern.find(it)
                val address = match!!.groups[1]!!.value.toLong()
                val value = match.groups[2]!!.value.toBigInteger()
                val binary = value.toString(2).padStart(36, '0').toCharArray()

                mask.toCharArray().forEachIndexed { index, c ->
                    if (c != 'X') {
                        binary[index] = c
                    }
                }

                val newValue = binary.joinToString("").toBigInteger(2)
                map[address] = newValue
            }
        }

        return map.values.sumOf { it }
    }

    private fun part2(lines: List<String>) : BigInteger {
        var mask = ""
        val map = hashMapOf<BigInteger,BigInteger>()

        lines.forEach {
            if (it.startsWith("mask")) {
                mask = it.slice(7..42)
            } else {
                val match = pattern.find(it)
                val address = match!!.groups[1]!!.value.toBigInteger()
                val addressBinary = address.toString(2).padStart(36, '0').toCharArray()
                val value = match.groups[2]!!.value.toBigInteger()

                mask.toCharArray().forEachIndexed { index, c ->
                    if (c != '0') {
                        addressBinary[index] = c
                    }
                }

                val addresses = mutableListOf<CharArray>()
                val floats = addressBinary.count { c -> c == 'X' }
                val two = 2
                val permutations = two.toDouble().pow(floats).toInt()
                val permLength = (permutations - 1).toString(2).length

                for (add in 0 until permutations) {
                    val copy = addressBinary.copyOf()
                    val bin = add.toString(2).padStart(permLength, '0').toCharArray()

                    var xCount = 0

                    for (i in copy.indices) {
                        if (copy[i] == 'X') {
                            copy[i] = bin[xCount]
                            xCount ++
                        }
                    }

                    addresses.add(copy)
                }

                addresses.forEach { a ->
                    val s = a.joinToString("")
                    println(s)
                    map[s.toBigInteger(2)] = value
                }

            }
        }

        return map.values.sumOf { it }
    }
}

fun main(args: Array<String>) {
    Day14().main()
}