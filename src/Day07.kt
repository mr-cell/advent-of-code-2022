fun main() {

    fun getDirTree(input: List<String>): Node {
        val dirTree = Node()
        var context = Context(currentDir = dirTree)

        input.forEach { row ->
            if (row.startsWith("$ cd")) {
                val arg = row.split(" ").last()
                if (arg == "..") {
                    context = context.currentDir.parent?.let { Context(it) } ?: context
                } else if (arg == "/") {
                    context = Context(dirTree)
                } else {
                    context = context.currentDir.children.first { it.name == arg }.let { Context(it) }
                }
            } else if (row.startsWith("$ ls")) {
                // ignore
            } else if (row.startsWith("dir")) {
                val arg = row.split(" ").last()
                context.currentDir.children.add(Node(context.currentDir, arg))
            } else {
                val fileInfo = row.split(" ")
                val fileName = fileInfo.last()
                val fileSize = fileInfo.first().toInt()
                context.currentDir.children.add(Node(context.currentDir, fileName, fileSize))
            }
        }
        dirTree.calculateSize()
        return dirTree
    }

    fun part1(input: List<String>): Int {
        val dirTree = getDirTree(input)
        return  dirTree.getAllDirs().map { it.size }.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val dirTree = getDirTree(input)
        val usedSpace = dirTree.size
        return dirTree.getAllDirs().sortedBy { it.size }.first { (70000000 - usedSpace) + it.size >= 30000000 }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private data class Context(val currentDir: Node)

private class Node(val fullPath: String = "/", val parent: Node? = null, var size: Int = 0) {
    val children = mutableListOf<Node>()
    val name = fullPath.split("/").lastOrNull { it.isNotBlank() } ?: "/"

    constructor(parent: Node, name: String, size: Int = 0) : this("${parent.fullPath}${name}/", parent, size)

    fun isLeaf(): Boolean = children.size == 0

    fun calculateSize() {
        children.forEach { it.calculateSize() }
        size += children.sumOf { it.size }
    }

    fun getAllDirs(): List<Node> {
        val childrenDirs = children.filter { !it.isLeaf() }.flatMap { it.getAllDirs() }
        return listOf(this, *childrenDirs.toTypedArray())
    }
}