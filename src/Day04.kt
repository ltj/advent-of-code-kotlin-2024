fun main() {
    val word = """XMAS""".toRegex()

    fun part1(input: List<String>): Int {
        var count = 0
        for ((i, line) in input.withIndex()) {
            count += word.findAll(line).count() + word.findAll(line.reversed()).count()
            for (j in line.indices) {
                if (i <= input.lastIndex - 3) {
                    val vsub = (i..i + 3).map { input[it][j] }.joinToString("")
                    if (vsub == "XMAS" || vsub == "SAMX") count++
                    if (j <= line.lastIndex - 3) {
                        val drsub = (i..i + 3).zip(j..j + 3).map { input[it.first][it.second] }
                            .joinToString("")
                        if (drsub == "XMAS" || drsub == "SAMX") count++
                    }
                    if (j >= 3) {
                        val dlsub = (i..i + 3).zip(j downTo j - 3).map { input[it.first][it.second] }
                            .joinToString("")
                        if (dlsub == "XMAS" || dlsub == "SAMX") count++
                    }
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for ((i, line) in input.withIndex()) {
            for (j in line.indices) {
                if (i <= input.lastIndex - 2 && j <= line.lastIndex - 2) {
                    val subr = (i..i + 2).zip(j..j + 2).map { input[it.first][it.second] }.joinToString("")
                    val subl = (i..i + 2).zip(j + 2 downTo j).map { input[it.first][it.second] }.joinToString("")
                    if ((subr == "MAS" || subr == "SAM") && (subl == "MAS" || subl == "SAM")) count++
                }
            }
        }
        return count
    }

    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
    println(part1(testInput))
    check(part1(testInput) == 18)
    println(part2(testInput))
    check(part2(testInput) == 9)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
