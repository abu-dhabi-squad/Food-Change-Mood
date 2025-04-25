package presentation

import logic.usecase.GetRandomPotatoesMealsUseCase
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class RandomPotatoesMealsConsoleUi(
    private val getRandomPotatoesMealsUseCase: GetRandomPotatoesMealsUseCase,
    private val reader: InputReader,
    private val printer: Printer

) : ChangeFoodMoodLauncher {
    override val id: Int = 12

    override val name: String = "Potato-Based Meals"

    override fun launchUI() {
        try {
            val meals = getRandomPotatoesMealsUseCase.getRandomPotatoesMeals()

            printer.displayLn("\nHere are some meals that include potatoes:\n")
            meals.also {
                it.forEachIndexed { index, mealName -> printer.displayLn("${index + 1}) $mealName") }
            }
            promptForMoreMeals()
        } catch (exception: Exception) {
            printer.displayLn("\n${exception.message}")
        }
    }

    private fun promptForMoreMeals() {
        while (true) {
            printer.displayLn("\nWould you like to see more potato meals? (Y/N)")
            val input = reader.readString()
            when {
                input.equals("y", ignoreCase = true) -> {
                    launchUI()
                    return
                }

                input.equals("n", ignoreCase = true) -> {
                    printer.displayLn("Thanks! , Enjoy your meals \n")
                    return

                }

                else -> {
                    printer.displayLn("Invalid input. Please enter 'Y' or 'N'.")
                }
            }
        }
    }

}