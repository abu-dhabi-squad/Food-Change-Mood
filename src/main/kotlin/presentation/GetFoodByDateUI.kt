package presentation

import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealByIdUseCase
import model.InvalidIdException
import model.WrongInputException
import presentation.ui_io.InputReader

import util.DateParserInterface
import util.GetFoodByDateValidationInterface
import java.time.LocalDate

class GetFoodByDateUI(
    private val dateParserInterface: DateParserInterface,
    private val getFoodByDateUseCase: GetFoodByDateUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val getFoodByDateValidationInterface: GetFoodByDateValidationInterface,
    private val intReader: InputReader<Int>,
    private val stringReader: InputReader<String>
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            getFoodByDateUseCase.getMealsByDate(getInputDate())
                .let { mealsByDate ->
                    mealsByDate.forEach { (id, name) -> println("id: $id - name: $name") }
                    getDetailsOfMeals(mealsByDate)
                }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun getInputDate(): LocalDate {
        print("Enter the Date (yyyy-M-d) : ")
        return stringReader.read()
            ?.takeIf { date -> getFoodByDateValidationInterface.isValidDate(date) }
            ?.let { date -> dateParserInterface.parseDateFromString(date) }
            ?: throw WrongInputException()
    }

    private fun getDetailsOfMeals(mealsByDate: List<Pair<Int, String>>) {
        while (true) {
            print("Do you want to see details of one of the Meals (Y/N)? ")
            val input = stringReader.read()?.trim()?.lowercase()
            when (input) {
                "y" -> try {
                    getDetailsById(mealsByDate)
                } catch (e: Exception) {
                    println(e.message)
                }

                "n" -> return
                else -> println("Please enter Y or N.")
            }
        }
    }

    private fun getDetailsById(mealsByDate: List<Pair<Int, String>>) {
        print("enter id of the meal : ")
        intReader.read()?.let { enteredID ->
            mealsByDate.takeIf { it.any { item -> item.first == enteredID } }
                ?.let {
                    println(getMealByIdUseCase.getMealById(enteredID).getFullDetails())
                } ?: throw InvalidIdException()
        } ?: throw WrongInputException()
    }


}