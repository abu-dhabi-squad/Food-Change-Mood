package presentation

import logic.usecase.GetRandomMealsByCountryUseCase
import presentation.ui_io.Printer
import presentation.ui_io.StringReader

class GetRandomMealsByCountryUI(
    private val getRandomMealsByCountryUseCase: GetRandomMealsByCountryUseCase,
    private val stringReader: StringReader
) : ChangeFoodMoodLauncher, Printer {

    override fun launchUI() {
        print("Enter a country name: ")
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
        } ?: println("No meals matched your input.")
    }

    override fun print(input: Any) {
        kotlin.io.print(input)
    }

    override fun println(input: Any) {
        kotlin.io.println(input)
    }
}
