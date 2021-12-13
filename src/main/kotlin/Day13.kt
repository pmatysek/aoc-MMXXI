fun main() {
    val points = "day13".getInputLines()
        .map { it.split(",") }
        .map { it.first().toInt() to it.last().toInt() }
        .toSet()

    val folds = "day13_folds".getInputLines()
        .map {
            it.split(" ").last().split("=")
        }.map {
            it.first() to it.last().toInt()
        }
    val fold = folds.first()
    println("${points.fold(fold).size}")

    var foldedPoints = points
    folds.forEach { fold ->
        foldedPoints = foldedPoints.fold(fold)
    }
    foldedPoints.print()

}

private fun Set<Pair<Int, Int>>.print() {
   val maxX = this.maxOf { it.first }
   val maxY = this.maxOf { it.second }

    for(y in 0..maxY){
        for(x in 0..maxX){
            if((x to y) in this) {
                print("***")
            } else {
                print("   ")
            }
        }
        print("\n")
    }
}

private fun Set<Pair<Int, Int>>.fold(fold: Pair<String, Int>): Set<Pair<Int, Int>> {
    val (half1, half2) = this.splitByFold(fold)

    half2.forEach {
        half1.add(it.fold(fold))
    }
    return half1
}

private fun Set<Pair<Int, Int>>.splitByFold(fold: Pair<String, Int>) =
    when (fold.first) {
        "y" -> this.filter { it.second < fold.second }.toMutableSet() to this.filter { it.second > fold.second }
            .toMutableSet()
        "x" -> this.filter { it.first < fold.second }.toMutableSet() to this.filter { it.first > fold.second }
            .toMutableSet()
        else -> throw UnsupportedOperationException()
    }


private fun Pair<Int, Int>.fold(fold: Pair<String, Int>) =
    when (fold.first) {
        "y" -> this.first to 2 * fold.second - this.second
        "x" -> 2 * fold.second - this.first to this.second
        else -> throw UnsupportedOperationException()
    }