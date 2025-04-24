package presentation

import logic.usecase.GetItalianMealsForLargeGroupUseCase
import presentation.ui_io.Printer

class ItalianMealsForLargeGroupUI(
    private val getItalianMealsForLargeGroup: GetItalianMealsForLargeGroupUseCase,
    private val  printer: Printer
):ChangeFoodMoodLauncher {

    override fun launchUI() {
        getItalianMealsForLargeGroup.getItalianMealForLargeGroup().forEach {
            printer.displayLn("\nName: ${it.first}")
            printer.displayLn("Description: ${it.second}")
        }
    }

}