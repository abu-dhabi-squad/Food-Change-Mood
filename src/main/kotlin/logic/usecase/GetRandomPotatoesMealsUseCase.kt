package logic.usecase

import logic.repository.FoodRepository

class GetRandomPotatoesMealsUseCase(
    private val foodRepository: FoodRepository
) {
    fun getTenRandomPotatoesMeals(): List<String> {
        return foodRepository.getFoods().getOrThrow()
            .filter {
                hasValidName(it.name) && isIngredientsContainsPotatoes(it.ingredients)
            }
            .shuffled()
            .take(MEALS_COUNT)
            .map { it.name!! }
    }

    private fun hasValidName(name: String?): Boolean {
        return !name.isNullOrBlank()
    }

    private fun isIngredientsContainsPotatoes(ingredients: List<String>): Boolean {
        return ingredients.any { it.contains(POTATOES_KEYWORD, ignoreCase = true) }
    }

    companion object {
        const val MEALS_COUNT = 10
        const val POTATOES_KEYWORD = "potatoes"
    }
}