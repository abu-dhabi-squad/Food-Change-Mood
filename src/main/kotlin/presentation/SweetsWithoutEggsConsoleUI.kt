package presentation

import logic.usecase.GetSweetsWithoutEggsUseCase
import util.print

class SweetsWithoutEggsConsoleUI(
    private val getSweetsWithoutEggsUseCase: GetSweetsWithoutEggsUseCase,
) : ChangeFoodMoodLauncher {
    override fun launchUI() {
        val shownMeals = mutableSetOf<Int>()
        try {
            do {
                val meal = getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(shownMeals)
                println("We suggest: ${meal.name}")
                println("Description: ${meal.description}")
                when (isLikedMeal()) {
                    true -> {
                        meal.print(); break
                    }

                    false -> shownMeals.add(meal.id)
                }
            } while (true)
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}