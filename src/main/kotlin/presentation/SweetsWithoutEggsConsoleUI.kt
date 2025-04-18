package presentation

import logic.usecase.GetSweetsWithoutEggsUseCase
import model.WrongInputException

class SweetsWithoutEggsConsoleUI(
    private val getSweetsWithoutEggsUseCase: GetSweetsWithoutEggsUseCase,
) {
    fun start() {
        try {
            getSweetsWithoutEggsUseCase.getSweetsWithoutEggs().forEach { meal ->
                println("We suggest: ${meal.name}")
                print("Do you like it? ")
                val input = readlnOrNull()?.trim()
                when {
                    input.equals("y", true) -> {meal.print(); return}
                    !input.equals("n", true) -> { throw WrongInputException() }
                }
            }
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}