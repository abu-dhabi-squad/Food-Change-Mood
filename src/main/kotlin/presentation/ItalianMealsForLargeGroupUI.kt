package presentation

import logic.usecase.GetItalianMealsForLargeGroupUseCase
import presentation.ui_io.Printer

class ItalianMealsForLargeGroupUI(
    private val getItalianMealsForLargeGroup: GetItalianMealsForLargeGroupUseCase,
    private val printer: Printer
) : ChangeFoodMoodLauncher {
    override val id: Int = 15

    override val name: String = "Italian Meals for Large Groups"

    override fun launchUI() {
        try {
            getItalianMealsForLargeGroup.getItalianMealForLargeGroup().forEach {
                printer.displayLn("\nName: ${it.first}")
                printer.displayLn("Description: ${it.second}")
            }
        } catch (
            exception: Exception
        ) {
            printer.displayLn("Error: ${exception.message}")
        }

    }

}