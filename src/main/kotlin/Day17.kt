import kotlin.math.abs

fun main() {
    val input = "day17".getInputLines().first().split(",").map { it.split("..") }.flatten().map { it.toInt() }
    val maxTrajectory = input.map { abs(it) }.maxOf { it }
    val xTarget = input[0]..input[1]
    val yTarget = input[2]..input[3]
    val trajectories = findAllTrajectories(maxTrajectory).filterValues { trajectory ->
        trajectory.any { point ->
            point.first in xTarget && point.second in yTarget
        }
    }
    val maxY = trajectories.values.maxOf { trajectory -> trajectory.maxOf { it.second } }
    println("maxY = $maxY")
    println("velocities = ${trajectories.size}")

}

fun findAllTrajectories(maxTrajectory: Int): Map<Pair<Int, Int>, List<Pair<Int, Int>>> {
    val trajectories = mutableMapOf<Pair<Int, Int>, List<Pair<Int, Int>>>()
    (-maxTrajectory..maxTrajectory).forEach() { i ->
        (-maxTrajectory..maxTrajectory).forEach { j ->
            trajectories[i to j] = (i to j).findTrajectory(maxTrajectory)
        }
    }

    return trajectories
}

fun Pair<Int, Int>.findTrajectory(maxTrajectory: Int): List<Pair<Int, Int>> {
    val trajectory = mutableListOf<Pair<Int, Int>>()
    var x = 0
    var y = 0
    var xVelocity = this.first
    var yVelocity = this.second
    (-maxTrajectory..maxTrajectory).forEach { _ ->
        trajectory.add(x to y)
        x += xVelocity
        y += yVelocity
        xVelocity = when {
            xVelocity > 0 -> xVelocity - 1
            xVelocity < 0 -> xVelocity + 1
            else -> 0
        }
        yVelocity -= 1
    }

    return trajectory
}