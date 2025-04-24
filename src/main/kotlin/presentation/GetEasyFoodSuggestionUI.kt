package presentation

import logic.usecase.EasyFoodSuggestionGameUseCase
import model.AppException
import model.Food

class GetEasyFoodSuggestionUI(
    private val easyFoodSuggestionGameUseCase: EasyFoodSuggestionGameUseCase
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            val meals = easyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
            if (meals.isEmpty()) {
                println("No easy meals found. Please try again.")
                return
            }
            println("\n  Here are 10 easy meals for you:")
            printMealDetails(meals)
            println("--------------------------------------------------\n")
        } catch (exception: AppException) {
            println(exception.message)
        }
    }

    private fun printMealDetails(meals: List<Food>) {
        meals.forEachIndexed { index, meal ->
            println("--------------------------------------------------")
            println("${index + 1}. ${meal.name ?: "Unnamed Meal"}")
            println("Prepared Time: ${meal.minutes} minutes")
            println("Description: ${meal.description ?: "No description available"}")
            println("Ingredients: ${meal.ingredients.joinToString(", ")}")
            println("Steps: ${meal.steps.size} steps")
            println("Nutrition: Calories = ${meal.nutrition.calories}, Protein = ${meal.nutrition.protein}g")
        }
    }
}