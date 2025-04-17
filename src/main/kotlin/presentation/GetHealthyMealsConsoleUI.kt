package presentation

import logic.usecase.GetHealthyMealsUseCase

class GetHealthyMealsConsoleUI(private val getHealthyMealsUseCase: GetHealthyMealsUseCase) {

    fun executeHealthyMeals() {
        try {
            getHealthyMealsUseCase.fetchHealthyFastFoods()
                .also { meals ->
                    if (meals.isEmpty())
                        println("No meals found!")
                    else {
                        println("Healthy meals: -")
                        meals.forEach { println("\nName: ${it.name ?: "Unnamed meal"}\nDescription: ${it.description ?: "No description"}") }
                    }
                }
        } catch (exception: Exception) {
            println(exception.message)
        }

    }
}