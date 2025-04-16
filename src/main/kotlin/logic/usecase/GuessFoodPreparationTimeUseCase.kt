package logic.usecase

import model.GuessWrongPreparationTimeException
import model.InValidPreparationTimeException
import model.RichMaxAttemptException


class GuessFoodPreparationTimeUseCase() {
    private var attempts = 0
    private var preparationTime: Int = 0


    operator fun invoke(userGuess: Int): Result<String> {
        attempts++
        return if (userGuess == preparationTime)
            onUserGuessCorrectTime()
        else if (attempts == MAX_ATTEMPT)
            onUserRichMaxAttempt(preparationTime)
        else if (userGuess < preparationTime)
            Result.failure(GuessWrongPreparationTimeException("The preparation time is too low."))
        else
            Result.failure(GuessWrongPreparationTimeException("The preparation time is too high."))
    }

    private fun onUserGuessCorrectTime(): Result<String> {
        attempts = 0
        return Result.success("Congratulations! You guessed the correct preparation time.")
    }


    private fun onUserRichMaxAttempt(preparationTime: Int): Result<String> {
        attempts = 0
        return Result.failure(
            RichMaxAttemptException(
                "You have reached the maximum number of attempts.\n" +
                        "The correct preparation time is $preparationTime minutes"
            )
        )
    }

    fun setPreparationTime(preparationTime: Int) {
        if (preparationTime > 0)
            this.preparationTime = preparationTime
        else throw InValidPreparationTimeException("Preparation time can not be negative")
    }

    companion object {
        private const val MAX_ATTEMPT = 3
    }


}