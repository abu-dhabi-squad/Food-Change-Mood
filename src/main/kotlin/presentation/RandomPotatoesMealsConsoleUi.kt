package presentation

import logic.usecase.GetRandomPotatoesMealsUseCase

class RandomPotatoesMealsConsoleUi(private val getRandomPotatoesMealsUseCase: GetRandomPotatoesMealsUseCase) {
    fun displayRandomPotatoesMealsUI() {
        println("Meals that include potatoes:\n")
        try {
            val meals = getRandomPotatoesMealsUseCase.getTenRandomPotatoesMeals()
            meals.forEachIndexed { index, mealName ->
                println("$index : $mealName")
            }
            promptForMoreMeals()
        } catch (exception: Exception) {
            println("Error: ${exception.message}")
        }
    }

    private fun promptForMoreMeals() {
        while (true) {
            println("\nWould you like to see more potato meals? (Y/N)")
            val input = readUserInput()

            when {
                input.equals("y", ignoreCase = true) -> {
                    displayRandomPotatoesMealsUI()
                }

                input.equals("n", ignoreCase = true) -> {
                    println("Thanks! Enjoy your meals ")
                    return
                }

                else -> {
                    println("Invalid input. Please enter 'Y' or 'N'.")
                }
            }
        }
    }

    private fun readUserInput(): String {
        return readlnOrNull()?.trim() ?: ""
    }
}