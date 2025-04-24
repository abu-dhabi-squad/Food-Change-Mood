package presentation

import logic.usecase.GetMealBySearchNameUseCase
import model.NoMealsFoundException
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GetMealByNameUI(
    private val getMealBySearchNameUseCase: GetMealBySearchNameUseCase,
    private val reader: InputReader,
    private val printer: Printer
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        printer.display("Enter the meal name to search: ")
        reader.readString()
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.let { input ->
                try {
                    printResultFromSearch(input)
                } catch (exception: NoMealsFoundException) {
                    printer.displayLn(exception)
                }
            }
            ?: printer.displayLn("Invalid input. Please enter a non-empty keyword.")
    }

    private fun printResultFromSearch(input: String) {
        getMealBySearchNameUseCase.findMealsByName(input)
            .also { results ->
                printer.displayLn("Search results for '$input':")
                results.forEach { printer.displayLn(it.name ?: "none") }
            }
    }

}