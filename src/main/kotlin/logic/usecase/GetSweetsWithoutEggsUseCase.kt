package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException

class GetSweetsWithoutEggsUseCase(
    private val foodRepository: FoodRepository
) {
    fun getSweetsWithoutEggs(): List<Food> {
        return foodRepository.getFoods().getOrThrow()
            .filter(::isValidMealWithoutEgg)
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?: throw NoMealsFoundException()
    }

    private fun isValidMealWithoutEgg(meal: Food): Boolean {
        return !meal.name.isNullOrBlank() && !meal.description.isNullOrBlank() && meal.ingredients.isNotEmpty() && !meal.ingredients.contains(EGG_KEYWORD)
    }

    companion object {
        const val EGG_KEYWORD = "egg"
    }
}