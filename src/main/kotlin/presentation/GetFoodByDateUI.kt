package presentation

import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealByIdUseCase
import model.InvalidIdException
import model.WrongInputException
import presentation.ui_io.IntReader
import presentation.ui_io.Printer
import presentation.ui_io.StringReader
import util.DateParserInterface
import util.GetFoodByDateValidationInterface
import util.showDetails
import java.time.LocalDate

class GetFoodByDateUI(
    private val dateParserInterface: DateParserInterface,
    private val getFoodByDateUseCase: GetFoodByDateUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val getFoodByDateValidationInterface: GetFoodByDateValidationInterface,
    private val intReader: IntReader,
    private val stringReader: StringReader,
    private val printer: Printer

) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            getFoodByDateUseCase.getMealsByDate(getInputDate())
                .let { mealsByDate ->
                    mealsByDate.forEach { (id, name) -> printer.println("id: $id - name: $name") }
                    getDetailsOfMeals(mealsByDate)
                }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun getInputDate(): LocalDate {
        printer.print("Enter the Date (yyyy-M-d) : ")
        return stringReader.read()
            ?.takeIf { date -> getFoodByDateValidationInterface.isValidDate(date) }
            ?.let { date -> dateParserInterface.parseDateFromString(date) }
            ?: throw WrongInputException()
    }

    private fun getDetailsOfMeals(mealsByDate: List<Pair<Int, String>>) {
        while (true) {
            printer.print("Do you want to see details of one of the Meals (Y/N)? ")
            val input = stringReader.read()?.trim()?.lowercase()
            when (input) {
                "y" -> try {
                    getDetailsById(mealsByDate)
                } catch (e: Exception) {
                    println(e.message)
                }

                "n" -> return
                else -> printer.println("Please enter Y or N.")
            }
        }
    }

    private fun getDetailsById(mealsByDate: List<Pair<Int, String>>) {
        printer.print("enter id of the meal : ")
        intReader.read()?.let { enteredID ->
            mealsByDate.takeIf { it.any { item -> item.first == enteredID } }
                ?.let {
                    getMealByIdUseCase.getMealById(enteredID).showDetails()
                } ?: throw InvalidIdException()
        } ?: throw WrongInputException()
    }


}