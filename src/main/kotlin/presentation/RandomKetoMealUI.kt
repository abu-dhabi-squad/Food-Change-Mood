package presentation

import logic.usecase.GetRandomKetoDietMealsUseCase
import model.Food
import util.showDetails

class RandomKetoMealUI(
    private val getRandomKetoDietMealsUseCase: GetRandomKetoDietMealsUseCase,
) : ChangeFoodMoodLauncher {
    private lateinit var ketoMeal: Food
    private val showedKetoMeals = mutableListOf<Int>()

    override fun launchUI() {
        try {
            ketoMeal = getRandomKetoDietMealsUseCase(showedKetoMeals)
            showedKetoMeals.add(ketoMeal.id)
            println(ketoMeal.name)
            if (isLikedMeal()) {
                ketoMeal.showDetails()
            } else {
                launchUI()
            }

        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}
