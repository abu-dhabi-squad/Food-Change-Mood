package presentation

import logic.usecase.GetKetoDietMealsUseCase
import logic.usecase.GetRandomKetoMealUseCase
import model.Food
class RandomKetoMealUI(
    private val getKetoDietMealsUseCase: GetKetoDietMealsUseCase,
    private val getRandomKetoMealUseCase: GetRandomKetoMealUseCase
) {
    var ketoMeals: List<Food>
    private val shownKetoMealsId = mutableListOf<Int>()

    init {
        ketoMeals = try {
            getKetoDietMealsUseCase()
        }catch (ex :Exception){
            emptyList()
        }
    }

    fun start() {
        try {
            val randomKetoMeal = getRandomKetoMealUseCase(ketoMeals, shownKetoMealsId)
            println(randomKetoMeal.name)
            shownKetoMealsId.add(randomKetoMeal.id)
            when (isTheMealLikable()) {
                true -> randomKetoMeal.showDetails()
                false -> start()
            }
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

}
