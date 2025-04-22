package presentation

import logic.usecase.GetRandomFoodUseCase
import logic.usecase.GuessFoodPreparationTimeUseCase
import model.Food
import model.GuessPreparationTimeState
import presentation.ui_io.IntReader

class GuessFoodPreparationTimeGameUI(
    private val getRandomFoodUseCase: GetRandomFoodUseCase,
    private val guessFoodPreparationTimeUseCase: GuessFoodPreparationTimeUseCase,
    private val intReader: IntReader
) : ChangeFoodMoodLauncher {
    private var attemptsCount = 0
    private lateinit var food: Food
    private var isGameFinished = false

    override fun launchUI() {
        try {
            food = getRandomFoodUseCase()
            println("The Food is ${food.name}")
            isGameFinished = false
            runGameLoop()
        } catch (e: Exception) {
            println("Error while starting the game: ${e.message}")
        }
    }

    private fun runGameLoop() {
        try {
            while (!isGameFinished) {
                print("Guess the preparation time (in minutes): ")
                val userGuess = intReader.read()
                if (userGuess == null) {
                    println("Invalid input. Please enter a number.")
                    continue
                }
                val result = guessFoodPreparationTimeUseCase(userGuess, food.minutes, ++attemptsCount)
                handelUserGuessResult(result)
            }
        } catch (e: Throwable) {
            handleGuessFailure(e)
        }
    }

    private fun handelUserGuessResult(result: GuessPreparationTimeState) {
        when (result) {
            GuessPreparationTimeState.CORRECT -> {
                println("Congratulations! You guessed the correct preparation time.")
                endTheGame()
            }

            GuessPreparationTimeState.TOO_LOW -> println("The preparation time is too low.")
            GuessPreparationTimeState.TOO_HIGH -> println("The preparation time is too high")
        }
    }

    private fun handleGuessFailure(error: Throwable) {
        println(error.message)
        endTheGame()
    }

    private fun endTheGame() {
        isGameFinished = true
        attemptsCount = 0
    }
}
