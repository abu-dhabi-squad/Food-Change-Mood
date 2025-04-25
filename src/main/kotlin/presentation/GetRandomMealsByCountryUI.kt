package presentation

import logic.usecase.GetRandomMealsByCountryUseCase
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GetRandomMealsByCountryUI(
    private val getRandomMealsByCountryUseCase: GetRandomMealsByCountryUseCase,
    private val reader: InputReader,
    private val printer: Printer,
) : ChangeFoodMoodLauncher{
    override val id: Int = 10

    override val name: String = "Explore Country Food Culture"

    override fun launchUI() {

        printer.display(ENTER_INPUT_MESSAGE)
        val inputCountry = reader.readString()?.trim()

        if (inputCountry.isNullOrEmpty()) {
            printer.displayLn(EMPTY_INPUT_MESSAGE)
            return
        }

        try {
            val meals = getRandomMealsByCountryUseCase.getRandomMeals(inputCountry)
            printRandomMealsByCountry(meals)
        } catch (e: Exception) {
            printer.displayLn(e.message)
        }
    }

    private fun printRandomMealsByCountry(meals: List<String>) {
        meals.forEachIndexed { index, meal ->
            printer.displayLn("${index + 1}. $meal")
        }
    }

    companion object{

        const val EMPTY_INPUT_MESSAGE = "No country was entered. Please try again."
        const val NO_MEALS_FOUND = "No meals found!"
        const val ENTER_INPUT_MESSAGE = "Enter a country name: "
    }
}
