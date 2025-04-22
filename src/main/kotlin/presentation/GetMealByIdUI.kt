package presentation

import logic.usecase.GetMealByIdUseCase
import presentation.ui_io.IntReader
import presentation.ui_io.Printer
import util.showDetails

class GetMealByIdUI(
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val intReader: IntReader,
    private val printer: Printer
) : ChangeFoodMoodLauncher {
    override fun launchUI() {
        printer.print("enter id of the meal : ")
        intReader.read()?.let { enteredID ->
            try {
                getMealByIdUseCase.getMealById(enteredID).showDetails()
            } catch (exception: Exception) {
                exception.message?.let { printer.println(it) }
            }
        }
    }

}