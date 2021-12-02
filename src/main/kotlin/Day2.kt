fun main(){
    task1()
    task2()
}

private fun task1() {
    var (depth, y) = 0 to 0
    getInputInstructions().forEach {
        val (direction, unit) = it
        when (direction) {
            "forward" -> y += unit
            "up" -> depth -= unit
            "down" -> depth += unit
        }
    }
    println("depth = $depth and y = $y, depth * y= ${depth * y}")
}

private fun task2() {
    var (depth, y, aim) = Triple(0, 0, 0)
    getInputInstructions().forEach {
        val (direction, unit) = it
        when (direction) {
            "forward" -> {
                y += unit
                depth += aim * unit
            }
            "up" -> aim -= unit
            "down" -> aim += unit
        }
    }
    println("depth = $depth and y = $y, depth * y= ${depth * y}")
}

private fun getInputInstructions() = "day2".getInputLines().map { it.parseInstructions() }
private fun String.parseInstructions(): Pair<String, Int> {
    val values = this.split(" ")
    return values[0] to values[1].toInt()
}