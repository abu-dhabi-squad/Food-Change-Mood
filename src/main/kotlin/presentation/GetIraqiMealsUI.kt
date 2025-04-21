package presentation

import logic.usecase.GetIraqiMealsUseCase

class GetIraqiMealsUI(
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            val allMeals = getIraqiMealsUseCase.getAllIraqiMeals()

            println("\n══════════════════════════════════")
            println("         Iraqi Meals List          ")
            println("══════════════════════════════════")

            allMeals.forEach { meal ->
                println("Meal Name: ${meal.name}")
                println("Description: ${meal.description ?: "No description available."}")
                println("----------------------------------")
            }

        } catch (e: Exception) {
            println(" (${e.message})")
        }
    }
}