package presentation

import logic.usecase.GymHelperUseCase
import model.Food
import model.WrongInputException
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GymHelperConsoleUI(
    private val gymHelperUseCase: GymHelperUseCase,
    private val reader: InputReader,
    private val consolePrinter: Printer,
) : ChangeFoodMoodLauncher {
    override val id: Int = 9

    override val name: String = "Gym Helper (Calories/Protein)"

    override fun launchUI() {
        try {
            consolePrinter.display("\nEnter desired calories: ")
            val calories = reader.readFloat() ?: throw WrongInputException()
            consolePrinter.display("Enter desired proteins: ")
            val proteins = reader.readFloat() ?: throw WrongInputException()
            consolePrinter.displayLn()
            gymHelperUseCase.getListOfMealsForGym(calories, proteins).forEach { food: Food -> consolePrinter.displayLn(food.getFullDetails()) }
        } catch (exception: Exception) {
            consolePrinter.displayLn(exception.message)
        }
    }
}