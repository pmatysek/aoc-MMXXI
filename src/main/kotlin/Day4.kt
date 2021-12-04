typealias Board = List<List<Int>>

fun main() {
    val input = "day4".getInputLines()

    val bingoNumbers = input.first().split(",").map { it.toInt() }
    var boards = input.getBingoBoards()
    val winnersScores = mutableListOf<Int>()

    for (number in bingoNumbers) {
        boards = boards.markNumber(number)
        val winners = boards.findWinners()
        if (winners.isNotEmpty()) {
            winnersScores.addAll(winners.map { boards[it].sumOfUnmarked() * number })
            boards.removeWinners(winners)
        }
    }

    println("First winner score: ${winnersScores.first()}")
    println("Last winner score: ${winnersScores.last()}")
}

private fun MutableList<Board>.removeWinners(winners: MutableList<Int>) {
    val winnerBoards = winners.associateWith { this[it] }
    winners.forEach { this.remove(winnerBoards[it]) }
}

private fun Board.sumOfUnmarked() = this.flatten().filter { it != -1 }.sum()

private fun MutableList<Board>.findWinners(): MutableList<Int> =
    this.indices.filter { this[it].isWinner() }.toMutableList()

private fun Board.isWinner() = isRowsWinner() || this.transpose().map { row -> row.map { it ?: -1 } }.isRowsWinner()

private fun Board.isRowsWinner() = this.any { row -> row.all { it == -1 } }

private fun MutableList<Board>.markNumber(number: Int) =
    this.map { board -> board.map { it.markNumberInRow(number) } }.toMutableList()

private fun List<Int>.markNumberInRow(number: Int) = this.map { if (it == number) -1 else it }

private fun List<String>.getBingoBoards(): MutableList<Board> =
    this
        .drop(1)
        .chunked(6)
        .map { boardLines ->
            boardLines.filter { line -> line.isNotBlank() }
                .map { line ->
                    line
                        .split(" ")
                        .filter { it.isNotBlank() }
                        .map { it.toInt() }
                }
        }.toMutableList()