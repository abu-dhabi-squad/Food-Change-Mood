package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.Nutrition

class GetKetoDietMealsUseCase(private val foodRepository: FoodRepository) {
    operator fun invoke(): List<Food> {
        return foodRepository.getFoods()
            .getOrThrow()
            .filter { isKetoDietMeal(it.nutrition) }
    }

    private fun isKetoDietMeal(
        nutrition: Nutrition
    ): Boolean {
        val calories = nutrition.calories
        val carbohydrates = nutrition.carbohydrates
        val fatCalories = nutrition.totalFat * 9f
        val proteinCalories = nutrition.protein * 4f
        val carbCalories = carbohydrates * 4f

        val fatPercentage = fatCalories / calories * 100
        val proteinPercentage = proteinCalories / calories * 100
        val carbPercentage = carbCalories / calories * 100

        val isKeto = fatPercentage >= 60f &&
                proteinPercentage <= 25f &&
                carbPercentage <= 10f &&
                carbohydrates <= 20f

        if (nutrition.sugar > 5f) return false

        return isKeto
    }
}