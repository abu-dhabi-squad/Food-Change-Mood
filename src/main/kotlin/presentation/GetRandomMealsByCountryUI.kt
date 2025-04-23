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

        if (inputCountry.isNullOrEmpty()) {
            displayLn(EMPTY_INPUT_MESSAGE)
            return
        }

        try {
            val meals = getRandomMealsByCountryUseCase.getRandomMeals(inputCountry)
            printRandomMealsByCountry(meals)
        } catch (e: Exception) {
            displayLn(NO_MEALS_MATCHED_YOUR_INPUT_MESSAGE)
        }
    }

    private fun printRandomMealsByCountry(meals: List<String>?) {
        meals?.forEachIndexed { index, meal ->
            displayLn("${index + 1}. $meal")
        }
    }


    override fun display(input: Any) {
        print(input)
    }

    override fun displayLn(input: Any) {
        println(input)
    }

    companion object{

        const val EMPTY_INPUT_MESSAGE = "No country was entered. Please try again."
        const val NO_MEALS_MATCHED_YOUR_INPUT_MESSAGE = "No meals matched your input."
        const val ENTER_INPUT_MESSAGE = "Enter a country name: "
    }
}
