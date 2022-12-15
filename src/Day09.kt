import java.lang.IllegalArgumentException
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {

    fun part1(input: List<String>): Int {
        val moves = parseInput(input)
        return followPath(moves, 2)
    }

    fun part2(input: List<String>): Int {
        val moves = parseInput(input)
        return followPath(moves, 10)
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

private fun parseInput(input: List<String>): String =
    input.joinToString("") {
        val direction = it.substringBefore(" ")
        val numberOfMoves = it.substringAfter(" ").toInt()
        direction.repeat(numberOfMoves)
    }

private fun followPath(headPath: String, knots: Int): Int {
    val rope = Array(knots) { Position(0, 0) }
    val tailVisits = mutableSetOf(Position(0, 0))

    headPath.forEach { direction ->
        rope[0] = rope[0].move(direction)
        rope.indices.windowed(2, 1) { (head, tail) ->
            if (!rope[head].touches(rope[tail])) {
                rope[tail] = rope[tail].moveTowards(rope[head])
            }
        }
        tailVisits += rope.last()
    }
    return tailVisits.size
}

data class Position(val x: Int, val y: Int) {

    fun touches(other: Position): Boolean =
        (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1

    fun moveTowards(other: Position): Position =
        Position(
            x = (other.x - x).sign + x,
            y = (other.y - y).sign + y
        )

    fun move(direction: Char): Position =
        when (direction) {
            'U' -> copy(y = y + 1)
            'D' -> copy(y = y - 1)
            'L' -> copy(x = x - 1)
            'R' -> copy(x = x + 1)
            else -> throw IllegalArgumentException("Unknown direction: $direction")
        }
}