fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { Pair(it.substring(0, it.length/2), it.substring(it.length/2)) }
            .map { it.first.toCharArray().intersect(it.second.toCharArray().toList().toSet()) }
            .map { it.distinct() }
            .flatMap {
                it.map { char ->
                    return@map if (CharRange('a', 'z').contains(char)) {
                        char.minus('a') + 1
                    } else {
                        char.lowercaseChar().minus('a') + 27
                    }
                }
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toCharArray() }
            .windowed(3, 3)
            .map { it.reduce { acc, chars -> acc.intersect(chars.toList().toSet()).toCharArray()} }
            .flatMap {
                it.map { char ->
                    return@map if (CharRange('a', 'z').contains(char)) {
                        char.minus('a') + 1
                    } else {
                        char.lowercaseChar().minus('a') + 27
                    }
                }
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
