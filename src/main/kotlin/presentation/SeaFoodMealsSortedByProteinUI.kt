package presentation

import logic.usecase.GetSeaFoodMealsSortedByProteinUseCase

class SeaFoodMealsSortedByProteinUI(
    private val getSeafoodMealsSortedByProtein: GetSeaFoodMealsSortedByProteinUseCase,
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            println("🍤 All Seafood Meals Sorted by Protein Content:\n")

            val seafoodMeals = getSeafoodMealsSortedByProtein()

            seafoodMeals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name} - \u001B[32mProtein: ${meal.nutrition.protein}g\u001B[0m")
            }
        } catch (exception: Exception) {
            println(exception.message)
        }
    }

}