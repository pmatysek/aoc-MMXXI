val openBrackets = mapOf(
    ')' to '(',
    ']' to '[',
    '}' to '{',
    '>' to '<',
)

val enclosingBracketsErrorPoints = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137,
)

val bracketsMissingPoints = mapOf(
    '(' to 1L,
    '[' to 2L,
    '{' to 3L,
    '<' to 4L,
)

fun main() {
    val input = "day10".getInputLines()
    val errors = input.map { it.findError() }.filter { it.isNotEmpty() }
    val errorScore = errors.sumOf { enclosingBracketsErrorPoints[it.first()] ?: 0 }
    println("Error score is: $errorScore")

    val missing = input.map { it.findError(true) }.filter { it.isNotEmpty() }
    val missingScores = missing.map { lineMissings ->
        lineMissings.map { bracketsMissingPoints[it]!! }.reduce { totalScore, score -> totalScore * 5L + score }
    }.sorted()
    println("Missing score is: ${missingScores[(missingScores.size / 2)]}")
}

fun Char.isCloseBracket() = this in openBrackets.keys

fun String.findError(ignoreErrors: Boolean = false): List<Char> {
    val openBracketsFound = ArrayDeque<Char>()
    this.forEach { bracket ->
        if (bracket.isCloseBracket()) {
            if (openBracketsFound.removeFirst() != openBrackets[bracket]) {
                return if (ignoreErrors) emptyList() else listOf(bracket)
            }
        } else {
            openBracketsFound.addFirst(bracket)
        }
    }
    return if (!ignoreErrors) emptyList() else openBracketsFound
}