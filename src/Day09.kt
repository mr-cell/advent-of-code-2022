import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val moves = parseInput(input)

        return moves
            .fold(mutableListOf(Position(0, 0))) { heads, move ->
                heads.add(heads.last().move(x = move.first, y = move.second))
                heads
            }
            .fold(mutableListOf(Position(0, 0))) { tails, head ->
                val tail = tails.last()
                val distance = tail.distance(head)
                val x = if (distance.first == 0) 1  else distance.first / abs(distance.first)
                val y = if (distance.second == 0) 1 else distance.second / abs(distance.second)

                if ((abs(distance.first) == 2 && abs(distance.second) == 1)
                    || (abs(distance.first) == 1 && abs(distance.second) == 2)) {

                    tails.add(tail.move(x = x, y = y))
                } else if (abs(distance.first) == 2) {
                    tails.add(tail.move(x = x))
                } else if (abs(distance.second) == 2) {
                    tails.add(tail.move(y = y))
                } else {
                    tails.add(tail.move())
                }
                tails
            }.distinct().count()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 0)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

private fun parseInput(input: List<String>): List<Move> =
    input.map { it.split(" ") }
        .map { Pair(it.first(), it.last().toInt()) }
        .flatMap {
            (0 until it.second).map { _ ->
                when (it.first) {
                    "L" -> Pair(-1 , 0)
                    "R" -> Pair(1, 0)
                    "U" -> Pair(0, 1)
                    else -> Pair(0, -1) // down
                }
            }
        }

typealias Move = Pair<Int, Int>