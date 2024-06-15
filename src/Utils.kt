import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String) = Path("$name.txt").readLines()

fun Any?.println() = println(this)
