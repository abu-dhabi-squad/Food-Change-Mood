package presentation

import logic.usecase.GetRandomMealsByCountryUseCase
import presentation.ui_io.Printer
import presentation.ui_io.StringReader

class GetRandomMealsByCountryUI(
    private val getRandomMealsByCountryUseCase: GetRandomMealsByCountryUseCase,
    private val stringReader: StringReader
) : ChangeFoodMoodLauncher, Printer {

    override fun launchUI() {
        display(ENTER_INPUT_MESSAGE)
        val inputCountry = stringReader.read()?.trim()
        try {
            val meals = inputCountry?.let {
                getRandomMealsByCountryUseCase.getRandomMeals(it)
            }
            printRandomMealsByCountry(meals)
        } catch (e: Exception) {
            displayLn("Error: ${e.message}")
        }
    }

    private fun printRandomMealsByCountry(meals: List<String>?) {
        meals?.forEachIndexed { index, meal ->
            displayLn("${index + 1}. $meal")
        } ?: displayLn(NO_MEALS_MATCHED_YOUR_INPUT_MESSAGE)
    }

    override fun display(input: Any) {
        print(input)
    }

    override fun displayLn(input: Any) {
        println(input)
    }

    companion object{

        const val NO_MEALS_MATCHED_YOUR_INPUT_MESSAGE = "No meals matched your input."
        const val ENTER_INPUT_MESSAGE = "Enter a country name: "
    }
}
