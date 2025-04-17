package logic

import logic.repository.FoodRepository
import model.Food

class GetItalianMealsForLargeGroupUsesCase(
    private val foodRepository : FoodRepository
) {

    fun getItalianMealForLargeGroup(): List<Pair<String?, String?>> {

        return foodRepository.getFoods().getOrThrow()
            .filter { onlyHighQualityData(it) && onlyItalianMealForLargeGroup(it) }
            .map { it.name to it.description }
    }

    private fun onlyItalianMealForLargeGroup(food: Food): Boolean{
        return (food.tags.contains("italian") && food.tags.contains("for-large-groups"))
    }

    private fun onlyHighQualityData(input: Food): Boolean{
        return input.name != null && input.description != null
    }
}