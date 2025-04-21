package presentation

import logic.usecase.GetRandomPotatoesMealsUseCase
import presentation.input_reader.StringReader

class RandomPotatoesMealsConsoleUi(
    private val getRandomPotatoesMealsUseCase: GetRandomPotatoesMealsUseCase,
    private val stringReader: StringReader
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            val meals = getRandomPotatoesMealsUseCase.getRandomPotatoesMeals()

            println("\nHere are some meals that include potatoes:\n")
            meals.also {
                it.forEachIndexed { index, mealName -> println("${index + 1}) $mealName") }
            }
            promptForMoreMeals()
        } catch (exception: Exception) {
            println("\n${exception.message}")
        }
    }

    private fun promptForMoreMeals() {
        while (true) {
            println("\nWould you like to see more potato meals? (Y/N)")
            val input = stringReader.read()
            when {
                input.equals("y", ignoreCase = true) -> {
                    launchUI()
                    return
                }

                input.equals("n", ignoreCase = true) -> {
                    println("Thanks! , Enjoy your meals \n")
                    return

                }

                else -> {
                    println("Invalid input. Please enter 'Y' or 'N'.")
                }
            }
        }
    }

}