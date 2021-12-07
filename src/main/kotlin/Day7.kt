import kotlin.math.abs

fun main() {
    val positions = "day7".getInputLines().first().split(",").map { it.toInt() }
    val fuelSums = (positions.minOrNull()!!..positions.maxOrNull()!!).associateWith { targetPosition ->
        positions.sumOf { currentPosition -> abs(currentPosition - targetPosition) }
    }
    println("They have to spend ${fuelSums.minOf { it.value }} fuel")

    val fuelSums2 = (positions.minOrNull()!!..positions.maxOrNull()!!).associateWith { targetPosition ->
        positions.sumOf { currentPosition -> calculateFuel(currentPosition, targetPosition) }
    }
    println("They have to spend ${fuelSums2.minOf { it.value }} fuel")
}

private fun calculateFuel(currentPosition: Int, targetPosition: Int): Int {
    val n = abs(currentPosition - targetPosition)
    return ((1 + n) * n) / 2
}
