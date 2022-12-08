fun main() {
    fun part1(input: List<String>): Int {
        return input.asSequence().map { it.split(",") }
            .map { Pair(it.first().split("-"), it.last().split("-")) }
            .map { Pair(Pair(it.first.first().toInt(), it.first.last().toInt()), Pair(it.second.first().toInt(), it.second.last().toInt())) }
            .filter {
                (it.first.first >= it.second.first && it.first.second <= it.second.second)
                        || (it.second.first >= it.first.first && it.second.second <= it.first.second )
            }.count()
    }

    fun part2(input: List<String>): Int {
        return input.asSequence().map { it.split(",") }
            .map { Pair(it.first().split("-"), it.last().split("-")) }
            .map { Pair(Pair(it.first.first().toInt(), it.first.last().toInt()), Pair(it.second.first().toInt(), it.second.last().toInt())) }
            .filter {
                (it.first.first <= it.second.second && it.first.second >= it.second.first)
                        || (it.second.first <= it.first.second && it.second.second >= it.first.first)
            }.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}