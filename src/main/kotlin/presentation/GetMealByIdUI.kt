package presentation

import logic.usecase.GetMealByIdUseCase
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GetMealByIdUI(
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val reader: InputReader,
    private val printer: Printer
) : ChangeFoodMoodLauncher {
    override val id: Int
        get() = 16

    override val name: String
        get() = "search meal by ID"

    override fun launchUI() {
        printer.display("enter id of the meal : ")
        reader.readInt()?.let { enteredID ->
            try {
                printer.displayLn(getMealByIdUseCase.getMealById(enteredID).getFullDetails())
            } catch (exception: Exception) {
                exception.message?.let { printer.displayLn(it) }
                    ?: printer.displayLn("error")
            }
        }
            ?: printer.displayLn("wrong input")
    }

}