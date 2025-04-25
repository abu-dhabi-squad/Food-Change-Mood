package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoEasyMealsFoundException
import logic.takeShuffled

class GetEasyFoodSuggestionGameUseCase(
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
        return isNameAndDescriptionValid(food) &&
                isTimeValid(food) &&
                isIngredientsValid(food) &&
                isStepsValid(food)
    }

    private fun isNameAndDescriptionValid(food: Food): Boolean {
        return food.name != null && food.description != null
    }

    private fun isTimeValid(food: Food): Boolean {
        return food.minutes <= MAX_MINUTES
    }

    private fun isIngredientsValid(food: Food): Boolean {
        return food.ingredients.isNotEmpty() && food.ingredients.size <= MAX_INGREDIENTS
    }

    private fun isStepsValid(food: Food): Boolean {
        return food.steps.isNotEmpty() && food.steps.size <= MAX_STEPS
    }

    companion object {
        private const val DEFAULT_SUGGESTED_MEALS_COUNT = 10
        private const val MAX_MINUTES = 30
        private const val MAX_INGREDIENTS = 5
        private const val MAX_STEPS = 6
    }

}