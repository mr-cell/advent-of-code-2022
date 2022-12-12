fun main() {

    fun part1(input: List<String>): Int {
        val grid = parseInput(input)
        return (1 until grid.size - 1).sumOf { y ->
            (1 until grid[y].size - 1).count { x ->
                grid.isVisible(x, y)
            }
        } + (2 * grid.size) + (2 * grid[0].size) - 4
    }

    fun part2(input: List<String>): Int {
        val grid = parseInput(input)
        return (1 until grid.size - 1).maxOf { y ->
            (1 until grid[y].size - 1).maxOf { x ->
                grid.scoreAt(x, y)
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private fun parseInput(input: List<String>): Array<IntArray> =
    input.map { row -> row.map(Char::digitToInt).toIntArray() }.toTypedArray()

private fun Array<IntArray>.viewFrom(x: Int, y: Int): List<List<Int>> =
    listOf(
        (y - 1 downTo 0).map { this[it][x] },
        (y + 1 until this.size).map { this[it][x] },
        this[y].take(x).asReversed(),
        this[y].drop(x + 1)
    )

private fun Array<IntArray>.isVisible(x: Int, y: Int): Boolean =
    viewFrom(x, y).any { direction ->
        direction.all { it < this[y][x] }
    }

private fun Array<IntArray>.scoreAt(x: Int, y: Int): Int =
    viewFrom(x, y).map { direction ->
        direction.takeUntil { it >= this[y][x] }.count()
    }.product()