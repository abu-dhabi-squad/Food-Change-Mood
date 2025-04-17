package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoEasyMealsFoundException

class EasyFoodSuggestionGameUseCase(
    private val foodRepository: FoodRepository
) {
    fun suggestRandomEasyMeals(count: Int = 10): List<Food>{

        return foodRepository.getFoods()
            .getOrThrow()
            .filter(::isValidEasyMeal)
            .shuffled()
            .take(count)
            .takeIf { it.isNotEmpty() }
            ?: throw NoEasyMealsFoundException()
    }

    private fun isValidEasyMeal(food: Food): Boolean {
        return food.description != null &&
                food.minutes <= 30 && food.ingredients.size <= 5 && food.steps.size <= 6
    }

}