fun main() {
    fun part1(input: List<String>): String {
        val stacks = readStacks(input)
        val instructions = readInstructions(input)

        instructions.forEach { (howMany, from, to) ->
            (1..howMany).forEach { _ ->
                val crate = stacks[from].removeAt(0)
                stacks[to].add(0, crate)
            }
        }

        return stacks.map { it[0] }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val stacks = readStacks(input)
        val instructions = readInstructions(input)

        instructions.forEach { (howMany, from, to) ->
            val crates = (1..howMany).map {
                stacks[from].removeAt(0)
            }

            stacks[to].addAll(0, crates)
        }

        return stacks.map { it[0] }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun readStacks(input: List<String>): Stacks {
    val stackRows = input.takeWhile { it.contains("[") }
    return (1..stackRows.last().length step 4).map { index ->
            stackRows.mapNotNull { it.getOrNull(index) }
                .filter { it.isUpperCase() }
                .toMutableList()
    }
}

private fun readInstructions(input: List<String>): List<Instruction> {
    return input.dropWhile { !it.startsWith("move") }
        .map { it.split(" ") }
        .map { Instruction(it.get(1).toInt(), it.get(3).toInt() - 1, it.get(5).toInt() - 1) }
}

private data class Instruction(val howMany: Int, val from: Int, val to: Int)

typealias Stacks = List<MutableList<Char>>