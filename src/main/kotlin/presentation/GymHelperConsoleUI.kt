package presentation

import logic.usecase.GymHelperUseCase
import model.Food

class GymHelperConsoleUI(
    private val gymHelperUseCase: GymHelperUseCase,
) {
    fun start() {
        try {
            print("\nEnter desired calories: ")
            val calories = readlnOrNull()?.toFloatOrNull() ?: throw Exception("Invalid input")
            print("Enter desired proteins: ")
            val proteins = readlnOrNull()?.toFloatOrNull() ?: throw Exception("Invalid input")
            println()
            gymHelperUseCase.getListOfMealsForGym(calories, proteins).forEach { food: Food -> food.print() }
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}