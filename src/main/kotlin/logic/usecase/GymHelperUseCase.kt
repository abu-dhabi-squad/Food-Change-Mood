package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException
import kotlin.math.absoluteValue

class GymHelperUseCase(
    private val foodRepository: FoodRepository
) {
    fun getListOfMealsForGym(calories: Float, protein: Float): List<Food> {
        return foodRepository.getFoods().getOrThrow()
            .filter { meal -> isSuitableMeal(meal, calories, protein) }
            .takeIf { foods: List<Food> -> foods.isNotEmpty() }
            ?.sortedBy { food: Food -> calculateMealScore(food, calories, protein) }
            ?: throw NoMealsFoundException()
    }

    private fun isSuitableMeal(meal: Food, calories: Float, protein: Float): Boolean {
        return getPercentage(calories, meal.nutrition.calories) <= ACCEPTABLE_DIFFERENCE_PERCENTAGE
                && (getPercentage(protein, meal.nutrition.protein)) <= ACCEPTABLE_DIFFERENCE_PERCENTAGE
    }

    private fun calculateMealScore(meal: Food, calories: Float, protein: Float): Float {
        return (getPercentage(calories, meal.nutrition.calories) + (getPercentage(protein, meal.nutrition.protein))) / 2
    }

    private fun getPercentage(baseNum: Float, otherNum: Float): Float {
        return (baseNum - otherNum).absoluteValue / baseNum
    }

    companion object {
        private const val ACCEPTABLE_DIFFERENCE_PERCENTAGE = 0.1F // = 10 %
    }
}