fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.asRanges() }
            .count { it.first fullyOverlaps it.second || it.second fullyOverlaps it.first }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.asRanges() }
            .count { it.first overlaps it.second }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private infix fun IntRange.fullyOverlaps(other: IntRange): Boolean =
    first <= other.first && last >= other.last

private infix fun IntRange.overlaps(other: IntRange): Boolean =
    first <= other.last && last >= other.first

private fun String.asIntRange(): IntRange =
    substringBefore("-").toInt()..substringAfter("-").toInt()

private fun String.asRanges(): Pair<IntRange, IntRange> =
    substringBefore(",").asIntRange() to substringAfter(",").asIntRange()