package presentation

import logic.usecase.GymHelperUseCase
import model.Food
import model.WrongInputException
import presentation.ui_io.FloatReader

class GymHelperConsoleUI(
    private val gymHelperUseCase: GymHelperUseCase,
    private val floatReader: FloatReader
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            print("\nEnter desired calories: ")
            val calories = floatReader.read() ?: throw WrongInputException()
            print("Enter desired proteins: ")
            val proteins = floatReader.read() ?: throw WrongInputException()
            println()
            gymHelperUseCase.getListOfMealsForGym(calories, proteins).forEach { food: Food -> println(food.getFullDetails()) }
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}