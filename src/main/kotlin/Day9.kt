fun main() {
    val heightmap = "day9".getInputLines().map { line -> line.map { Character.getNumericValue(it) } }
    val lowPoints = mutableListOf<Int>()
    val basinSizes = mutableListOf<Int>()
    for (i in heightmap.indices) {
        for (j in heightmap.first().indices) {
            if (heightmap.isLowPoint(i, j)) {
                lowPoints.add(heightmap[i][j])
                basinSizes.add(
                    heightmap.findBasinPoints(i, j).size
                )
            }
        }
    }

    println("Sum of the risk level is ${lowPoints.sumOf { it + 1 }}")

    val resultPart2 = basinSizes.sortedDescending().take(3).reduce { a, b -> a * b }
    println("Multiply of 3 largest basin sizes is $resultPart2")
}

private fun List<List<Int>>.isLowPoint(i: Int,
                                       j: Int) =
    (i == 0 || this[i - 1][j] > this[i][j])
            && (i == this.lastIndex || this[i + 1][j] > this[i][j])
            && (j == this.first().lastIndex || this[i][j + 1] > this[i][j])
            && (j == 0 || this[i][j - 1] > this[i][j])


private fun List<List<Int>>.findBasinPoints(i: Int,
                                            j: Int,
                                            basinPoints: Set<Pair<Int, Int>> = emptySet()): MutableSet<Pair<Int, Int>> {
    var resultBasinPoints = basinPoints.toMutableSet()
    resultBasinPoints.add(i to j)
    if (i != 0 && this[i - 1][j] != 9 && this[i - 1][j] > this[i][j]) {
        resultBasinPoints = findBasinPoints(i - 1, j, resultBasinPoints)
    }
    if (j != 0 && this[i][j - 1] != 9 && this[i][j - 1] > this[i][j]) {
        resultBasinPoints = findBasinPoints(i, j - 1, resultBasinPoints)
    }
    if (i != lastIndex && this[i + 1][j] != 9 && this[i + 1][j] > this[i][j]) {
        resultBasinPoints = findBasinPoints(i + 1, j, resultBasinPoints)
    }
    if (j != first().lastIndex && this[i][j + 1] != 9 && this[i][j + 1] > this[i][j]) {
        resultBasinPoints = findBasinPoints(i, j + 1, resultBasinPoints)
    }
    return resultBasinPoints
}