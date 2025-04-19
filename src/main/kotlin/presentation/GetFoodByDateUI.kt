package presentation

import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealByIdUseCase
import model.InvalidIdException
import model.WrongInputException
import util.DateParserInterface
import util.GetFoodByDateValidationInterface
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetFoodByDateUI(
    private val dateParserInterface: DateParserInterface,
    private val getFoodByDateUseCase: GetFoodByDateUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val getFoodByDateValidationInterface: GetFoodByDateValidationInterface
) {
    fun runUI() {
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
        return readlnOrNull()
            ?.takeIf { date -> getFoodByDateValidationInterface.isValidDate(date) }
            ?.let { date -> dateParserInterface.parseDateFromString(date) }
            ?: throw WrongInputException()
    }

    private fun getDetailsOfMeals(mealsByDate: List<Pair<Int, String>>) {
        while (true) {
            print("Do you want to see details of one of the Meals (Y/N)? ")
            when (readlnOrNull()?.trim()?.lowercase()) {
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
        readlnOrNull()?.toIntOrNull()?.let { enteredID ->
            mealsByDate.takeIf { it.any { item -> item.first == enteredID } }
                ?.let {
                    getMealByIdUseCase.getMealById(enteredID).showDetails()
                } ?: throw InvalidIdException()
        }?: throw WrongInputException()
    }
}