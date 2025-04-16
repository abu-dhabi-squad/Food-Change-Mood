package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException
import kotlin.math.absoluteValue

class GymHelperUseCase(
    private val foodRepository: FoodRepository
) {
    fun getListOfMealsForGym(calories: Float, protein: Float): List<Food> {
        return foodRepository.getFoods().fold(
            onSuccess = { meals ->
                meals.filter { meal -> isSuitableMeal(meal, calories, protein) }
                    .takeIf { foods: List<Food> -> foods.isNotEmpty() }
                    ?.sortedBy { food: Food -> calculateMealScore(food, calories, protein) }
                    ?: throw NoMealsFoundException()
            },
            onFailure = { throw it },
        )
    }

    private fun isSuitableMeal(meal: Food, calories: Float, protein: Float): Boolean {
        return ((meal.nutrition.calories - calories).absoluteValue / calories) <= ACCEPTABLE_DIFFERENCE_PERCENTAGE
                && ((meal.nutrition.protein - protein).absoluteValue / protein) <= ACCEPTABLE_DIFFERENCE_PERCENTAGE
    }

    private fun calculateMealScore(meal: Food, calories: Float, protein: Float): Float {
        return (((meal.nutrition.calories - calories).absoluteValue / calories)
         + ((meal.nutrition.protein - protein).absoluteValue / protein)) / 2
    }

    companion object {
        private const val ACCEPTABLE_DIFFERENCE_PERCENTAGE = 0.1F // = 10 %
    }
}