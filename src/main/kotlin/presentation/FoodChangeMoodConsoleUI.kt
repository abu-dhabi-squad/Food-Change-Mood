package presentation

import logic.usecase.GetRandomMealsByCountryUseCase
import logic.usecase.GetIraqiMealsUseCase
import logic.usecase.GetMealForThinPeopleUseCase
import logic.usecase.GetMealBySearchForNameUseCase
import kotlin.system.exitProcess

class FoodChangeMoodConsoleUI(
    private val gymHelperConsoleUI: GymHelperConsoleUI,
    private val getMealForThinPeopleUseCase: GetMealForThinPeopleUseCase,
    private val getMealByIdUI: GetMealByIdUI,
    private val getFoodByDateUI: GetFoodByDateUI,
    private val getRandomMealsByCountryUseCase: GetRandomMealsByCountryUseCase,
    private val guessFoodPreparationTimeGameUI: GuessFoodPreparationTimeGameUI,
    private val randomPotatoesMealsConsoleUi: RandomPotatoesMealsConsoleUi,
    private val getSeaFoodMealsSortedByProteinUI: SeaFoodMealsSortedByProteinUI,
    private val getFoodChangeMoodConsoleUi: ItalianMealsForLargeGroupUI,
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase,
    private val getHealthyMealsConsoleUI: GetHealthyMealsConsoleUI,
    private val getSweetsWithoutEggsConsoleUI: SweetsWithoutEggsConsoleUI,
    private val getMealBySearchForNameUI: GetMealBySearchForNameUI,
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
        when (input) {
            1 -> getHealthyMealsConsoleUI.executeHealthyMeals()
            2 -> getMealBySearchForNameUI.executeMealSearchByName()
            3 -> getIraqiMealsUseCaseUI(getIraqiMealsUseCase)
            4 -> println("Easy Food Suggestions")
            5 -> guessFoodPreparationTimeGameUI.start()
            6 -> getSweetsWithoutEggsConsoleUI.start()
            7 -> println("Keto Diet Meal Helper")
            8 -> getFoodByDateUI.runUI()
            9 -> gymHelperConsoleUI.start()
            10 -> getRandomMealsByCountryUI(getRandomMealsByCountryUseCase)
            11 -> println("Ingredient Guess Game")
            12 -> { randomPotatoesMealsConsoleUi.displayRandomPotatoesMealsUI() }
            13 -> getHighCalorieMealUI(getMealForThinPeopleUseCase)
            14 -> getSeaFoodMealsSortedByProteinUI.start()
            15 -> getFoodChangeMoodConsoleUi.start()
            16 -> getMealByIdUI.getDetailsById()
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

    private fun getUserInput(): Int? {
        print("Enter your choice: ")
        return readlnOrNull()?.toIntOrNull()
    }
}
