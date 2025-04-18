package logic.usecase

import logic.repository.FoodRepository
import model.Food

class GetSeaFoodMealsSortedByProteinUseCase(private val foodRepository: FoodRepository) {
    operator fun invoke(): List<Food> {
        return foodRepository.getFoods()
            .getOrThrow()
            .filter { meal -> meal.tags.any { it.contains("seafood", ignoreCase = true) } }
            .sortedByDescending { it.nutrition.protein }
    }
}