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
        if (!isValidSugarAmount(nutrition.sugar))
            return false

        val isKeto = isValidFatPercentage(nutrition.totalFat, nutrition.calories) &&
                isValidProteinPercentage(nutrition.protein, nutrition.calories) &&
                isValidCarbPercentage(nutrition.carbohydrates, nutrition.calories) &&
                isValidSaturatedFatPercentage(nutrition.saturated, nutrition.totalFat)

        return isKeto
    }

    private fun isValidSugarAmount(sugar: Float) = sugar < 5f

    private fun isValidFatPercentage(totalFat: Float, calories: Float): Boolean {
        val fatCalories = totalFat * FAT_CALORIES_BER_GRAM
        val fatPercentage = fatCalories / calories * 100
        return fatPercentage >= 60f
    }

    private fun isValidProteinPercentage(protein: Float, calories: Float): Boolean {
        val proteinCalories = protein * PROTEIN_CALORIES_BER_GRAM
        val proteinPercentage = proteinCalories / calories * 100
        return proteinPercentage <= 25f
    }

    private fun isValidCarbPercentage(carb: Float, calories: Float): Boolean {
        val carbCalories = carb * CARB_CALORIES_BER_GRAM
        val carbPercentage = carbCalories / calories * 100
        return carbPercentage <= 10f
    }

    private fun isValidSaturatedFatPercentage(saturated: Float, totalFat: Float): Boolean {
        val saturatedFatPercentage = saturated / totalFat * 100
        return saturatedFatPercentage in 20f..25f
    }

    companion object {
        const val FAT_CALORIES_BER_GRAM = 9f
        const val PROTEIN_CALORIES_BER_GRAM = 4f
        const val CARB_CALORIES_BER_GRAM = 4f

    }


}