import kotlin.math.abs

data class Point(val x: Int, val y: Int)
data class Line(val startPoint: Point, val endPoint: Point)

fun main() {
    val lines = getInput()
    val nonDiagonalLines = lines.filter { it.isNotDiagonal() }

    println("Number of points covered at least two times (non diagonal lines only): ${nonDiagonalLines.countNumberOfPointsCoveredAtLeastTwoTimes()}")
    println("Number of points covered at least two times: ${lines.countNumberOfPointsCoveredAtLeastTwoTimes()}")

}

private fun List<Line>.countNumberOfPointsCoveredAtLeastTwoTimes() =
    this
        .map { it.getAllPoints() }
        .flatten()
        .groupingBy { it }
        .eachCount()
        .count {
            it.value >= 2
        }

private fun Line.isNotDiagonal() = startPoint.x == endPoint.x || startPoint.y == endPoint.y

fun Line.getAllPoints(): List<Point> {
    if (startPoint.x == endPoint.x) {
        return getYRange().map { y -> Point(startPoint.x, y) }
    }

    if (startPoint.y == endPoint.y) {
        return getXRange().map { x -> Point(x, startPoint.y) }
    }
    val dY = dY()
    val direction = dY.getDirection()

    return (0..dX()).map {
        Point(startPoint.x + it, startPoint.y + direction * it)
    }
}

fun getInput(): List<Line> =
    "day5".getInputLines().map {
        it.split(" -> ").map { point ->
            point.split(",").map { coordinate ->
                coordinate.toInt()
            }.toPoint()
        }.toLine()
    }

fun Line.getYRange() = startPoint.y..endPoint.y
fun Line.getXRange() = startPoint.x..endPoint.x
fun Line.dX() = getXRange().getDelta()
fun Line.dY() = getYRange().getDelta()
fun Int.getDirection() = if (this == 0) 0 else this / abs(this)
fun IntRange.getDelta() = last - first


fun List<Int>.toPoint() = Point(first(), last())
fun List<Point>.toLine() =
    when {
        first().x < last().x -> Line(first(), last())
        first().x == last().x && first().y < last().y -> Line(first(), last())
        else -> Line(last(), first())
    }
