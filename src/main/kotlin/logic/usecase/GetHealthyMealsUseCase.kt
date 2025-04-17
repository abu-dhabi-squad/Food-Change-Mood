package logic.usecase

import logic.repository.FoodRepository
import model.Food

class GetHealthyMealsUseCase (private val foodRepository: FoodRepository) {

    fun execute(): List<String> {
        val fastFoods = foodRepository.getFoods().getOrThrow()
            .filter { meal-> meal.minutes <= 15 }
        if (fastFoods.isEmpty()) return emptyList()

        val fatThreshold = fastFoods.percentile25 { meal ->  meal.nutrition.totalFat }
        val satFatThreshold = fastFoods.percentile25 { meal ->  meal.nutrition.saturated }
        val carbThreshold = fastFoods.percentile25 { meal ->  meal.nutrition.carbohydrates }

        return fastFoods.filter {
            with(it.nutrition) {
                totalFat <= fatThreshold &&
                saturated <= satFatThreshold &&
                carbohydrates <= carbThreshold
            }
        }.map { it.name ?: "none"}
    }

}

fun List<Food>.percentile25(selector: (Food) -> Float): Float {
    return this.map(selector).sorted()[size / 4]
}
