const val NUMBER_OF_DAYS_TO_SIMULATE = 256

fun main() {
    var lanternfishes = "day6"
        .getInputLines()
        .first()
        .split(",")
        .map { it.toInt() }
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }

    for (i in 0 until NUMBER_OF_DAYS_TO_SIMULATE) {
        lanternfishes = lanternfishes.simulateLateFishDay()
    }

    println("Number of fishes after $NUMBER_OF_DAYS_TO_SIMULATE days is: ${lanternfishes.values.sum()}")
}


fun Map<Int, Long>.simulateLateFishDay(): Map<Int, Long> {
    val numberOfNewKids = this[0] ?: 0
    val newFishesMap = mutableMapOf<Int, Long>()
    for(i in 0..7){
        newFishesMap[i] = this[i + 1] ?: 0
    }
    newFishesMap[8] = numberOfNewKids
    newFishesMap[6] =  numberOfNewKids + (newFishesMap[6] ?: 0)
    return newFishesMap
}