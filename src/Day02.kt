import kotlin.math.abs

fun main() {
    fun isSafe(report: List<Int>): Boolean {
        val isIncreasing = report[1] - report[0] > 0
        report.forEachIndexed { i, v ->
            if (i > 0) {
                val diff = v - report[i - 1]
                if (isIncreasing && diff < 0 || !isIncreasing && diff > 0 || abs(diff) !in 1..3) {
                    return false
                }
            }
        }
        return true
    }

    fun isSafeDampened(report: List<Int>): Boolean {
        if (isSafe(report)) {
            return true
        } else {
            for (i in report.indices) {
                val newReport = report.toMutableList()
                newReport.removeAt(i)
                if (isSafe(newReport)) {
                    return true
                }
            }
        }
        return false
    }

    fun part1(input: List<String>): Int =
        input.count { s -> isSafe(s.split(" ").map { it.toInt() }) }

    fun part2(input: List<String>): Int =
        input.count { s -> isSafeDampened(s.split(" ").map { it.toInt() }) }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
