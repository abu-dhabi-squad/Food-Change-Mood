package presentation

import logic.usecase.GetHealthyMealsUseCase

class GetHealthyMealsUI(private val getHealthyMealsUseCase: GetHealthyMealsUseCase) {

    fun executeHealthyMeals() {
        try {
            getHealthyMealsUseCase.fetchHealthyFastFoods().also { meals ->
                println("Healthy meals:\n")
                meals.forEach {
                    println("\nName: ${it.name ?: "Unnamed meal"}\nDescription: ${it.description ?: "No description"}")
                }
            }
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}