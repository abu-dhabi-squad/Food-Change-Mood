package logic.usecase

import logic.repository.FoodRepository
import model.EmptySearchByDateListException
import model.Food

class GetSeaFoodMealsSortedByProteinUseCase(private val foodRepository: FoodRepository) {
    operator fun invoke(): List<Food> {
        return foodRepository.getFoods()
            .getOrThrow()
            .filter { meal -> meal.tags.any { it.contains(SEAFOOD, ignoreCase = true) } }
            .sortedByDescending { it.nutrition.protein }
            .takeIf { it.isNotEmpty() }
            ?: throw EmptySearchByDateListException()
    }

    companion object {
        const val SEAFOOD = "seafood"
    }
}