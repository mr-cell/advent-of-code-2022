import kotlin.math.floor

fun main() {

    fun part1(input: List<String>): Long {
        val monkeys = input.toMonkeys()
        repeat(20) {
            monkeys.forEach { monkey ->
                val transfers = monkey.inspectItems()
                transfers.forEach { transfer -> monkeys[transfer.first].addItem(transfer.second) }
            }
        }

        return monkeys
            .map(Monkey::getInspections)
            .sortedDescending()
            .take(2)
            .reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toMonkeys(): List<Monkey> = this.filterNot { it == "" }.chunked(6).map { it.toMonkey() }

private fun List<String>.toMonkey(): Monkey {
    val startItems = this[1].substringAfter(": ").split(", ").map { it.toLong() }.toMutableList()
    val operationValue = this[2].substringAfterLast(" ")
    val testParameter = this[3].substringAfterLast(" ").toLong()
    val trueMonkey = this[4].substringAfterLast(" ").toInt()
    val falseMonkey = this[5].substringAfterLast(" ").toInt()

    return Monkey(
        items = startItems,
        operation = when {
            operationValue == "old" -> ({ it * it })
            '*' in this[2] -> ({ it * operationValue.toLong() })
            else -> ({ it + operationValue.toLong() })
        },
        test = { i -> (i % testParameter).toInt() == 0 },
        trueMonkey = trueMonkey,
        falseMonkey = falseMonkey
    )
}

class Monkey(
    private val items: MutableList<Long>,
    private val operation: (Long) -> Long,
    private val test: (Long) -> Boolean,
    private val trueMonkey: Int,
    private val falseMonkey: Int
) {
    private var inspections: Long = 0

    fun addItem(item: Long) {
        items.add(item)
    }

    fun inspectItems(): List<Transfer> {
        inspections += items.size
        val transfers = items
            .map(operation)
            .map { floor(it.toDouble().div(3)).toLong() }
            .map {
                if(test.invoke(it)) {
                    Transfer(trueMonkey, it)
                } else {
                    Transfer(falseMonkey, it)
                }
            }
        items.clear()
        return transfers
    }

    fun inspectItemsPart2(): List<Transfer> {
        inspections += items.size
        val transfers = items
            .map(operation)
            .map {
                if(test.invoke(it)) {
                    Transfer(trueMonkey, it)
                } else {
                    Transfer(falseMonkey, it)
                }
            }
        items.clear()
        return transfers
    }

    fun getInspections(): Long = inspections

}

private typealias Transfer = Pair<Int, Long>