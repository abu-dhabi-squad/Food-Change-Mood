package logic.usecase

import com.google.common.truth.Truth.assertThat
import model.GuessPreparationTimeState
import model.RichMaxAttemptException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GuessFoodPreparationTimeUseCaseTest{

    private lateinit var guessFoodPreparationTimeUseCase : GuessFoodPreparationTimeUseCase
    @BeforeEach
    fun setup(){
        guessFoodPreparationTimeUseCase = GuessFoodPreparationTimeUseCase()
    }

    @Test
    fun `guessFoodPreparationTimeUseCase when user guess equal the preparation time then return CORRECT`(){

        val userGuess = 15
        val preparationTime = 15
        val attempts = 2

      val result =   guessFoodPreparationTimeUseCase(userGuess = userGuess, preparationTime = preparationTime,attempts = attempts)

        assertThat(result).isEqualTo(GuessPreparationTimeState.CORRECT)
    }

    @Test
    fun `guessFoodPreparationTimeUseCase when user guess high than the preparation time then return TOO_HIGH`(){
        val userGuess = 20
        val preparationTime = 15
        val attempts = 2

        val result =   guessFoodPreparationTimeUseCase(userGuess = userGuess, preparationTime = preparationTime,attempts = attempts)

        assertThat(result).isEqualTo(GuessPreparationTimeState.TOO_HIGH)
    }

    @Test
    fun `guessFoodPreparationTimeUseCase when user guess less than the preparation time then return TOO_LOW`(){
        val userGuess = 10
        val preparationTime = 15
        val attempts = 2

        val result =   guessFoodPreparationTimeUseCase(userGuess = userGuess, preparationTime = preparationTime,attempts = attempts)

        assertThat(result).isEqualTo(GuessPreparationTimeState.TOO_LOW)
    }

    @Test
    fun `guessFoodPreparationTimeUseCase when rich max attempts with incorrect guess value then throw RichMaxAttemptException`(){
        val userGuess = 10
        val preparationTime = 15
        val attempts = 3


        assertThrows<RichMaxAttemptException>  {
            guessFoodPreparationTimeUseCase(userGuess = userGuess, preparationTime = preparationTime,attempts = attempts)
        }
    }


    @Test
    fun `guessFoodPreparationTimeUseCase when rich max attempts with correct guess value then  return CORRECT`(){
        val userGuess = 15
        val preparationTime = 15
        val attempts = 3

        val result =   guessFoodPreparationTimeUseCase(userGuess = userGuess, preparationTime = preparationTime,attempts = attempts)

        assertThat(result).isEqualTo(GuessPreparationTimeState.CORRECT)
    }

}