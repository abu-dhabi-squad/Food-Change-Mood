package logic.usecase

import model.RichMaxAttemptException


class GuessFoodPreparationTimeUseCase() {
    operator fun invoke(userGuess: Int, preparationTime: Int, attempts: Int): Pair<Boolean,String> {
        return if (userGuess == preparationTime)
            Pair(true,"Congratulations! You guessed the correct preparation time.")
        else if (attempts == MAX_ATTEMPT)
            throw RichMaxAttemptException("You have reached the maximum number of attempts.The correct preparation time is $preparationTime minutes")
        else if (userGuess < preparationTime)
            Pair(false,"The preparation time is too low.")
        else
            Pair(false,"The preparation time is too high.")
    }

    companion object {
        private const val MAX_ATTEMPT = 3
    }


}