import kotlin.math.abs

fun main() {
    fun asTwoLists(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val l1 = mutableListOf<Int>()
        val l2 = mutableListOf<Int>()
        input.forEach { s ->
            val (a, b) = s.split("\\s+".toRegex(), 2).map { it.toInt() }
            l1.add(a)
            l2.add(b)
        }
        return Pair(l1, l2)
    }

    fun part1(input: List<String>): Int {
        val (l1, l2) = asTwoLists(input)
        l1.sort()
        l2.sort()
        return l1.zip(l2).sumOf { (a, b) -> abs(a - b) }
    }

    fun part2(input: List<String>): Int {
        val (l1, l2) = asTwoLists(input)
        val memo = mutableMapOf<Int, Int>() // not strictly necessary memoization
        return l1.sumOf { a -> memo.getOrPut(a, { l2.count { b -> b == a } }) * a }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
