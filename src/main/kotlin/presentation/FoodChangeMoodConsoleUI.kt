package presentation

import logic.usecase.EasyFoodSuggestionGameUseCase
import kotlin.system.exitProcess
class FoodChangeMoodConsoleUI(
    // use cases will be here
    private val easyFoodSuggestionGameUseCase: EasyFoodSuggestionGameUseCase
) {
    fun start() {
        showWelcome()
        presentFeature()
    }

    private fun showWelcome() {
        println("Welcome to Food Change MoodConsole ")
    }

    private fun presentFeature() {
        showOptions()
        val input = getUserInput()
        when(input){
            1 -> println("Healthy Fast Meals (<=15 min)")
            2 -> println("Search Meal by Name")
            3 -> println("Iraqi Meals")
            4 -> launchSEasyFoodSuggestionGame()
            5 -> println("Guess Prep Time Game")
            6 -> println("Egg-Free Sweets")
            7 -> println("Keto Diet Meal Helper")
            8 -> println("Search Foods by Add Date")
            9 -> println("Gym Helper (Calories/Protein)")
            10 -> println("Explore Country Food Culture")
            11 -> println("Ingredient Guess Game")
            12 -> println("Potato-Based Meals")
            13 -> println("High-Calorie Meals (>700 cal)")
            14 -> println("Seafood Meals (Protein Sorted)")
            15 -> println("Italian Meals for Large Groups")
            0 -> exitProcess(0)
            else -> println("Invalid input")
        }
        presentFeature()
    }



    private fun showOptions() {
        println("╔════════════════════════════════════╗")
        println("║      Food Change Mood Console      ║")
        println("╠════════════════════════════════════╣")
        println("║ 1.  Healthy Fast Meals (<=15 min)  ║")
        println("║ 2.  Search Meal by Name            ║")
        println("║ 3.  Iraqi Meals                    ║")
        println("║ 4.  Easy Food Suggestions          ║")
        println("║ 5.  Guess Prep Time Game           ║")
        println("║ 6.  Egg-Free Sweets                ║")
        println("║ 7.  Keto Diet Meal Helper          ║")
        println("║ 8.  Search Foods by Add Date       ║")
        println("║ 9.  Gym Helper (Calories/Protein)  ║")
        println("║ 10. Explore Country Food Culture   ║")
        println("║ 11. Ingredient Guess Game          ║")
        println("║ 12. Potato-Based Meals             ║")
        println("║ 13. High-Calorie Meals (>700 cal)  ║")
        println("║ 14. Seafood Meals (Protein Sorted) ║")
        println("║ 15. Italian Meals for Large Groups ║")
        println("║ 0.  Exit                           ║")
        println("╚════════════════════════════════════╝")
    }

    private fun launchSEasyFoodSuggestionGame() {
        val meals = easyFoodSuggestionGameUseCase.suggest10RandomEasyMeals()
        if (meals.isEmpty()) {
            println("No easy meals found. Please try again.")
            return
        }

        println("\n  Here are 10 easy meals for you:")
        meals.forEachIndexed { index, meal ->
            println("--------------------------------------------------")
            println("${index + 1}. ${meal.name ?: "Unnamed Meal"}")
            println("Prepared Time: ${meal.minutes} minutes")
            println("Description: ${meal.description ?: "No description available"}")
            println("Ingredients: ${meal.ingredients.joinToString(", ")}")
            println("Steps: ${meal.steps.size} steps")
            println("Nutrition: Calories = ${meal.nutrition.calories}, Protein = ${meal.nutrition.protein}g")
        }
        println("--------------------------------------------------\n")
    }

    private fun getUserInput(): Int? {
        print("Enter your choice: ")
        return readlnOrNull()?.toIntOrNull()
    }


}
