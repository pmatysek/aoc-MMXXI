fun main() {
    val input = "day3".getInputLines()
    val frequencies = input.first().indices.map { input.findFrequenciesAt(it) }

    val gammaNumber = frequencies.joinToString("") { it.first }.toInt(2)
    val epsilonNumber = frequencies.joinToString("") { it.second }.toInt(2)
    println("gamma = $gammaNumber, epsilon = $epsilonNumber, gamma * epsilon = ${gammaNumber * epsilonNumber}")

    val oxygenGRating = input.findRating(true)
    val Co2SRAting = input.findRating(false)
    println("oxygenGRating = $oxygenGRating; Co2SRAting = $Co2SRAting; Co2SRAting * oxygenGRating = ${oxygenGRating * Co2SRAting}")
}

private fun List<String>.findFrequenciesAt(index: Int): Pair<String, String> {
    val charsFrequency = this.groupingBy { it[index] }.eachCount()
    return if (charsFrequency['1']!! >= charsFrequency['0']!!) {
        "1" to "0"
    } else {
        "0" to "1"
    }
}

fun List<String>.findRating(mostFrequent: Boolean): Int {
    var reduced = this
    for (i in this[0].indices) {
        val frequencies = reduced.findFrequenciesAt(i)
        reduced = reduced.filter { frequencies.compareFrequencies(mostFrequent, it[i].toString()) }
        if (reduced.size == 1) {
            return reduced.single().toInt(2)
        }
    }
    return -1
}

private fun Pair<String, String>.compareFrequencies(mostFrequent: Boolean,
                                                    char: String) =
    (mostFrequent && char == first) || (!mostFrequent && char == second)