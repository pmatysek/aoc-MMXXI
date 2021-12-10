import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day10KtTest{

    @Test
    fun shouldNotReturnErrorIfCorrect(){
        //given
        val input = "[({(<(())[]>[[{[]{<()<>>"

        //when
        val error = input.findError()

        //then
        assertThat(error).isEmpty()
    }

    @Test
    fun shouldNotReturnCorrectErrorChar(){
        //given
        val input = "{([(<{}[<>[]}>{[]{[(<()>"

        //when
        val error = input.findError()

        //then
        assertThat(error).first().isEqualTo('}')
    }
}