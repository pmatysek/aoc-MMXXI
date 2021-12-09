fun main() {
    val outPutDigitsSegments = "day8".getInputLines().map { it.split("|").last().trim().split(" ") }.flatten()
    val easyDigitsNumber = outPutDigitsSegments.count {
        it.length in listOf(2, 3, 4, 7)
    }
    println("Easy digits number: $easyDigitsNumber")

    val decodedOutputs = "day8".getInputLines()
        .map { line -> line.split("|").map { it.trim().split(" ") }}.map {
            it.last().decodeBy(it.first().decodeDigits())
        }
    println("Sum of outputs is: ${decodedOutputs.sum()}")
}

fun List<String>.decodeBy(digitsMap: Map<String, String>) =
    this.map {
        digit ->
        digitsMap[digitsMap.findDigitKey(digit)]
    }.joinToString("").toInt()

private fun Map<String, String>.findDigitKey(digit: String) = this.keys.find { it.toSet() == digit.toSet() }

fun List<String>.decodeDigits(): Map<String, String> {
    val digitsMap = mutableMapOf<String, String>()
    digitsMap[findOne()] = "1"
    digitsMap[findFour()] = "4"
    digitsMap[findSeven()] = "7"
    digitsMap[findEight()] = "8"
    digitsMap[findTwo(digitsMap.toOppositeMap())] = "2"
    digitsMap[findThree(digitsMap.toOppositeMap())] = "3"
    digitsMap[findFive(digitsMap.toOppositeMap())] = "5"
    digitsMap[findSix(digitsMap.toOppositeMap())] = "6"
    digitsMap[findNine(digitsMap.toOppositeMap())] = "9"
    digitsMap[findZero(digitsMap.toOppositeMap())] = "0"
    return digitsMap
}

fun Map<String, String>.toOppositeMap() = this.entries.associate { it.value to it.key }
private fun List<String>.findZero(decodedDigits: Map<String, String>) = this.find { !decodedDigits.containsValue(it) }!!

private fun List<String>.findNine(decodedDigits: Map<String, String>) =
    this.find {
        it.length == 6 &&
                it.numberOfCommonSegmentsWith(decodedDigits["4"]!!) == 4
    }!!

private fun List<String>.findEight() = this.find { it.length == 7 }!!

private fun List<String>.findSeven() = this.find { it.length == 3 }!!

private fun List<String>.findSix(decodedDigits: Map<String, String>) =
    this.find {
        it.length == 6 &&
                it.numberOfCommonSegmentsWith(decodedDigits["1"]!!) == 1
    }!!

private fun List<String>.findFive(decodedDigits: Map<String, String>) =
    this.find {
        it.length == 5 && !decodedDigits.containsValue(it)
    }!!

private fun List<String>.findFour() = this.find { it.length == 4 }!!

private fun List<String>.findThree(decodedDigits: Map<String, String>) =
    this.find {
        it.length == 5 &&
                it.numberOfCommonSegmentsWith(decodedDigits["1"]!!) == 2
    }!!

private fun List<String>.findTwo(decodedDigits: Map<String, String>) =
    this.find {
        it.length == 5 &&
                it.numberOfCommonSegmentsWith(decodedDigits["4"]!!) == 2
    }!!

private fun List<String>.findOne() = this.find { it.length == 2 }!!

private fun String.numberOfCommonSegmentsWith(digit: String) = this.toList().intersect(digit.toList()).count()