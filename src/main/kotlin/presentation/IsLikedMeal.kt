package presentation

import model.WrongInputException
import presentation.ui_io.Printer
import presentation.ui_io.StringReader

class IsLikedMeal(
    private val printer: Printer,
    private val inputReader: StringReader
) {
    fun run(): Boolean {
        printer.displayLn("Do you like it? {y/n}")
        inputReader.read()?.let {
            when {
                it.equals("y", true) -> {
                    return true
                }

                it.equals("n", true) -> {
                    return false
                }

                else -> {
                    throw WrongInputException()
                }
            }
        } ?: throw WrongInputException()
    }

}