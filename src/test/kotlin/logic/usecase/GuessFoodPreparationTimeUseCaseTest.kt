package logic.usecase

import com.google.common.truth.Truth.assertThat
import model.GuessPreparationTimeState
import model.RichMaxAttemptException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class GuessFoodPreparationTimeUseCaseTest {

    private lateinit var guessFoodPreparationTimeUseCase: GuessFoodPreparationTimeUseCase

    @BeforeEach
    fun setup() {
        guessFoodPreparationTimeUseCase = GuessFoodPreparationTimeUseCase()
    }

    @ParameterizedTest
    @CsvSource("15,15,2", "15,15,3")
    fun `should return CORRECT when user guess equal the preparation time`(userGuess : Int , preparationTime : Int, attempts:Int) {
        // when
        val result = guessFoodPreparationTimeUseCase(
            userGuess = userGuess,
            preparationTime = preparationTime,
            attempts = attempts
        )
        // then
        assertThat(result).isEqualTo(GuessPreparationTimeState.CORRECT)
    }


    @Test
    fun `should return TOO_HIGH when user guess high than the preparation time`() {
        // given
        val userGuess = 20
        val preparationTime = 15
        val attempts = 2
        // when
        val result = guessFoodPreparationTimeUseCase(
            userGuess = userGuess,
            preparationTime = preparationTime,
            attempts = attempts
        )
        // then
        assertThat(result).isEqualTo(GuessPreparationTimeState.TOO_HIGH)
    }

    @Test
    fun `should return TOO_LOW when user guess less than the preparation time`() {
        // given
        val userGuess = 10
        val preparationTime = 15
        val attempts = 2
        // when
        val result = guessFoodPreparationTimeUseCase(
            userGuess = userGuess,
            preparationTime = preparationTime,
            attempts = attempts
        )
        // then
        assertThat(result).isEqualTo(GuessPreparationTimeState.TOO_LOW)
    }

    @Test
    fun `should throw RichMaxAttemptException when rich max attempts with incorrect guess value`() {
        // given
        val userGuess = 10
        val preparationTime = 15
        val attempts = 3
        // when && then
        assertThrows<RichMaxAttemptException> {
            guessFoodPreparationTimeUseCase(
                userGuess = userGuess,
                preparationTime = preparationTime,
                attempts = attempts
            )
        }
    }

}