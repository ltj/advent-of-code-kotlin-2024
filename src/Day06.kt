fun main() {
    data class Point(val x: Int, val y: Int)
    data class Guard(val position: Point, val direction: String)
    data class LabMap(val width: Int, val height: Int, val obstacles: Set<Point>, val guard: Guard)

    fun readMap(input: List<String>): LabMap {
        val obstacles = hashSetOf<Point>()
        var guard = Guard(Point(0, 0), "up")
        val width = input[0].length
        val height = input.size

        input.forEachIndexed { y, l ->
            l.forEachIndexed { x, c ->
                if (c == '#') {
                    obstacles.add(Point(x, y))
                }
                if (c == '^') {
                    guard = Guard(Point(x, y), "up")
                }
            }
        }

        return LabMap(width, height, obstacles, guard)
    }

    fun getRoute(map: LabMap): Pair<Set<Point>, Boolean> {
        val (width, height, obstacles, _) = map
        var guard = map.guard

        fun reachedMapEdge(guard: Guard): Boolean {
            return guard.position.x == 0 || guard.position.x == width - 1
                    || guard.position.y == 0 || guard.position.y == height - 1
        }

        fun changeDirection(guard: Guard): Guard {
            return when (guard.direction) {
                "up" -> guard.copy(direction = "right")
                "right" -> guard.copy(direction = "down")
                "down" -> guard.copy(direction = "left")
                "left" -> guard.copy(direction = "up")
                else -> guard
            }
        }

        val visited = hashSetOf(guard.position)
        val obstaclesSeen = ArrayDeque<Guard>()
        var loop = false

        while (!reachedMapEdge(guard)) {
            val nextPoint = when (guard.direction) {
                "up" -> Point(guard.position.x, guard.position.y - 1)
                "right" -> Point(guard.position.x + 1, guard.position.y)
                "down" -> Point(guard.position.x, guard.position.y + 1)
                "left" -> Point(guard.position.x - 1, guard.position.y)
                else -> guard.position
            }
            if (obstacles.contains(nextPoint)) {
                if (obstaclesSeen.contains(guard)) {
                    loop = true
                    break
                }
                obstaclesSeen.add(guard)
                guard = changeDirection(guard)
            } else {
                guard = guard.copy(position = nextPoint)
                visited.add(nextPoint)
            }
        }

        return Pair(visited, loop)
    }

    fun part1(input: List<String>): Int = getRoute(readMap(input)).first.size

    fun part2(input: List<String>): Int {
        val map = readMap(input)
        return getRoute(map).first.parallelStream()
            .filter {
                val newMap = map.copy(obstacles = map.obstacles + it)
                getRoute(newMap).second
            }.count().toInt()
    }

    // Or read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
