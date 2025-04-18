package presentation

import logic.usecase.GetKetoDietMealsUseCase
import logic.usecase.GetRandomKetoMealUseCase
import model.Food
class RandomKetoMealUI(
    private val getKetoDietMealsUseCase: GetKetoDietMealsUseCase,
    private val getRandomKetoMealUseCase: GetRandomKetoMealUseCase
) {
    lateinit var ketoMeals: List<Food>
    private val shownKetoMealsId = mutableListOf<Int>()

    init {
        ketoMeals = getKetoDietMealsUseCase()
    }

    fun start() {
        try {
            val food = getRandomKetoMealUseCase(ketoMeals, shownKetoMealsId)
            println(food.name)
            shownKetoMealsId.add(food.id)
            when (isTheMealLikable()) {
                true -> showMealDetails(food)
                false -> start()
            }
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

}
