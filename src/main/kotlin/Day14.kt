const val NUMBER_OF_ITERATION = 40

val input = "day14".getInputLines()
val polymer = input.first()
val polymerTemplate = input.drop(2).map { it.split(" -> ") }.associate { it.first() to it.last() }

fun main() {
    val polymerCounts = polymer.mutateFast(NUMBER_OF_ITERATION)
    val counts = mutableMapOf<String, Long>()
    polymerCounts.forEach {
        val first = it.key.first().toString()
        counts[first] = counts.getOrZero(first) + it.value
    }
    counts[polymer.last().toString()] = counts.getOrZero(polymer.last().toString()) + 1
    val sortedCounts = counts.toList().sortedBy { (_, value) -> value }
    print(sortedCounts.last().second - sortedCounts.first().second)
}

private fun String.mutate(numberOfIteration: Int): String {
    var polymer = this
    (1..numberOfIteration).forEach { i ->
        polymer = polymer.mutate()
        print("Step $i")
    }
    return polymer
}

private fun String.mutateFast(numberOfIteration: Int): Map<String, Long> {
    var polymer = this.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }
    (1..numberOfIteration).forEach { i ->
        polymer = polymer.mutateFast()
    }
    return polymer
}

private fun String.mutate(): String {
    return this.windowed(2).map { it.first() + polymerTemplate[it]!! }.joinToString(separator = "") { it } + this.last()
}

private fun Map<String, Long>.mutateFast(): Map<String, Long> {
    val newPolymer = mutableMapOf<String, Long>()
    this.forEach {
        val first = it.key.first().toString()
        val last = it.key.last().toString()
        val new = polymerTemplate[it.key]!!
        newPolymer[first + new] = newPolymer.getOrZero(first + new) + this.getOrZero(first + last)
        newPolymer[new + last] = newPolymer.getOrZero(new + last) + this.getOrZero(first + last)
    }
    return newPolymer
}

private fun Map<String, Long>.getOrZero(key: String) = this.getOrDefault(key, 0)
