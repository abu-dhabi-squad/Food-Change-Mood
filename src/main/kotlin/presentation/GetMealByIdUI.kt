package presentation

import logic.usecase.GetMealByIdUseCase
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GetMealByIdUI(
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val intReader: InputReader<Int>,
    private val printer: Printer
) : ChangeFoodMoodLauncher {
    override fun launchUI() {
        printer.display("enter id of the meal : ")
        intReader.read()?.let { enteredID ->
            try {
                printer.displayLn(getMealByIdUseCase.getMealById(enteredID).getFullDetails())
            } catch (exception: Exception) {
                exception.message?.let { printer.displayLn(it) }
            }
        }
    }

}