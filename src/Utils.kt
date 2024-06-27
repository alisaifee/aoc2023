import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String) = Path("$name.txt").readLines().filter { it.isNotBlank() }

fun Any?.println() = println(this)
