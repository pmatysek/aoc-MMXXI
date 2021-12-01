fun main(){
    val depths = "day1".getInputLines().map { it.toInt() }
    val depthIncrementsCount = depths.windowed(2).count { it[1] > it[0] }
    println("Depth increases $depthIncrementsCount times")

    val slidingWindowsDepths = depths.windowed(3).map { it.sum() }
    val slidingWindowsDepthsIncrementCounts = slidingWindowsDepths.windowed(2).count { it[1] > it[0] }
    println("Depth in sliding windows increases $slidingWindowsDepthsIncrementCounts times")
}