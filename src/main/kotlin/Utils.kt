inline fun <reified T> List<List<T>>.transpose(): MutableList<MutableList<T?>> {
    val transpose = MutableList(this.size) { MutableList<T?>(this.first().size) { null } }
    for (i in this.indices) {
        for (j in this.first().indices) {
            transpose[j][i] = this[i][j]
        }
    }
    return transpose
}