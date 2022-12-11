fun main() {

    fun performInstructions(instructions: List<Instruction>, stacks: Stacks, reverse: Boolean) {
        instructions.forEach { (howMany, from, to) ->
            val crates = stacks[from].take(howMany)
            repeat(howMany) { stacks[from].removeFirst() }
            stacks[to].addAll(0, if (reverse) crates.reversed() else crates)
        }
    }

    fun part1(input: List<String>): String {
        val stacks = readStacks(input)
        val instructions = readInstructions(input)
        performInstructions(instructions, stacks, true)
        return stacks.tops()
    }

    fun part2(input: List<String>): String {
        val stacks = readStacks(input)
        val instructions = readInstructions(input)
        performInstructions(instructions, stacks, false)
        return stacks.tops()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun List<MutableList<Char>>.tops(): String = this.map { it.first() }.joinToString("")

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