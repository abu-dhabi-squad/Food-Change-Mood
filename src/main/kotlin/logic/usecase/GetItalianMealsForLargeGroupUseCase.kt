package logic.usecase

import logic.repository.FoodRepository
import model.EmptyListException
import model.Food

class GetItalianMealsForLargeGroupUseCase(
    private val foodRepository: FoodRepository
) {

    fun getItalianMealForLargeGroup(): List<Pair<String?, String?>> {

        return foodRepository.getFoods().getOrThrow()
            .filter { isOnlyHighQualityData(it) && isOnlyItalianMealForLargeGroup(it) }
            .map { it.name to it.description }
            .takeIf { it.isNotEmpty() }
            ?: throw EmptyListException()
    }

    private fun isOnlyItalianMealForLargeGroup(food: Food): Boolean {
        return (food.tags.contains(ITALIAN_MEALS) && food.tags.contains(FOR_LARGE_GROUPS))
    }

    private fun isOnlyHighQualityData(input: Food): Boolean {
        return input.name != null && input.description != null
    }

    companion object {
        private const val ITALIAN_MEALS = "italian"
        private const val FOR_LARGE_GROUPS = "for-large-groups"
    }
}