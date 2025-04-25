package presentation

import logic.usecase.GetSweetsWithoutEggsUseCase

class SweetsWithoutEggsConsoleUI(
    private val getSweetsWithoutEggsUseCase: GetSweetsWithoutEggsUseCase,
) : ChangeFoodMoodLauncher {
    override val id: Int
        get() = 6

    override val name: String
        get() = "Egg-Free Sweets"

    override fun launchUI() {
        val shownMeals = mutableSetOf<Int>()
        try {
            do {
                val meal = getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(shownMeals)
                println("We suggest: ${meal.name}")
                println("Description: ${meal.description}")
                when (isLikedMeal()) {
                    true -> {
                        print(meal.getFullDetails()); break
                    }

                    false -> shownMeals.add(meal.id)
                }
            } while (true)
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}