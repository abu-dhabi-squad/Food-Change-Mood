package logic.usecase

import model.GuessPreparationTimeState
import model.RichMaxAttemptException


class GuessFoodPreparationTimeUseCase() {
    operator fun invoke(userGuess: Int, preparationTime: Int, attempts: Int): GuessPreparationTimeState {
        return if (userGuess == preparationTime)
             GuessPreparationTimeState.CORRECT
        else if (attempts == MAX_ATTEMPT)
            throw RichMaxAttemptException(preparationTime)
        else if (userGuess < preparationTime)
             GuessPreparationTimeState.TOO_LOW
        else
             GuessPreparationTimeState.TOO_HIGH
    }

    companion object {
        private const val MAX_ATTEMPT = 3
    }


}