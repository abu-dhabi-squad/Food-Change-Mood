package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException
import logic.usecase.search.StringSearchAlgorithm

class GetMealBySearchNameUseCase(
    private val foodRepository: FoodRepository,
    private val stringSearchAlgorithm: StringSearchAlgorithm

) {

    fun findMealsByName(input: String): List<Food> {
        return foodRepository.getFoods()
            .getOrThrow()
            .filter{ isMatchingMealByName(it, input) }
            .takeIf { it.isNotEmpty() }
            ?: throw NoMealsFoundException()
    }

    private fun isMatchingMealByName(food: Food, input: String): Boolean {
        val mealName = food.name?.lowercase() ?: return false
        return stringSearchAlgorithm.isContainsPattern(input, mealName.lowercase())
    }

}