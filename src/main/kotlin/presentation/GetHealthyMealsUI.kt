package presentation

import logic.usecase.GetHealthyMealsUseCase

class GetHealthyMealsUI(private val getHealthyMealsUseCase: GetHealthyMealsUseCase) : ChangeFoodMoodLauncher {
    override val id: Int
        get() = 1

    override val name: String
        get() = "Healthy Fast Meals (<=15 min)"

    override fun launchUI() {
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