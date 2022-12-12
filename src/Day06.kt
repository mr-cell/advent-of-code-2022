fun main() {
    fun getMarkerPosition(input: String, markerLength: Int): Int {
        return input.toCharArray().toList()
            .windowed(markerLength)
            .mapIndexedNotNull { index, chars -> if (chars.distinct() == chars) index + markerLength else null }
            .first()
    }
    fun part1(input: List<String>): Int {
        return getMarkerPosition(input.first(), 4)
    }

    fun part2(input: List<String>): Int {
        return getMarkerPosition(input.first(), 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}