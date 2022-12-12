fun main() {

    fun part1(input: List<String>): Int {
        var visibleTrees = 0
        val grid = parseInput(input)
        (grid.indices).forEach { i ->
            (0 until grid[i].size).forEach { j ->
                if (i == 0 || i == grid.size - 1 || j == 0 || j == grid[i].size - 1) {
                    visibleTrees++
                } else {
                    val treeSize = grid[i][j]
                    if ((i + 1 until grid.size).all { k -> grid[k][j] < treeSize }) {
                        visibleTrees++
                    }
                    else if ((i - 1 downTo  0).all { k -> grid[k][j] < treeSize }) {
                        visibleTrees++
                    }
                    else if ((j + 1 until grid[i].size).all { k ->  grid[i][k] < treeSize}) {
                        visibleTrees++
                    }
                    else if ((j - 1 downTo  0).all { k -> grid[i][k] < treeSize }) {
                        visibleTrees++
                    }
                }
            }
        }
        return visibleTrees
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 0)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private fun parseInput(input: List<String>): Array<Array<Int>> =
    input.map { row -> row.split("").filter { it.isNotBlank() }.map { it.toInt() } }
        .map { it.toTypedArray() }
        .toTypedArray()