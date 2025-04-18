package logic.usecase

import logic.repository.FoodRepository
import model.Food

class GetHealthyMealsUseCase(private val foodRepository: FoodRepository) {

    fun fetchHealthyFastFoods(): List<Food> {
        return foodRepository.getFoods().getOrThrow()
            .filter { meal -> meal.minutes <= MAX_DURATION_M }
            .takeIf { meals -> meals.isNotEmpty() }?.let { fastFoods ->
                val fatThreshold = fastFoods.percentile25 { meal -> meal.nutrition.totalFat }
                val satFatThreshold = fastFoods.percentile25 { meal -> meal.nutrition.saturated }
                val carbThreshold = fastFoods.percentile25 { meal -> meal.nutrition.carbohydrates }

                fastFoods.filter {
                    with(it.nutrition) {
                        totalFat <= fatThreshold &&
                                saturated <= satFatThreshold &&
                                carbohydrates <= carbThreshold
                    }
                }
            } ?: emptyList()
    }

    companion object {
        private const val MAX_DURATION_M = 15
    }
}

fun List<Food>.percentile25(selector: (Food) -> Float): Float {
    return this.map(selector).sorted()[size / 4]
}


