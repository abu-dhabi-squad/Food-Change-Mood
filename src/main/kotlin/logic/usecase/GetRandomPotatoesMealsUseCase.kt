package logic.usecase

import logic.repository.FoodRepository

class GetRandomPotatoesMealsUseCase(
    private val foodRepository: FoodRepository
) {
    fun getRandomPotatoesMeals(): List<String> {
        return foodRepository.getFoods().getOrThrow()
            .filter {
                hasValidName(it.name) && isIngredientsContainsPotatoes(it.ingredients)
            }
            .shuffled()
            .take(MEALS_COUNT)
            .mapNotNull { it.name }
    }

    private fun hasValidName(name: String?): Boolean = !name.isNullOrBlank()

    private fun isIngredientsContainsPotatoes(ingredients: List<String>): Boolean {
        return ingredients.any { it.contains(POTATOES_KEYWORD, ignoreCase = true) }
    }

    companion object {
        private const val MEALS_COUNT = 10
        private const val POTATOES_KEYWORD = "potatoes"
    }
}