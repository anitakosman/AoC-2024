import java.io.File
import kotlin.math.sign

fun getInput(fileName: String): File {
    return File(object {}.javaClass.getResource("/${fileName}.txt")!!.file)
}

val lineSeparator: String = System.lineSeparator()

enum class Direction(private val dx: Int, private val dy: Int) {
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

    fun move(pos: Pos) = (pos.x + dx) to (pos.y + dy)

    fun moveBack(pos: Pos) = (pos.x - dx) to (pos.y - dy)

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

typealias Pos = Pair<Int, Int>
val Pos.x: Int
    get() = first
val Pos.y: Int
    get() = second

typealias Dir = Pair<Int, Int>
val Dir.dx: Int
    get() = first
val Dir.dy: Int
    get() = second

data class Rational(val nominator: Int, val denominator: Int = 1) {
    fun simplify(): Rational {
        val gcd = gcd(nominator, denominator)
        return Rational(nominator * denominator.sign / gcd, denominator * denominator.sign / gcd)
    }

    operator fun times(x: Int) = Rational(nominator * x, denominator).simplify()

    operator fun plus(other: Rational): Rational {
        val lcm = lcm(this.denominator, other.denominator)
        return Rational(this.nominator * lcm / this.denominator + other.nominator * lcm / other.denominator, lcm).simplify()
    }

    operator fun minus(other: Rational) = this + Rational(-other.nominator, other.denominator)
}

operator fun Int.times(rational: Rational) = rational.times(this)

fun gcd(x: Int, y: Int) = x.toBigInteger().gcd(y.toBigInteger()).toInt()

fun lcm(x: Int, y: Int) = ((x * y) / gcd(x, y))
