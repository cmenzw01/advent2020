import java.math.BigInteger
import kotlin.math.min

class Day18 {
    fun main() {
        var part1 = 0L
        var part2 = BigInteger.ZERO

        this::class.java.getResource("day18.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    val lines = r.readLines()

                    part1 = part1(lines)
                    part2 = part2(lines)
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1(lines: List<String>) : Long {
        var total = 0L

        lines.map { it.replace(" ", "") }
            .forEach { l ->
                var line = l

                var openParentheses = line.lastIndexOf("(")
                while (openParentheses >= 0) {
                    val closeParentheses = line.indexOf(")", openParentheses)
                    val result = evaluate(line.slice(IntRange(openParentheses + 1, closeParentheses - 1)))
                    line = line.slice(IntRange(0, openParentheses - 1)) + result + line.slice(IntRange(closeParentheses + 1, line.lastIndex))
                    openParentheses = line.lastIndexOf("(")
                }



                total += evaluate(line)
        }
        return total
    }

    private fun evaluate(expression: String) : Long {
        var line = expression
        var operatorCount = line.toCharArray().filter { it == '+' || it == '*' }.size

        while (operatorCount > 1) {
            val firstOperator = line.indexOfFirst { it == '+' || it == '*' }
            val secondOperator = line.substring(IntRange(firstOperator + 1, line.lastIndex)).indexOfFirst { it == '+' || it == '*' } + firstOperator + 1
            val result = evaluate(line.slice(IntRange(0, secondOperator - 1)))
            line = result.toString() + line.slice(IntRange(secondOperator, line.lastIndex))
            operatorCount = line.toCharArray().filter { it == '+' || it == '*' }.size
        }

        var total = 0L
        var operator = ""
        var first = ""
        var second = ""

        line.forEach { c ->
            when (c) {
                '+' -> operator = c.toString()
                '*' -> operator = c.toString()
                else -> {
                    val value = c.toString()
                    if (operator == "") {
                        first += value
                    } else {
                        second += value
                    }
                }
            }
        }

        total = if (operator == "+") {
            first.toLong() + second.toLong()
        } else {
            first.toLong() * second.toLong()
        }

        return total
    }

    private fun part2(lines: List<String>) : BigInteger {
        var total = BigInteger.ZERO

        lines.map { it.replace(" ", "") }
            .forEach { l ->
                var line = l

                var openParentheses = line.lastIndexOf("(")
                while (openParentheses >= 0) {
                    val closeParentheses = line.indexOf(")", openParentheses)
                    val result = evaluate2(line.slice(IntRange(openParentheses + 1, closeParentheses - 1)))
                    line = line.slice(IntRange(0, openParentheses - 1)) + result + line.slice(IntRange(closeParentheses + 1, line.lastIndex))
                    openParentheses = line.lastIndexOf("(")
                }

                val answer = evaluate2(line)
                total += BigInteger.valueOf(answer)
                println("$l = $answer")
            }
        return total
    }

    private fun evaluate2(expression: String) : Long {
        var line = expression
        var addCount = line.toCharArray().filter { it == '+' }.size

        while (addCount > 0 && line.toCharArray().filter { it == '+' || it == '*' }.size > 1) {
            val firstAdd = line.indexOfFirst { it == '+' }
            val firstOp = line.indexOfAny(charArrayOf('+', '*'))
            val start = if (firstAdd == firstOp) 0 else firstOp + 1
            val nextOp = line.indexOfAny(charArrayOf('+', '*'), firstAdd + 1)
            val end = if (nextOp < 0) line.lastIndex else nextOp - 1
            if (firstAdd > 0) {
                val r = evaluate2(line.slice(IntRange(start, end)))
                val lastIndex = line.lastIndex
                var newline = if (start == 0) r.toString() else line.slice(IntRange(0, firstOp)) + r.toString()

                if (end < lastIndex) {
                    newline += line.slice(IntRange(min(end + 1, line.lastIndex), line.lastIndex))
                }
                line = newline
                addCount = line.toCharArray().filter { it == '+' }.size
            }
        }

        var operatorCount = line.toCharArray().filter { it == '+' || it == '*' }.size

        while (operatorCount > 1) {
            val firstOperator = line.indexOfFirst { it == '+' || it == '*' }
            val secondOperator = line.substring(IntRange(firstOperator + 1, line.lastIndex)).indexOfFirst { it == '+' || it == '*' } + firstOperator + 1
            val result = evaluate(line.slice(IntRange(0, secondOperator - 1)))
            line = result.toString() + line.slice(IntRange(secondOperator, line.lastIndex))
            operatorCount = line.toCharArray().filter { it == '+' || it == '*' }.size
        }

        var total = 0L
        var operator = ""
        var first = ""
        var second = ""

        line.forEach { c ->
            when (c) {
                '+' -> operator = c.toString()
                '*' -> operator = c.toString()
                else -> {
                    val value = c.toString()
                    if (operator == "") {
                        first += value
                    } else {
                        second += value
                    }
                }
            }
        }

        total = if (operator == "+") {
            first.toLong() + second.toLong()
        } else {
            first.toLong() * second.toLong()
        }

        return total
    }
}

fun main(args: Array<String>) {
    Day18().main()
}
