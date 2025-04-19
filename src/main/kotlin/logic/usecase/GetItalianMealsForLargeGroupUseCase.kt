package logic.usecase

import logic.repository.FoodRepository
import model.Food

class GetItalianMealsForLargeGroupUseCase(
    private val foodRepository : FoodRepository
) {

    fun getItalianMealForLargeGroup(): List<Pair<String?, String?>> {

        return foodRepository.getFoods().getOrThrow()
            .filter { onlyHighQualityData(it) && onlyItalianMealForLargeGroup(it) }
            .map { it.name to it.description }
    }

    private fun onlyItalianMealForLargeGroup(food: Food): Boolean{
        return (food.tags.contains(ITALIAN_MEALS) && food.tags.contains(FOR_LARGE_GROUPS))
    }

    private fun onlyHighQualityData(input: Food): Boolean{
        return input.name != null && input.description != null
    }

    companion object{
        private const val ITALIAN_MEALS = "italian"
        private const val FOR_LARGE_GROUPS = "for-large-groups"
    }
}