package logic.usecase

import logic.repository.FoodRepository
import model.NoMealsFoundException


class GetRandomPotatoesMealsUseCase(
    private val foodRepository: FoodRepository
) {

    fun getTenRandomPotatoesMeals(): List<String> {
        val mealsResult = foodRepository.getFoods().getOrThrow()

        val filteredNames = mealsResult
            .filter {
                !it.name.isNullOrBlank() &&
                        it.ingredients.any { ingredient ->
                            ingredient.contains(POTATOES_KEYWORD, ignoreCase = true)
                        }
            }
            .takeIf { it.isNotEmpty() }
            ?.mapNotNull { it.name }
            ?.distinct() ?: throw NoMealsFoundException()


        val randomSet = mutableSetOf<String>()
        while (randomSet.size < MEALS_COUNT && randomSet.size < filteredNames.size) {
            val randomMeal = filteredNames.random()
            randomSet.add(randomMeal)
        }

        return randomSet.toList()
    }

    companion object {
        const val MEALS_COUNT = 10
        const val POTATOES_KEYWORD = "potatoes"
    }
}