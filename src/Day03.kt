import kotlin.math.abs
import kotlin.text.Regex

val NUMEXPR = Regex("(\\d+)")
val SYMBOLEXPR = Regex("[^0-9.]")

class Entry(
    val value: String,
    val x: Int,
    val y: Int,
) {
    private data class Cell(
        val x: Int,
        val y: Int,
    )

    fun isAdjacent(other: Entry): Boolean =
        cells.any { cell ->
            other.cells.any { otherCell ->
                abs(cell.x - otherCell.x) <= 1 && abs(cell.y - otherCell.y) <= 1
            }
        }

    private val cells: List<Cell> by lazy {
        List(value.length) { Cell(x + it, y) }
    }
}

class Schema(
    input: List<String>,
) {
    val partNumbers: List<Entry> by lazy {
        input
            .mapIndexed { y, line ->
                NUMEXPR
                    .findAll(line)
                    .map { num ->
                        Entry(num.value, num.range.first, y)
                    }.toList()
            }.flatten()
    }
    val symbols: List<Entry> by lazy {
        input
            .mapIndexed { y, line ->
                SYMBOLEXPR
                    .findAll(line)
                    .map { sym ->
                        Entry(sym.value, sym.range.first, y)
                    }.toList()
            }.flatten()
    }
}

fun main() {
    fun parseInput(input: List<String>): Schema = Schema(input)

    fun part1(input: Schema): Int =
        input.partNumbers
            .filter { partNumber ->
                input.symbols
                    .any { symbol ->
                        partNumber.isAdjacent(symbol)
                    }
            }.sumOf { it.value.toInt() }

    fun part2(input: Schema): Int =
        input.symbols
            .filter { it.value == "*" }
            .flatMap { gear ->
                input.partNumbers
                    .filter { gear.isAdjacent(it) }
                    .takeIf { it.size == 2 }
                    ?.let { listOf(it.map { part -> part.value.toInt() }.reduce(Int::times)) }
                    ?: emptyList()
            }.sum()

    check(part1(parseInput(readInput("samples/03"))) == 4361)
    check(part2(parseInput(readInput("samples/03"))) == 467835)

    val input = parseInput(readInput("inputs/03"))
    part1(input).println()
    part2(input).println()
}
