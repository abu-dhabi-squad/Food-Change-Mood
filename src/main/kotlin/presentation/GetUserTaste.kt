package presentation

import model.WrongInputException
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GetUserTaste(
    private val printer: Printer,
    private val inputReader: InputReader
) {
    fun run(): Boolean {
        printer.displayLn("Do you like it? {y/n}")
        inputReader.readString()?.let {
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