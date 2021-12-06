import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day5KtTest {

    @Test
    fun `should get all points from diagonal line`() {
        //given
        val line = Line(
            Point(1, 1),
            Point(3, 3)
        )

        //when
        val points = line.getAllPoints()

        //then
        assertThat(points).containsExactly(
            Point(1,1),
            Point(2, 2),
            Point(3, 3)
        )
    }
}