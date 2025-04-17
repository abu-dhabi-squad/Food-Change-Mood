package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoIraqiMealsFoundException

class GetIraqiMealsUseCase(
    private val foodRepository: FoodRepository
) {

    private val shownMeals = mutableSetOf<Int>()

    fun getIraqMeal(): Food {
        return foodRepository.getFoods()
            .getOrThrow()
            .filter(::onlyIraqiMealsData)
            .filterNot { shownMeals.contains(it.id) }
            .takeIf { meals -> meals.isNotEmpty() }
            ?.random()
            ?.also { shownMeals.add(it.id) }
            ?: throw NoIraqiMealsFoundException()
    }

    fun getAllIraqiMeals(): List<Food> {
        return foodRepository.getFoods()
            .getOrThrow()
            .filter(::onlyIraqiMealsData)
            .takeIf { meals -> meals.isNotEmpty() }
            ?: throw NoIraqiMealsFoundException()

    }

    private fun onlyIraqiMealsData(food: Food): Boolean {
        return (food.name?.contains("Iraq", ignoreCase = true) == true ||
                food.description?.contains("Iraq", ignoreCase = true) == true)
    }
}
