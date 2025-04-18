package presentation

import logic.usecase.GetRandomMealsByCountryUseCase

fun getRandomMealsByCountryUI(
    getRandomMealsByCountryUseCase: GetRandomMealsByCountryUseCase
){
    print("Enter a country name: ")
    val inputCountry = readLine()?.trim()

    try {
        val meals = inputCountry?.let {
            getRandomMealsByCountryUseCase.getRandomMeals(it)
        }
        meals?.forEachIndexed { index, meal ->
            println("${index + 1}. $meal")
        } ?: println("No meals matched your input.")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}