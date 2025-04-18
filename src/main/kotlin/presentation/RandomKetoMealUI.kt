package presentation

import logic.usecase.GetRandomKetoDietMealsUseCase
import model.Food
import model.WrongInputException

class RandomKetoMealUI(
    private val getRandomKetoDietMealsUseCase: GetRandomKetoDietMealsUseCase,
) {
    var ketoMeals: List<Food>

    init {
        ketoMeals = try {
            getRandomKetoDietMealsUseCase()
        } catch (ex: Exception) {
            emptyList()
        }
    }

    fun start() {
        if (ketoMeals.isEmpty())
            println("No Keto Diet Meals Found.")
        else
            showKetoDietMeals()
    }

    private fun showKetoDietMeals(){
        for (randomKetoMeal in ketoMeals) {
            println(randomKetoMeal.name)
            try {
                if (isTheMealLikable()) {
                    randomKetoMeal.showDetails()
                    break
                }
            }catch (ex:WrongInputException){
                println(ex.message)
            }
        }
    }
}
