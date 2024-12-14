fun main() {
    fun getCals(input: List<String>): List<Pair<Long, List<Long>>> = input
        .map { l -> l.split(" ", ":").filter { it.isNotEmpty() }.map { it.toLong() } }
        .map { it.first() to it.slice(1..it.lastIndex) }

    fun formulaFound(target: Long, constants: List<Long>, useCat: Boolean = false): Boolean {
        var results = setOf(constants[0])
        if (constants.size > 1) {
            for (i in 1..constants.lastIndex) {
                val newResults = mutableSetOf<Long>()
                for (r in results) {
                    if (r + constants[i] <= target) newResults.add(r + constants[i])
                    if (r * constants[i] <= target) newResults.add(r * constants[i])
                    if (useCat) {
                        val cat = (r.toString() + constants[i].toString()).toLong()
                        if (cat <= target) newResults.add(cat)
                    }
                }
                results = newResults
            }
        }
        return target in results
    }

    fun part1(input: List<String>): Long =
        getCals(input).filter { formulaFound(it.first, it.second) }.sumOf { it.first }

    fun part2(input: List<String>): Long =
        getCals(input).filter { formulaFound(it.first, it.second, true) }.sumOf { it.first }

    // Or read a large test input from the `src/Day07_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)


    // Read the input from the `src/Day07.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
