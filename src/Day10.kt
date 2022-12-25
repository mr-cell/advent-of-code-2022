import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Int {
        val signals = parseInput(input).runningReduce(Int::plus)
        return signals.sampleSignals().sum()
    }

    fun part2(input: List<String>) {
        val signals = parseInput(input).runningReduce(Int::plus)
        signals.screen().print()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    part2(testInput)

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}

private fun parseInput(input: List<String>): List<Int> {
    return buildList {
        add(1)
        input.forEach { line ->
            add(0)
            if (line.startsWith("addx")) {
                add(line.substringAfter(" ").toInt())
            }
        }
    }
}

private fun List<Int>.sampleSignals(): List<Int> =
    (60.. size step 40).map { cycle ->
        cycle * this[cycle - 1]
    } + this[19] * 20

private fun List<Int>.screen(): List<Boolean> =
    this.mapIndexed { pixel, signal ->
        (signal - (pixel % 40)).absoluteValue <= 1
    }

private fun List<Boolean>.print() {
    this.windowed(40, 40, false).forEach { row ->
        row.forEach { pixel ->
            print(if(pixel) '#' else ' ')
        }
        println()
    }
}