fun main() {
    val roundOutcomes = mapOf(
        Pair(Pair("A", "X"), 3),
        Pair(Pair("A", "Y"), 6),
        Pair(Pair("A", "Z"), 0),
        Pair(Pair("B", "X"), 0),
        Pair(Pair("B", "Y"), 3),
        Pair(Pair("B", "Z"), 6),
        Pair(Pair("C", "X"), 6),
        Pair(Pair("C", "Y"), 0),
        Pair(Pair("C", "Z"), 3),
    )

    val shapePoints = mapOf(
        Pair("X", 1),
        Pair("Y", 2),
        Pair("Z", 3),
    )


    fun calculateScore(signs: Pair<String, String>): Int {
        return roundOutcomes[signs]!! + shapePoints[signs.second]!!
    }

    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" ") }
            .map { Pair(it[0], it[1]) }
            .sumOf { calculateScore(it) }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}