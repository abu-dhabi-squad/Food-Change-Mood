package presentation

import logic.usecase.GetRandomFoodUseCase
import logic.usecase.GuessFoodPreparationTimeUseCase
import model.Food

class GuessFoodPreparationTimeGameUI(
    private val getRandomFoodUseCase: GetRandomFoodUseCase,
    private val guessFoodPreparationTimeUseCase: GuessFoodPreparationTimeUseCase
) {
    private var attemptsCount = 0
    private lateinit var food: Food
    private var isGameFinished = false

    fun start() {
        try {
            food = getRandomFoodUseCase()
            println("The Food is ${food.name}")
            runGameLoop()
        } catch (e: Exception) {
            println("Error while starting the game: ${e.message}")
        }
    }

    private fun runGameLoop() {
        try {
            while (!isGameFinished) {
                val userGuess = getUserGuess()
                if (userGuess == null) {
                    println("Invalid input. Please enter a number.")
                    continue
                }
                val result  = guessFoodPreparationTimeUseCase(userGuess, food.minutes, ++attemptsCount)
                isGameFinished = result.first
                println(result.second)
            }
        } catch (e: Throwable) {
            handleGuessFailure(e)
        }
        if (isGameFinished){
            isGameFinished = false
            attemptsCount = 0
        }
    }

    private fun getUserGuess(): Int? {
        print("Guess the preparation time (in minutes): ")
        return readlnOrNull()?.toIntOrNull()
    }

    private fun handleGuessFailure(error: Throwable) {
        println(error.message)
        attemptsCount = 0
    }
}
