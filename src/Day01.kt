fun main() {
    fun parseInput(input: List<String>): List<Int> =
        input.fold(mutableListOf(mutableListOf<Int>())) { acc, i ->
            if (i == "") acc.add(mutableListOf())
            else acc.last().add(i.toInt())
            acc
        }
            .map { it.sum() }
            .sortedDescending()

    fun part1(input: List<String>): Int {
        val calories = parseInput(input)
        return calories.first()
    }

    fun part2(input: List<String>): Int {
        val calories = parseInput(input)
        return calories.take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
