package presentation

import logic.usecase.GetRandomFoodUseCase
import logic.usecase.GuessFoodPreparationTimeUseCase
import model.Food
import model.GuessPreparationTimeState
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GuessFoodPreparationTimeGameUI(
    private val getRandomFoodUseCase: GetRandomFoodUseCase,
    private val guessFoodPreparationTimeUseCase: GuessFoodPreparationTimeUseCase,
    private val reader: InputReader,
    private val printer : Printer
) : ChangeFoodMoodLauncher {
    override val id: Int
        get() = 5

    override val name: String
        get() = "Guess Prep Time Game"

    private var attemptsCount = 0
    private lateinit var food: Food
    private var isGameFinished = false

    override fun launchUI() {
        try {
            food = getRandomFoodUseCase()
            printer.displayLn("The Food is ${food.name}")
            isGameFinished = false
            runGameLoop()
        } catch (e: Exception) {
            printer.displayLn("Error while starting the game: ${e.message}")
        }
    }

    private fun runGameLoop() {
        try {
            while (!isGameFinished) {
                printer.display("Guess the preparation time (in minutes): ")
                val userGuess = reader.readInt()
                if (userGuess == null) {
                    printer.displayLn("Invalid input. Please enter a number.")
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
                printer.displayLn("Congratulations! You guessed the correct preparation time.")
                endTheGame()
            }
            GuessPreparationTimeState.TOO_LOW -> printer.displayLn("The preparation time is too low.")
            GuessPreparationTimeState.TOO_HIGH -> printer.displayLn("The preparation time is too high")
        }
    }

    private fun handleGuessFailure(error: Throwable) {
        printer.displayLn(""+error.message)
        endTheGame()
    }

    private fun endTheGame() {
        isGameFinished = true
        attemptsCount = 0
    }
}
