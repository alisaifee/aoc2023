import kotlin.text.Regex

fun main() {
    val numbers =
        mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )
    val expr = Regex("(?=(${numbers.keys.joinToString("|")}|${numbers.values.joinToString("|")}))")

    fun part1(input: List<String>): Int = input.map { it.filter(Char::isDigit) }.sumOf { "${it.first()}${it.last()}".toInt() }

    fun part2(input: List<String>): Int =
        input
            .map { expr.findAll(it).map { it.groupValues[1] }.map { numbers.getOrElse(it) { it } } }
            .sumOf { "${it.first()}${it.last()}".toInt() }
    check(part1(readInput("samples/01")) == 142)
    check(part2(readInput("samples/01_2")) == 281)

    val input = readInput("inputs/01")
    part1(input).println()
    part2(input).println()
}
