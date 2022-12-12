fun main() {

    fun part1(input: List<String>): Int {
        val rootDir = parseInput(input)
        return rootDir.find { it.size <= 100_000 }.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val rootDir = parseInput(input)
        val unusedSpace = 70_000_000 - rootDir.size
        val deficit = 30_000_000 - unusedSpace
        return rootDir.find { it.size >= deficit }.minBy { it.size }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun parseInput(input: List<String>): Directory {
    val callStack = ArrayDeque<Directory>().apply { add(Directory("/")) }
    input.forEach { item ->
        when {
            item == "$ ls" -> {} // no-op
            item.startsWith("dir") -> {} // no-op
            item == "$ cd /" -> callStack.removeIf { it.name != "/" }
            item == "$ cd .." -> callStack.removeFirst()
            item.startsWith("$ cd") -> {
                val name = item.substringAfterLast(" ")
                callStack.addFirst(callStack.first().traverse(name))
            }
            else -> {
                val size = item.substringBefore(" ").toInt()
                callStack.first().addFile(size)
            }
        }
    }
    return callStack.last()
}

private class Directory(val name: String) {
    private val children: MutableMap<String, Directory> = mutableMapOf()
    private var sizeOfFiles: Int = 0

    val size: Int
        get() = sizeOfFiles + children.values.sumOf { it.size }

    fun addFile(size: Int) {
        sizeOfFiles += size
    }

    fun traverse(dir: String): Directory = children.getOrPut(dir) { Directory(dir) }

    fun find(predicate: (Directory) -> Boolean): List<Directory> =
        children.values.filter(predicate) + children.values.flatMap { it.find(predicate) }
}