import kotlin.math.max

class Pick(
    val red: Int,
    val blue: Int,
    val green: Int,
)

fun main() {
    fun parseInput(input: List<String>): Map<Int, List<Pick>> =
        input.associate { line ->
            val (game, details) = line.split(":")
            val gameId = game.split(" ").last().toInt()
            val gamePicks =
                details.split(";").map { pick ->
                    val colorMap =
                        pick.split(",").associate { p ->
                            val (num, color) = p.trim().split(" ")
                            color to num.toInt()
                        }
                    Pick(
                        red = colorMap["red"] ?: 0,
                        green = colorMap["green"] ?: 0,
                        blue = colorMap["blue"] ?: 0,
                    )
                }
            gameId to gamePicks
        }

    fun part1(input: Map<Int, List<Pick>>): Int =
        input
            .filter { game ->
                game.value.none { pick ->
                    pick.red > 12 || pick.green > 13 || pick.blue > 14
                }
            }.map { it.key }
            .sum()

    fun part2(input: Map<Int, List<Pick>>): Int =
        input
            .map { (_, picks) ->
                picks
                    .fold(Triple(0, 0, 0)) { curMax, pick ->
                        Triple(max(curMax.first, pick.red), max(curMax.second, pick.green), max(curMax.third, pick.blue))
                    }.toList()
                    .reduce(Int::times)
            }.sum()

    check(part1(parseInput(readInput("samples/02"))) == 8)
    check(part2(parseInput(readInput("samples/02"))) == 2286)

    val input = parseInput(readInput("inputs/02"))
    part1(input).println()
    part2(input).println()
}
