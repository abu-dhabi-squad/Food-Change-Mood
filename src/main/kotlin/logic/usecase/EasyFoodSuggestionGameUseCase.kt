package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoEasyMealsFoundException

class EasyFoodSuggestionGameUseCase(
    private val foodRepository: FoodRepository
) {
    fun suggestRandomEasyMeals(count: Int = DEFAULT_SUGGESTED_MEALS_COUNT): List<Food>{

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
                food.minutes <= MAX_MINUTES &&
                food.ingredients.size <= MAX_INGREDIENTS &&
                food.steps.size <= MAX_STEPS
    }

    companion object {
        private const val DEFAULT_SUGGESTED_MEALS_COUNT = 10
        private const val MAX_MINUTES = 30
        private const val MAX_INGREDIENTS = 5
        private const val MAX_STEPS = 6
    }

}