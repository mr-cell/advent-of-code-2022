import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val moves = parseInput(input)

        return moves
            .toHeadPositions()
            .fold(mutableListOf(Position(0, 0))) { tails, head ->
                tails.add(tails.last().newKnotPosition(head))
                tails
            }.distinct().count()
    }

    fun part2(input: List<String>): Int {
        val moves = parseInput(input)

        val initialKnotPositions = (0 until 9).map { _ -> Position(0, 0) }.toMutableList()
        return moves
            .toHeadPositions()
            .fold(mutableListOf(initialKnotPositions)) { knotsPositions, head ->
                val lastKnotsPositions = knotsPositions.last()
                val currentKnotPositions = mutableListOf(lastKnotsPositions[0].newKnotPosition(head))
                knotsPositions.add(currentKnotPositions)
                (1 until 9).map {
                    knotsPositions.last().add(lastKnotsPositions[it].newKnotPosition(currentKnotPositions[it - 1]))
                }
                knotsPositions
            }.map { it.last() }.distinct().count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val testInput2 = readInput("Day09_test_2")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)
    check(part2(testInput2) == 36)

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

private fun List<Move>.toHeadPositions(): List<Position> {
    return this.fold(mutableListOf(Position(0, 0))) { headPositions, move ->
        headPositions.add(headPositions.last().move(x = move.first, y = move.second))
        headPositions
    }
}

private fun Position.newKnotPosition(precedingKnotPosition: Position): Position {
    val distance = this.distance(precedingKnotPosition)
    val x = if (distance.first == 0) 1  else distance.first / abs(distance.first)
    val y = if (distance.second == 0) 1 else distance.second / abs(distance.second)

    return if ((abs(distance.first) == 2 && abs(distance.second) >= 1)
        || (abs(distance.first) >= 1 && abs(distance.second) == 2)) {

        this.move(x = x, y = y)
    } else if (abs(distance.first) == 2) {
        this.move(x = x)
    } else if (abs(distance.second) == 2) {
        this.move(y = y)
    } else {
        this.move()
    }
}

typealias Move = Pair<Int, Int>