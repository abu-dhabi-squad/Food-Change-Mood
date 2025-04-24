package logic.usecase
import logic.repository.FoodRepository
import model.Food
import model.NoIraqiMealsFoundException

class GetIraqiMealsUseCase(
    private val foodRepository: FoodRepository,
) {

    fun getAllIraqiMeals(): List<Food> {
        return foodRepository.getFoods()
            .getOrThrow()
            .filter(::isValidMeal)
            .filter(::isOnlyIraqiMeal)
            .map(::normalizeFood)
            .takeIf { it.isNotEmpty() }
            ?: throw NoIraqiMealsFoundException()
    }

    private fun isValidMeal(food: Food): Boolean {
        return !(food.name.isNullOrEmpty() && food.description.isNullOrEmpty())
    }

    private fun isOnlyIraqiMeal(food: Food): Boolean {
        return (food.name?.contains("Iraq", ignoreCase = true) == true ||
                food.description?.contains("Iraq", ignoreCase = true) == true || food.tags.any { it.contains("Iraq", ignoreCase = true) })
    }

    private fun normalizeFood(food: Food): Food {
        return food.copy(
            name = food.name.takeUnless { it.isNullOrEmpty() } ?: "unnamed",
            description = food.description.takeUnless { it.isNullOrEmpty() } ?: "no description"
        )
    }
}
