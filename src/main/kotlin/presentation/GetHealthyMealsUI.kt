package presentation

import logic.usecase.GetHealthyMealsUseCase
import presentation.ui_io.Printer

class GetHealthyMealsUI(
    private val getHealthyMealsUseCase: GetHealthyMealsUseCase,
    private val printer : Printer
    ) : ChangeFoodMoodLauncher {
    override val id: Int
        get() = 1

    override val name: String
        get() = "Healthy Fast Meals (<=15 min)"

    override fun launchUI() {
        try {
            getHealthyMealsUseCase.fetchHealthyFastFoods().also { meals ->
                printer.displayLn("Healthy meals:\n")
                meals.forEach {
                    printer.displayLn("\nName: ${it.name ?: "Unnamed meal"}\nDescription: ${it.description ?: "No description"}")
                }
            }
        } catch (exception: Exception) {
            printer.displayLn(exception.message)
        }
    }
}