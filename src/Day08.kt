import kotlin.math.abs

fun main() {
    fun parseMap(input: List<String>): Triple<Map<Char, Set<Point>>, Int, Int> {
        val width = input[0].length
        val height = input.size
        val map = mutableMapOf<Char, Set<Point>>()
        input.forEachIndexed { y, l ->
            l.forEachIndexed { x, c ->
                if (c != '.') map[c] = map.getOrDefault(c, setOf()) + Point(x, y)
            }
        }
        return Triple(map, width, height)
    }

    fun pointOnMap(point: Point, width: Int, height: Int): Boolean =
        point.x in 0..<width && point.y in 0..<height

    tailrec fun combine(
        items: List<Point>,
        idx: Int = 1,
        pairs: List<Pair<Point, Point>> = emptyList()
    ): List<Pair<Point, Point>> =
        when {
            items.size <= 1 -> pairs
            idx == items.lastIndex -> combine(items.drop(1), 1, pairs + Pair(items[0], items[idx]))
            else -> combine(items, idx + 1, pairs + Pair(items[0], items[idx]))
        }

    fun getAntinodes(ants: Pair<Point, Point>, width: Int, height: Int): List<Point> {
        val xDist = ants.first.x - ants.second.x
        val yDist = ants.first.y - ants.second.y
        val a1 = Point(ants.first.x + xDist, ants.first.y + yDist)
        val a2 = Point(ants.second.x + (-1 * xDist), ants.second.y + (-1 * yDist))
        return listOf(a1, a2).filter { pointOnMap(it, width, height) }
    }

    fun getAntinodesExtended(ants: Pair<Point, Point>, width: Int, height: Int): List<Point> {
        val xDist = ants.first.x - ants.second.x
        val yDist = ants.first.y - ants.second.y
        val a1 = Point(ants.first.x + xDist, ants.first.y + yDist)
        val a2 = Point(ants.second.x + (-1 * xDist), ants.second.y + (-1 * yDist))
        val ans1 = generateSequence(a1) { Point(it.x + xDist, it.y + yDist) }
            .takeWhile { pointOnMap(it, width, height) }
            .toList()
        val ans2 = generateSequence(a2) { Point(it.x + (-1 * xDist), it.y + (-1 * yDist)) }
            .takeWhile { pointOnMap(it, width, height) }
            .toList()
        return ans1 + ans2 + ants.toList()
    }

    fun part1(input: List<String>): Int {
        val (map, width, height) = parseMap(input)

        return map.asSequence()
            .map { combine(it.value.toList()) }
            .flatten().flatMap { getAntinodes(it, width, height) }
            .toSet().count()
    }

    fun part2(input: List<String>): Int {
        val (map, width, height) = parseMap(input)

        return map.asSequence()
            .map { combine(it.value.toList()) }
            .flatten().flatMap { getAntinodesExtended(it, width, height) }
            .toSet().count()
    }

    // Or read a large test input from the `src/Day08_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    val p2 = part2(testInput)
    check(p2 == 34)

    // Read the input from the `src/Day08.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
