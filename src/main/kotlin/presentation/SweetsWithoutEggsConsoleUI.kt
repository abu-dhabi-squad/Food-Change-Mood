package presentation

import logic.usecase.GetSweetsWithoutEggsUseCase
import model.WrongInputException

class SweetsWithoutEggsConsoleUI(
    private val getSweetsWithoutEggsUseCase: GetSweetsWithoutEggsUseCase,
) {
    fun start() {
        val shownMeals = mutableSetOf<Int>()
        try {
            do {
                val meal = getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(shownMeals)
                println("We suggest: ${meal.name}")
                println("Description: ${meal.description}")
                print("\nDo you like it (y / n)? ")
                val input = readln().trim()
                when {
                    input.equals("y", true) -> {
                        meal.print();
                        break
                    }
                    input.equals("n", true) -> {
                        shownMeals.add(meal.id)
                    }
                    else -> throw WrongInputException()
                }
            } while (true)
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}