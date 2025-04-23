package presentation

import logic.usecase.GymHelperUseCase
import model.Food
import model.WrongInputException
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GymHelperConsoleUI(
    private val gymHelperUseCase: GymHelperUseCase,
    private val floatReader: InputReader<Float>,
    private val consolePrinter: Printer,
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            consolePrinter.display("\nEnter desired calories: ")
            val calories = floatReader.read() ?: throw WrongInputException()
            consolePrinter.display("Enter desired proteins: ")
            val proteins = floatReader.read() ?: throw WrongInputException()
            consolePrinter.displayLn()
            gymHelperUseCase.getListOfMealsForGym(calories, proteins).forEach { food: Food -> consolePrinter.displayLn(food.getFullDetails()) }
        } catch (exception: Exception) {
            consolePrinter.displayLn(exception.message)
        }
    }
}