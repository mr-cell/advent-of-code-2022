import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Int {
        val instructions = parseInput(input)
        return CpuRegister(instructions)
            .executeUpTo(220)
            .let {values ->
                (20..220 step 40).sumOf { values[it - 1] * it  }
            }
    }

    fun part2(input: List<String>) {
        val instructions = parseInput(input)
        val register = CpuRegister(instructions)
        val registerValues = register.executeUpTo(240)
        (0 until 6).forEach { y ->
            val line = (0 until 40).joinToString("") { x ->
                val drawCycle = x + (40 * y)
                if ((registerValues[drawCycle] - x).absoluteValue <= 1) {
                    "#"
                } else {
                    "."
                }
            }
            println(line)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    part2(testInput)

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}

private fun parseInput(input: List<String>): List<CpuInstruction> {
    return input.map { instruction ->
        if (instruction.startsWith("addx")) {
            CpuInstruction(instruction.split(" ").last().toInt(), 2)
        } else {
            CpuInstruction(0, 1)
        }
    }
}

private class CpuRegister(private val instructions: List<CpuInstruction>) {

    var value = 1
        private set

    val seq = sequence<Int> {
        instructions.forEach {instruction ->
            (1..instruction.cycleDuration).forEach { _ ->
                yield(value)
            }
            value += instruction.registerIncrement
        }
    }

    fun executeUpTo(cycles: Int): List<Int> {
        return seq.take(cycles).toList()
    }
}

private data class CpuInstruction(val registerIncrement: Int, val cycleDuration: Int)