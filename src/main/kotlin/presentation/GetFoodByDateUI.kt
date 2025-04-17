package presentation

import logic.usecase.GetFoodByDateUseCase
import model.InvalidIdException
import model.WrongInputException
import util.DateParserInterface
import util.GetFoodByDateValidationInterface
import java.time.LocalDate

class GetFoodByDateUI(
    private val dateParserInterface: DateParserInterface,
    private val getFoodByDateUseCase: GetFoodByDateUseCase,
    private val getFoodByDateValidationInterface: GetFoodByDateValidationInterface
)
{
    fun runUI() {
        try {
            getFoodByDateUseCase.getMealsByDate(getInputDate())
                .let { mealsByDate ->
                    mealsByDate.forEach { it -> println("id : " + it.first + " - name: " + it.second) }
                    getDetailsOfMeals(mealsByDate)
                }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun getInputDate(): LocalDate {
        print("Enter the Date (yyyy-MM-dd) : ")
        readLine()?.let { date ->
            getFoodByDateValidationInterface.isValidDate(date)
            return dateParserInterface.parseDateFromString(date)
        }
        throw WrongInputException()
    }

    private fun getDetailsOfMeals(mealsByDate: List<Pair<Int, String>>) {

        print("do you want to see details of one of the Meals (Y/N) : ")
        readLine().let {
            when {
                it.equals("y", true) -> {
                    getDetailsById(mealsByDate)
                    getDetailsOfMeals(mealsByDate)
                }
                it.equals("n", true) -> {
                    return
                }
                else -> {
                    throw WrongInputException()
                }
            }
        }
    }

    private fun getDetailsById(mealsByDate: List<Pair<Int, String>>) {
        print("enter id of the meal : ")
        readLine()?.toIntOrNull()?.let { enteredID ->
            mealsByDate.takeIf { it.any { item -> item.first == enteredID } }
                ?.let {
                    getFoodByDateUseCase.getMealById(enteredID).showDetails()
                    } ?: throw InvalidIdException()
        }
    }
}

