package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException
import logic.usecase.search.SearchAlgorithm

class GetMealBySearchNameUseCase(
    private val foodRepository: FoodRepository,
    private val searchAlgorithm: SearchAlgorithm

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
        return searchAlgorithm.isContainsPattern(input, mealName.lowercase())
    }

}