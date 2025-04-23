package presentation

import presentation.ui_io.InputReader
import kotlin.system.exitProcess

class FoodChangeMoodConsoleUI(
    private val guessIngredientConsoleUI: GuessIngredientConsoleUI,
    private val gymHelperConsoleUI: GymHelperConsoleUI,
    private val getMealByIdUI: GetMealByIdUI,
    private val getFoodByDateUI: GetFoodByDateUI,
    private val guessFoodPreparationTimeGameUI: GuessFoodPreparationTimeGameUI,
    private val randomPotatoesMealsConsoleUi: RandomPotatoesMealsConsoleUi,
    private val getSeaFoodMealsSortedByProteinUI: SeaFoodMealsSortedByProteinUI,
    private val getFoodChangeMoodConsoleUi: ItalianMealsForLargeGroupUI,
    private val getSweetsWithoutEggsConsoleUI: SweetsWithoutEggsConsoleUI,
    private val randomKetoMealUI: RandomKetoMealUI,
    private val getHealthyMealsUI: GetHealthyMealsUI,
    private val getMealBySearchForNameUI: GetMealByNameUI,
    private val easyFoodSuggestionUI: GetEasyFoodSuggestionUI,
    private val getHighCalorieMealForThinPeopleUI: GetHighCalorieMealForThinPeopleUI,
    private val getIraqiMealsUI: GetIraqiMealsUI,
    private val getRandomMealsByCountryUI: GetRandomMealsByCountryUI,
    private val reader: InputReader
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        showWelcome()
        presentFeature()
    }

    private fun showWelcome() {
        println("Welcome to Food Change MoodConsole ")
    }

    private fun presentFeature() {
        showOptions()
        print("Enter your choice: ")
        val input = reader.readInt()
        when (input) {
            1 -> getHealthyMealsUI.launchUI()
            2 -> getMealBySearchForNameUI.launchUI()
            3 -> getIraqiMealsUI.launchUI()
            4 -> easyFoodSuggestionUI.launchUI()
            5 -> guessFoodPreparationTimeGameUI.launchUI()
            6 -> getSweetsWithoutEggsConsoleUI.launchUI()
            7 -> randomKetoMealUI.launchUI()
            8 -> getFoodByDateUI.launchUI()
            9 -> gymHelperConsoleUI.launchUI()
            10 -> getRandomMealsByCountryUI.launchUI()
            11 -> guessIngredientConsoleUI.launchUI()
            12 -> randomPotatoesMealsConsoleUi.launchUI()
            13 -> getHighCalorieMealForThinPeopleUI.launchUI()
            14 -> getSeaFoodMealsSortedByProteinUI.launchUI()
            15 -> getFoodChangeMoodConsoleUi.launchUI()
            16 -> getMealByIdUI.launchUI()
            0 -> exitProcess(0)
            else -> println("Invalid input")
        }
        presentFeature()
    }

    private fun showOptions() {
        println()
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

}
