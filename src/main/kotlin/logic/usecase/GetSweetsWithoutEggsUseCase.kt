package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException

class GetSweetsWithoutEggsUseCase(
    private val foodRepository: FoodRepository
) {
    fun getSweetsWithoutEggs(shownMeals: Set<Int>): Food {
        return foodRepository.getFoods().getOrThrow()
            .filter { meal -> isValidMealWithoutEgg(meal) && isNotShownBefore(meal, shownMeals) }
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?: throw NoMealsFoundException()
    }

    private fun isValidMealWithoutEgg(meal: Food): Boolean {
        return isValidMeal(meal) && isSweet(meal) && isEggFree(meal)
    }

    private fun isNotShownBefore(meal: Food, shownMeals: Set<Int>): Boolean {
        return meal.id !in shownMeals
    }

    private fun isValidMeal(meal: Food) = !meal.name.isNullOrBlank() && !meal.description.isNullOrBlank()

    private fun isSweet(meal: Food) = meal.name?.lowercase()?.split(" ")?.contains(SWEET_KEYWORD) == true
            || meal.description?.lowercase()?.split(" ")?.contains(SWEET_KEYWORD) == true
            || meal.tags.any { it.equals(SWEET_KEYWORD, true) }

    private fun isEggFree(meal: Food) = meal.ingredients.isNotEmpty() && !meal.ingredients.contains(EGG_KEYWORD)

    companion object {
        const val EGG_KEYWORD = "egg"
        const val SWEET_KEYWORD = "sweet"
    }
}