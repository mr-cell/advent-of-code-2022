fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { it.sharedItem().priority() }

    fun part2(input: List<String>): Int =
        input.chunked(3)
            .sumOf { it.sharedItem().priority() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun Char.priority(): Int = when (this) {
    in 'a'..'z' -> (this - 'a') + 1
    in 'A'..'Z' -> (this - 'A') + 27
    else -> throw IllegalArgumentException("Letter not in range $this")
}

private fun List<String>.sharedItem(): Char =
    map { it.toSet() }
        .reduce { left, right -> left intersect right }
        .first()

private fun String.sharedItem(): Char =
    listOf(
        substring(0..length / 2),
        substring(length / 2)
    )
        .sharedItem()
