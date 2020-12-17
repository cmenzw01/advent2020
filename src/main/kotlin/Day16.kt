class Day16 {
    private val rulePattern = Regex("(\\w+\\s?\\w+):\\s(\\d+)-(\\d+)\\sor\\s(\\d+)-(\\d+)")
    private val validTickets = mutableListOf<IntArray>()

    fun main() {
        var part1 = 0
        var part2 = 0L

        this::class.java.getResource("day16.txt")
                .openStream()
                .bufferedReader()
                .use { r ->
                    var inputSection = 0
                    val rules = mutableMapOf<String,Rule>()
                    var myTicket: IntArray = intArrayOf()
                    val tickets = mutableListOf<IntArray>()
                    var skip = false

                    val lines = r.readLines()
                    for (it in lines) {
                        if (skip) {
                            skip = false
                            continue
                        }

                        if (it.isBlank()) {
                            inputSection ++
                            if (inputSection == 1 || inputSection == 2) {
                                skip = true
                                continue
                            }
                        }

                        when (inputSection) {
                            0 -> {
                                val match = rulePattern.find(it)
                                val name = match!!.groups[1]!!.value
                                val min1 = match.groups[2]!!.value.toInt()
                                val max1 = match.groups[3]!!.value.toInt()
                                val min2 = match.groups[4]!!.value.toInt()
                                val max2 = match.groups[5]!!.value.toInt()
                                val rule = Rule(min1, max1, min2, max2)
                                rules[name] = rule
                            }
                            1 -> {
                                myTicket = it.split(",").map { it.toInt() }.toIntArray()
                            }
                            2 -> {
                                val ticket = it.split(",").map { it.toInt() }.toIntArray()
                                tickets.add(ticket)
                            }
                        }
                    }
                    part1 = part1(rules, tickets)
                    part2 = part2(rules, validTickets, myTicket)
                }

        println("Part1: $part1")
        println("Part2: $part2")
    }

    private fun part1(rules: Map<String,Rule>, tickets: List<IntArray>) : Int {
        var invalidTotal = 0

        tickets.forEach { ticket ->
            var ticketValid = true
            for (i in ticket) {
                var valid = false
                rules.values.forEach { rule ->
                    if (isValueValid(i, rule)) {
                        valid = true
                    }
                }
                if (!valid) {
                    ticketValid = false
                    invalidTotal += i
                }
            }
            if (ticketValid) {
                validTickets.add(ticket)
            }
        }


        return invalidTotal
    }

    private fun part2(rules: Map<String,Rule>, tickets: List<IntArray>, myTicket: IntArray) : Long {

        val ruleTicketsMatches = hashMapOf<Int, MutableList<Int>>()
        val ruleList = rules.values.toList()
        val ruleNames = rules.keys.toList()

        for (ruleIndex in rules.entries.indices) {
            var ruleMatchCount = 0
            for (ticketIndex in rules.entries.indices) {
                if (isTicketIndexValid(ticketIndex, tickets, ruleList[ruleIndex])) {
                    if (!ruleTicketsMatches.containsKey(ruleIndex)) {
                        ruleTicketsMatches[ruleIndex] = mutableListOf()
                    }
                    ruleTicketsMatches[ruleIndex]!!.add(ticketIndex)
                    ruleMatchCount ++
                }
            }
        }


        val ticketRuleMap: HashMap<Int, Int> = hashMapOf()
        ruleTicketsMatches.filter { it.value.size == 1 }.forEach { ticketRuleMap[it.value.first()] = it.key }

        while (ticketRuleMap.size < ruleTicketsMatches.size) {
            ruleTicketsMatches.forEach { ruleTicket ->
                if (ruleTicket.value.size == 1) {
                    ticketRuleMap[ruleTicket.value.first()] = ruleTicket.key
                } else {
                    val remove = mutableListOf<Int>()
                    for (i in ruleTicket.value.indices) {
                        if (ticketRuleMap.containsKey(ruleTicket.value[i])) {
                            remove.add(i)
                        }
                    }
                    remove.forEach { ruleTicket.value.removeAt(it) }
                }
            }
        }

        val departureIndices = mutableListOf<Int>()

        ticketRuleMap.forEach {
            val ruleName = ruleNames[it.value]
            if (ruleName.startsWith("departure")) {
                departureIndices.add(it.key)
            }
        }

        var answer: Long = 1
        departureIndices.forEach {
            answer *= myTicket[it]
        }

        return answer
    }

    private fun isTicketIndexValid(index: Int, tickets: List<IntArray>, rule: Rule) : Boolean {
        var valid = true

        for (ticket in tickets) {
            if (!isValueValid(ticket[index], rule)) {
                valid = false
                break
            }
        }

        return valid
    }

    private fun isValueValid(value: Int, rule: Rule) : Boolean {
        return (value >= rule.min1 && value <= rule.max1) || (value >= rule.min2 && value <= rule.max2)
    }
}

fun main(args: Array<String>) {
    Day16().main()
}

data class Rule (
    val min1: Int,
    val max1: Int,
    val min2: Int,
    val max2: Int
)