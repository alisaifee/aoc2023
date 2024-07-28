import kotlin.math.pow

fun main() {
    fun parseInput(input: List<String>): List<Pair<Set<Int>, List<Int>>> =
        input.map { line ->
            val (winners, values) =
                line
                    .split(":")[1]
                    .split("|")
                    .map {
                        it
                            .split(" ")
                            .filter { it.isNotBlank() }
                            .map { it.trim().toInt() }
                    }
            winners.toSet() to values
        }

    fun part1(input: List<Pair<Set<Int>, List<Int>>>): Int =
        input
            .map { card ->
                2.toDouble().pow(card.first.intersect(card.second).size - 1).toInt()
            }.sum()

    fun part2(input: List<Pair<Set<Int>, List<Int>>>): Int {
        val counter = List(input.size) { it }.associateWith { idx -> 1 }.toMutableMap()
        input.forEachIndexed { idx, card ->
            (1..card.first.intersect(card.second).size).forEach { n -> counter[idx + n] = (counter[idx + n] ?: 1) + (counter[idx] ?: 1) }
        }
        return counter.values.sum()
    }

    check(part1(parseInput(readInput("samples/04"))) == 13)
    check(part2(parseInput(readInput("samples/04"))) == 30)

    val input = parseInput(readInput("inputs/04"))
    part1(input).println()
    part2(input).println()
}
