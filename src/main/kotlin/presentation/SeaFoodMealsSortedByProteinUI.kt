package presentation

import logic.usecase.GetSeaFoodMealsSortedByProteinUseCase
import presentation.ui_io.Printer

class SeaFoodMealsSortedByProteinUI(
    private val getSeafoodMealsSortedByProtein: GetSeaFoodMealsSortedByProteinUseCase,
    private val consolePrinter: Printer,
) : ChangeFoodMoodLauncher {
    override val id: Int = 14

    override val name: String = "Seafood Meals (Protein Sorted)"

    override fun launchUI() {
        try {
            val seafoodMeals = getSeafoodMealsSortedByProtein()

            consolePrinter.displayLn("🍤 All Seafood Meals Sorted by Protein Content:")

            seafoodMeals.forEachIndexed { index, meal ->
                consolePrinter.displayLn("${index + 1}. ${meal.name} - \u001B[32mProtein: ${meal.nutrition.protein}g\u001B[0m")
            }
        } catch (exception: Exception) {
            consolePrinter.displayLn(exception.message)
        }
    }

}