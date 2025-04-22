package presentation

import logic.usecase.GetRandomMealsByCountryUseCase
import presentation.ui_io.Printer
import presentation.ui_io.StringReader

class GetRandomMealsByCountryUI(
    private val getRandomMealsByCountryUseCase: GetRandomMealsByCountryUseCase,
    private val stringReader: StringReader
) : ChangeFoodMoodLauncher, Printer {

    override fun launchUI() {
        print(ENTER_INPUT_MESSAGE)
        val inputCountry = stringReader.read()?.trim()
        try {
            val meals = inputCountry?.let {
                getRandomMealsByCountryUseCase.getRandomMeals(it)
            }
            printRandomMealsByCountry(meals)
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private fun printRandomMealsByCountry(meals: List<String>?) {
        meals?.forEachIndexed { index, meal ->
            println("${index + 1}. $meal")
        } ?: println(NO_MEALS_MATCHED_YOUR_INPUT_MESSAGE)
    }

    override fun print(input: Any) {
        kotlin.io.print(input)
    }

    override fun println(input: Any) {
        kotlin.io.println(input)
    }

    companion object{

        const val NO_MEALS_MATCHED_YOUR_INPUT_MESSAGE = "No meals matched your input."
        const val ENTER_INPUT_MESSAGE = "Enter a country name: "
    }
}
