package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.Nutrition

class GetRandomKetoDietMealsUseCase(private val foodRepository: FoodRepository) {
    operator fun invoke(): List<Food> {
        return foodRepository.getFoods()
            .getOrThrow()
            .filter { isKetoDietMeal(it.nutrition) }
            .shuffled()

    }

    private fun isKetoDietMeal(
        nutrition: Nutrition
    ): Boolean {
        val calories = nutrition.calories
        val carbohydrates = nutrition.carbohydrates
        val fatCalories = nutrition.totalFat * FAT_CALORIES_BER_GRAM
        val proteinCalories = nutrition.protein * PROTEIN_CALORIES_BER_GRAM
        val carbCalories = carbohydrates * CARB_CALORIES_BER_GRAM

        val fatPercentage = fatCalories / calories * 100
        val proteinPercentage = proteinCalories / calories * 100
        val carbPercentage = carbCalories / calories * 100
        val saturatedFitPercentage = nutrition.saturated / nutrition.totalFat * 100
        val isKeto = fatPercentage >= 60f &&
                     proteinPercentage <= 25f &&
                     carbPercentage <= 10f &&
                     carbohydrates <= 20f &&
                     saturatedFitPercentage in 20f .. 25f

        if (nutrition.sugar > 5f) return false

        return isKeto
    }

    companion object {
        const val FAT_CALORIES_BER_GRAM = 9f
        const val PROTEIN_CALORIES_BER_GRAM = 4f
        const val CARB_CALORIES_BER_GRAM = 4f

    }


}