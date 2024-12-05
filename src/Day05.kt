fun main() {
    val rule = """^(\d+)\|(\d+)""".toRegex()
    val update = """^(\d+,?)+""".toRegex()

    fun getRulesAndUpdates(input: List<String>): Pair<Map<Int, List<Int>>, List<List<Int>>> {
        val rules =
            input.filter { rule.matches(it) }
                .map { r -> r.trim().split("|").let { Pair(it.first().toInt(), it.last().toInt()) } }
                .groupBy({ it.first }, { it.second })
        val updates = input.filter { update.matches(it) }.map { u -> u.trim().split(",").map { it.toInt() } }
        return Pair(rules, updates)
    }

    fun List<Int>.ruleSort(rules: Map<Int, List<Int>>): List<Int> = sortedWith { a, b ->
        when {
            rules[a]?.contains(b) == true -> -1
            rules[b]?.contains(a) == true -> 1
            else -> 0
        }
    }

    fun updateOk(update: List<Int>, rules: Map<Int, List<Int>>): Boolean =
        update.ruleSort(rules) == update

    fun part1(input: List<String>): Int {
        val (rules, updates) = getRulesAndUpdates(input)
        return updates.filter { updateOk(it, rules) }.sumOf { it[it.lastIndex / 2] }
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = getRulesAndUpdates(input)
        return updates.filter { !updateOk(it, rules) }
            .map { update -> update.ruleSort(rules) }.sumOf { it[it.lastIndex / 2] }
    }

    // Or read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
