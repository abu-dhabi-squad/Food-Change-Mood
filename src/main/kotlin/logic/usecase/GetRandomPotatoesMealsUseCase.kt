package logic.usecase

import logic.repository.FoodRepository
import model.NoMealsFoundException


class GetRandomPotatoesMealsUseCase(
    private val foodRepository: FoodRepository
) {
    fun getTenRandomPotatoesMeals(): List<String> {
        return foodRepository.getFoods().getOrThrow()
            .filter {
                !it.name.isNullOrBlank() &&
                        it.ingredients.any { ingredient ->
                            ingredient.contains(POTATOES_KEYWORD, ignoreCase = true)
                        }
            }
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?.take(MEALS_COUNT)
            ?.map { it.name.toString() } ?: throw NoMealsFoundException()
    }

    companion object {
        const val MEALS_COUNT = 10
        const val POTATOES_KEYWORD = "potatoes"
    }
}