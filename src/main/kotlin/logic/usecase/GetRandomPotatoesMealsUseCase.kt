package logic.usecase

import logic.repository.FoodRepository


class GetRandomPotatoesMealsUseCase(
    private val foodRepository: FoodRepository
) {

    fun getTenRandomPotatoesMeals(): List<String> {
        return try {
            foodRepository
                .getFoods()
                .fold(
                    onFailure = {
                        throw Exception("error in getting meals")
                    },
                    onSuccess = { meals ->
                        meals
                            .filter {
                                !it.name.isNullOrBlank() && it.ingredients.any {
                                    it.contains(
                                        POTATOES_KEYWORD,
                                        ignoreCase = true
                                    )
                                }
                            }
                            .shuffled()
                            .take(MEALS_COUNT)
                            .map { it.name.toString() }
                    })
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        const val MEALS_COUNT = 10
        const val POTATOES_KEYWORD = "potatoes"
    }
}