package presentation

import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealByIdUseCase
import model.InvalidIdException
import model.WrongInputException
import presentation.ui_io.InputReader
import presentation.ui_io.Printer
import util.DateParserInterface
import util.DateValidationInterface
import java.time.LocalDate

class GetFoodByDateUI(
    private val dateParserInterface: DateParserInterface,
    private val getFoodByDateUseCase: GetFoodByDateUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val DateValidationInterface: DateValidationInterface,
    private val reader: InputReader,
    private val printer: Printer
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            getFoodByDateUseCase.getMealsByDate(getInputDate())
                .let { mealsByDate ->
                    mealsByDate.forEach { (id, name) -> printer.displayLn("id: $id - name: $name") }
                    getDetailsOfMeals(mealsByDate)
                }
        } catch (e: Exception) {
            e.message?.let { printer.displayLn(it) }
        }
    }

    private fun getInputDate(): LocalDate {
        printer.display("Enter the Date (M/d/yyyy) : ")
        return reader.readString()
            ?.takeIf { date -> DateValidationInterface.isValidDate(date) }
            ?.let { date -> dateParserInterface.parseDateFromString(date) }
            ?: throw WrongInputException()
    }

    private fun getDetailsOfMeals(mealsByDate: List<Pair<Int, String>>) {
        while (true) {
            print("Do you want to see details of one of the Meals (Y/N)? ")
            val input = reader.readString()?.trim()?.lowercase()
            when (input) {
                "y" -> try {
                    getDetailsById(mealsByDate)
                } catch (e: Exception) {
                    e.message?.let { printer.displayLn(it) }
                }

                "n" -> return
                else -> printer.displayLn("Please enter Y or N.")
            }
        }
    }

    private fun getDetailsById(mealsByDate: List<Pair<Int, String>>) {
        printer.display("enter id of the meal : ")
        reader.readInt()?.let { enteredID ->
            mealsByDate.takeIf { it.any { item -> item.first == enteredID } }
                ?.let {
                    println(getMealByIdUseCase.getMealById(enteredID).getFullDetails())
                } ?: throw InvalidIdException()
        } ?: throw WrongInputException()
    }


}