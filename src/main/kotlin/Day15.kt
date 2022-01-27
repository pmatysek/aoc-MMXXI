import java.lang.Integer.min

var MAX_I = "day15".getInputLines().size - 1
var MAX_J = "day15".getInputLines().first().length - 1

fun main() {
    val input = "day15".getInputLines().map { line -> line.map { Character.getNumericValue(it) } }
        .flatMapIndexed { i, list ->
            list.mapIndexed { j: Int, value: Int -> (i to j) to value }
        }.toMap().toMutableMap()
    //val lowestRisk = input.findTheLowestRisk(0, 0)
    val riskTable = input.computeRiskTable()
    println("Lowest risk: ${riskTable[0 to 0]}")

    val input2 = input.toMutableMap()
    input.forEach { entry ->
        (0..4).forEach { i ->
            (0..4).forEach { j ->
                input2[i * (MAX_I + 1) + entry.key.first to j * (MAX_J + 1) + entry.key.second] =
                    entry.calculateNewValue(i, j)
            }
        }
    }
    val i = input2.maxByOrNull { it.key.first }!!.key.first
    val j = input2.maxByOrNull { it.key.second }!!.key.second
    MAX_I += 4 * (MAX_I + 1)
    MAX_J += 4 * (MAX_J + 1)


    val riskTable2 = input2.computeRiskTable()
    println("Lowest risk: ${riskTable2[0 to 0]}")
}

private fun Map.Entry<Pair<Int, Int>, Int>.calculateNewValue(i: Int,
                                                             j: Int): Int {
    val result = i + j + value
    return if (result > 9) (result % 10) + 1 else result

}


fun Map<Pair<Int, Int>, Int>.findTheLowestRisk(i: Int, j: Int, risk: Int = 0): Int {
    val newRisk = risk + this[i to j]!!
    return when {
        i + 1 == MAX_I && j + 1 == MAX_J -> newRisk
        i + 1 == MAX_I && j + 1 < MAX_J -> this.findTheLowestRisk(i, j + 1, newRisk)
        i + 1 < MAX_I && j + 1 == MAX_J -> this.findTheLowestRisk(i + 1, j, newRisk)
        else -> min(this.findTheLowestRisk(i + 1, j, newRisk), this.findTheLowestRisk(i, j + 1, newRisk))
    }
}

fun Map<Pair<Int, Int>, Int>.computeRiskTable(): MutableMap<Pair<Int, Int>, Int?> {
    val riskTable = mutableMapOf((MAX_I to MAX_J) to this[MAX_I to MAX_J])
    for (i in MAX_I downTo 0) {
        for (j in MAX_J downTo 0) {
            //println("$i , $j")
            when {
                j == MAX_J && i == MAX_I -> {}
                j == MAX_J -> riskTable[i to j] = riskTable[i + 1 to j]!! + this[i to j]!!
                i == MAX_I -> riskTable[i to j] = riskTable[i to j + 1]!! + this[i to j]!!
                else -> riskTable[i to j] = min(riskTable[i + 1 to j]!!, riskTable[i to j + 1]!!) + this[i to j]!!
            }
        }
    }
    riskTable[0 to 0] = riskTable[0 to 0]!! - this[0 to 0]!!
    return riskTable
}

fun Map<Pair<Int, Int>, Int>.computeRiskTableDijkstra(): MutableMap<Pair<Int, Int>, Int> {
    val riskTable = mutableMapOf<Pair<Int, Int>, Int>()
    val d = this.mapValues { Int.MAX_VALUE }.toMutableMap()
    d[0 to 0] = 0
    while (riskTable.size < this.size) {
        val u = this.filter { it.key !in riskTable }.minByOrNull { d[it.key]!! }!!
        riskTable.put(u)
        u.getNeighbours().filter { it !in riskTable }.forEach { w ->
            if (d[w]!! > d[u.key]!! + this[w]!!) {
                d[w] = d[u.key]!! + this[w]!!
            }
        }
    }
    return d
}

private fun Map.Entry<Pair<Int, Int>, Int>.getNeighbours(): List<Pair<Int, Int>> {
    val neighbours = mutableListOf<Pair<Int, Int>>()
    if (this.key.first < MAX_I) {
        neighbours.add(this.key.first + 1 to this.key.second)
    }
    if (this.key.first > 0) {
        neighbours.add(this.key.first - 1 to this.key.second)
    }
    if (this.key.second < MAX_J) {
        neighbours.add(this.key.first to this.key.second + 1)
    }
    if (this.key.second > 0) {
        neighbours.add(this.key.first to this.key.second - 1)
    }
    return neighbours
}

private fun <K, V> MutableMap<K, V>.put(entry: Map.Entry<K, V>) {
    this[entry.key] = entry.value
}


