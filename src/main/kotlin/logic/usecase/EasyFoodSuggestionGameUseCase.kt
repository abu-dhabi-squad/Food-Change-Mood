package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoEasyMealsFoundException
import logic.takeShuffled

class EasyFoodSuggestionGameUseCase(
    private val foodRepository: FoodRepository
) {
    fun suggestRandomEasyMeals(count: Int = DEFAULT_SUGGESTED_MEALS_COUNT): List<Food>{
        return foodRepository.getFoods()
            .getOrThrow()
            .filter(::isValidEasyMeal)
            .takeIf { it.isNotEmpty() }
            ?.takeShuffled(count)
            ?: throw NoEasyMealsFoundException()
    }

    private fun isValidEasyMeal(food: Food): Boolean {
        return food.name != null &&
                food.description != null &&
                food.minutes <= MAX_MINUTES &&
                food.ingredients.isNotEmpty()&&
                food.ingredients.size <= MAX_INGREDIENTS &&
                food.steps.isNotEmpty() &&
                food.steps.size <= MAX_STEPS
    }

    companion object {
        private const val DEFAULT_SUGGESTED_MEALS_COUNT = 10
        private const val MAX_MINUTES = 30
        private const val MAX_INGREDIENTS = 5
        private const val MAX_STEPS = 6
    }

}