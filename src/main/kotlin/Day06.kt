import Direction.*

fun main() {
    var guard = Guard(0 to 0, NORTH)
    val obstacles = mutableSetOf<Pos>()
    val input = getInput("day6").readText().lines()
    input.withIndex().forEach { (y, line) ->
        line.withIndex().forEach { (x, c) ->
            when (c) {
                '^' -> guard = Guard(x to y, NORTH)
                '#' -> obstacles.add(x to y)
            }
        }
    }
    val m = input[0].length
    val n = input.size

    val (visitedPositions, _, _) = walk(guard, obstacles, m, n)
    println(visitedPositions)

    val (_, _, obstaclePositionsCreatingLoop) = walk(guard, obstacles, m, n, true)
    println(obstaclePositionsCreatingLoop)
}

fun walk(initialGuard: Guard, obstacles: Set<Pos>, m: Int, n: Int, createObstacle: Boolean = false): Triple<Int, Boolean, Int> {
    val visitedPositions = mutableSetOf<Guard>()
    val obstaclePositionsCreatingLoop = mutableSetOf<Pos>()
    var guard = initialGuard
    while (guard.pos.first in 0 until m && guard.pos.second in 0 until n && guard !in visitedPositions) {
        visitedPositions.add(guard)
        val forward = guard.direction.move(guard.pos)
        if (forward in obstacles) {
            guard = guard.turnRight()
        } else {
            if (createObstacle && forward != initialGuard.pos && forward !in visitedPositions.map { it.pos }) {
                val (_, loop, _) = walk(guard, obstacles.plus(forward), m, n)
                if (loop){
                    obstaclePositionsCreatingLoop.add(forward)
                }
            }
            guard = guard.move()
        }
    }
    return Triple(visitedPositions.map { it.pos }.toSet().size, guard in visitedPositions, obstaclePositionsCreatingLoop.size)
}

data class Guard(val pos: Pos, val direction: Direction) {
    fun turnRight() = Guard(this.pos, this.direction.turnRight())

    fun move() = Guard(this.direction.move(this.pos), this.direction)
}