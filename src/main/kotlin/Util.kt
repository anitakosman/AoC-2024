import java.io.File

fun getInput(fileName: String): File {
    return File(object {}.javaClass.getResource("/${fileName}.txt")!!.file)
}

val lineSeparator: String = System.lineSeparator()

enum class Facing(private val dx: Int, private val dy: Int) {
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0),
    NORTH(0, -1),
    NORTHEAST(1, -1),
    SOUTHEAST(1, 1),
    SOUTHWEST(-1, 1),
    NORTHWEST(-1, -1);

    companion object {
        val orthogonals = listOf(EAST, SOUTH, WEST, NORTH)
        val diagonals = listOf(NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST)
    }

    fun move(pos: Pair<Int, Int>) = (pos.first + dx) to (pos.second + dy)

    fun moveBack(pos: Pair<Int, Int>) = (pos.first - dx) to (pos.second - dy)

    fun turnLeft() = when (this) {
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
        NORTH -> WEST
        NORTHEAST -> NORTHWEST
        SOUTHEAST -> NORTHEAST
        SOUTHWEST -> SOUTHEAST
        NORTHWEST -> SOUTHWEST
    }

    fun turnRight() = when (this) {
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
        NORTH -> EAST
        NORTHEAST -> SOUTHEAST
        SOUTHEAST -> SOUTHWEST
        SOUTHWEST -> NORTHWEST
        NORTHWEST -> NORTHEAST
    }
}