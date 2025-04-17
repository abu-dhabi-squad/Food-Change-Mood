package logic.usecase

import logic.repository.FoodRepository
import model.Food

class EasyFoodSuggestionGameUseCase(
    private val foodRepository: FoodRepository
) {
    fun suggest10RandomEasyMeals(): List<Food>{

        return foodRepository.getFoods()
            .getOrThrow()
            .filter { food ->
                food.description != null &&
                    food.minutes <= 30 &&
                        food.ingredients.size <= 5 &&
                        food.steps.size <= 6 }
            .shuffled()
            .take(10)
    }

}