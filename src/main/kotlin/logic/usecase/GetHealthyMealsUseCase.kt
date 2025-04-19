package logic.usecase

import logic.repository.FoodRepository
import model.EmptyHealthFoodListListException
import model.Food

class GetHealthyMealsUseCase(private val foodRepository: FoodRepository) {

    fun fetchHealthyFastFoods(): List<Food> {
        return foodRepository.getFoods().getOrThrow()
            .filter { meal -> meal.minutes <= MAX_DURATION_MINUETS }
            .takeIf { meals -> meals.isNotEmpty() }
            ?.let { fastFoods ->
                val thresholds = NutritionThresholds.from(fastFoods)
                fastFoods.filter {
                    with(it.nutrition) {
                        totalFat <= thresholds.fat &&
                        saturated <= thresholds.saturatedFat &&
                        carbohydrates <= thresholds.carbohydrates
                    }
                }.takeIf { it.isNotEmpty() }?:throw EmptyHealthFoodListListException()
            } ?: throw EmptyHealthFoodListListException()
    }

    private data class NutritionThresholds(
        val fat: Float,
        val saturatedFat: Float,
        val carbohydrates: Float
    ) {
        companion object {
            fun from(foods: List<Food>) = NutritionThresholds(
                fat = foods.percentile25 { it.nutrition.totalFat },
                saturatedFat = foods.percentile25 { it.nutrition.saturated },
                carbohydrates = foods.percentile25 { it.nutrition.carbohydrates }
            )
        }
    }

    companion object {
        private const val MAX_DURATION_MINUETS = 15
    }
}

private fun List<Food>.percentile25(selector: (Food) -> Float): Float {
    return this.map(selector).sorted()[size / 4]
}



