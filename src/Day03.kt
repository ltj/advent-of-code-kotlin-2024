fun main() {
    val exp = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

    fun sumOfMultiples(line: String): Int =
        exp.findAll(line).sumOf { result -> result.groupValues[1].toInt() * result.groupValues[2].toInt() }

    fun part1(input: List<String>): Int = sumOfMultiples(input.joinToString("\n"))

    fun part2(input: List<String>): Int = input.joinToString("\n").let { line ->
        val dos = line.split("do()")
        dos.sumOf { sumOfMultiples(it.split("don't()").first()) }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    val testInput2 = readInput("Day03_test2")
    check(part2(testInput2) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
