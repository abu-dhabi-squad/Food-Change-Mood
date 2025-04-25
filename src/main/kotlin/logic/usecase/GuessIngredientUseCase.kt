package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.IngredientQuestion
import model.NoMealsFoundException
import logic.takeShuffled

class GuessIngredientUseCase(
    private val foodRepository: FoodRepository
) {
    fun guessIngredient(incorrectAnswersCount: Int, askedQuestions: Set<Int>): IngredientQuestion {
        return getRandomMeal(askedQuestions).let { meals ->
            val meal = meals.random()
            IngredientQuestion(
                mealId = meal.id,
                mealName = meal.name.toString(),
                correctAnswer = meal.ingredients.random(),
                answers = getIngredients(meals).filter(isNotInMeal(meal)).take(incorrectAnswersCount)
            )
        }
    }

    private fun getRandomMeal(askedQuestions: Set<Int>): List<Food> {
        return foodRepository
            .getFoods()
            .getOrThrow()
            .filter { meal -> isValidMeal(meal) && !askedQuestions.contains(meal.id)}
            .takeIf { meals -> meals.isNotEmpty() }
            ?.takeShuffled(MAXIMUM_SHUFFLED_MEALS)
            ?: throw NoMealsFoundException()
    }

    private fun isValidMeal(meal: Food): Boolean {
        return meal.name?.isNotBlank() == true && meal.ingredients.isNotEmpty()
    }

    private fun getIngredients(meals: List<Food>): Set<String> {
        return meals.map { meal ->
            meal.ingredients
        }.flatten().toSet()
    }

    private fun isNotInMeal(meal: Food): (String) -> Boolean {
        return { ingredient -> ingredient !in meal.ingredients }
    }

    companion object {
        private const val MAXIMUM_SHUFFLED_MEALS = 15
    }
}