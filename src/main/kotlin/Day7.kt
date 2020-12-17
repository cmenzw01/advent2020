class Day7 {
    private val suitcaseRegex = Regex("([a-z]+\\s[a-z]+)\\sbags\\scontain")
    private val containsRegex = Regex("(\\d+)\\s([a-z]+\\s[a-z]+)\\sbag")
    private val bags = hashMapOf<String,MutableMap<String,Int>>()

    fun main() {

        this::class.java.getResource("day7.txt")
            .openStream()
            .bufferedReader()
            .use { r ->
                r.readLines().forEach {
                    val suitcaseName = suitcaseRegex.find(it)!!.groups[1]!!.value
                    bags[suitcaseName] = mutableMapOf()
                    containsRegex.findAll(it).forEach { c ->
                        val count = c.groups[1]!!.value.toInt()
                        val name = c.groups[2]!!.value
                        bags[suitcaseName]?.put(name, count)
                    }
                }
            }
        val color = "shiny gold"
        var bagCount = 0

        for (bag in bags) {
            if (bag.key != color && traverse(bag.value, color)) {
                bagCount++
            }
        }

        val bagCount2 = count(bags["shiny gold"]!!, 0)

        println("Part1: $bagCount")
        println("Part2: $bagCount2")
    }

    private fun traverse(bag: MutableMap<String, Int>, color: String) : Boolean {
        if (bag.containsKey(color)) {
            return true
        } else if (bag.isEmpty()) {
            return false
        } else {
            for (childBag in bag.entries) {
                val match = traverse(bags[childBag.key]!!, color)
                if (match) {
                    return true
                }
            }
            return false
        }
    }

    private fun count(bag: MutableMap<String, Int>, count: Int) : Int {
        var bagCount = count

        if (bag.isEmpty().not()) {
            for (childBag in bag.entries) {
                for (i in 1..childBag.value) {
                    bagCount ++
                    bagCount = count(bags[childBag.key]!!, bagCount)
                }
            }
        }

        return bagCount
    }
}

fun main(args: Array<String>) {
    Day7().main()
}