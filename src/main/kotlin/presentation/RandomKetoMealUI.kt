package presentation

import logic.usecase.GetRandomKetoDietMealsUseCase
import model.Food

class RandomKetoMealUI(
    private val getRandomKetoDietMealsUseCase: GetRandomKetoDietMealsUseCase,
) {
    private lateinit var ketoMeal: Food
    private val showedKetoMeals = mutableListOf<Int>()

    fun start() {
        showKetoDietMeals()
    }

    private fun showKetoDietMeals(){
        try {
            ketoMeal = getRandomKetoDietMealsUseCase(showedKetoMeals)
            showedKetoMeals.add(ketoMeal.id)
            println(ketoMeal.name)
            if (isLikedMeal()) {
                ketoMeal.showDetails()
            } else {
                showKetoDietMeals()
            }

        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}
