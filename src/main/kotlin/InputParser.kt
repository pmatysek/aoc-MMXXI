import java.io.File

fun String.getInputLines() = File("src/main/resources/$this.input").readLines()