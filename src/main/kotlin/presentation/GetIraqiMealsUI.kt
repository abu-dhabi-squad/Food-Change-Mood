package presentation

import logic.usecase.GetIraqiMealsUseCase
import model.NoIraqiMealsFoundException
import presentation.ui_io.Printer

class GetIraqiMealsUI(
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase,
    private val printer: Printer
) : ChangeFoodMoodLauncher {
    override val id: Int
        get() = 3

    override val name: String
        get() = "Iraqi Meals"

    override fun launchUI() {
        try {
            val allMeals = getIraqiMealsUseCase.getAllIraqiMeals()

            printer.displayLn("\n══════════════════════════════════")
            printer.displayLn("         Iraqi Meals List          ")
            printer.displayLn("══════════════════════════════════")

            allMeals.forEach { meal ->
                printer.displayLn("Meal Name: ${meal.name}")
                printer.displayLn("Description: ${meal.description}")
                printer.displayLn("----------------------------------")
            }

        } catch (e: NoIraqiMealsFoundException) {
            printer.displayLn(e.message)
        } catch (e: Exception) {
            printer.displayLn("An error occurred: ${e.message}")
        }
    }
}
