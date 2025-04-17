package presentation

import logic.usecase.GetIraqiMealsUseCase
import model.WrongInputException

fun getIraqiMealsUI(getIraqiMealsUseCase: GetIraqiMealsUseCase) {
    while (true) {
        println("Choose an option:")
        println("1- Show one Iraqi meal")
        println("2- Show all Iraqi meals")

        val userInput = readLine()

        when (userInput) {
            "1" -> {
                showIraqiMealsOneByOne(getIraqiMealsUseCase)
                break
            }
            "2" -> {
                showAllIraqiMeals(getIraqiMealsUseCase)
                break
            }
            else -> {
                println("Invalid input. Please choose 1 or 2.\n")
            }
        }
    }
}

private fun showIraqiMealsOneByOne(getIraqiMealsUseCase: GetIraqiMealsUseCase) {
    while (true) {
        try {
            val meal = getIraqiMealsUseCase.getIraqMeal()
            println("\nMeal Name: ${meal.name}")
            println("Meal Description: ${meal.description}\n")

            if (!wantsAnotherMeal()) {
                println("Thanks for using our feature!")
                break
            }
        } catch (e: Exception) {
            println(e.message)
            break
        }
    }
}

private fun wantsAnotherMeal(): Boolean {
    println("Do you want another Iraqi meal? (y/n)")
    val input = readLine()

    return when {
        input.equals("y", ignoreCase = true) -> true
        input.equals("n", ignoreCase = true) -> false
        else -> {
            println("Invalid input. Please answer with 'y' or 'n'.")
            wantsAnotherMeal()
        }
    }
}

private fun showAllIraqiMeals(getIraqiMealsUseCase: GetIraqiMealsUseCase) {
    try {
        val allMeals = getIraqiMealsUseCase.getAllIraqiMeals()
        println("\nAll Iraqi Meals Names:")
        allMeals.forEach { meal ->
            println("- ${meal.name}")
        }
        println()
    } catch (e: Exception) {
        println(e.message)
    }
}
