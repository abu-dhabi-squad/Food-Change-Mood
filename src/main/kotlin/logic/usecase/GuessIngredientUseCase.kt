package logic.usecase

import logic.repository.FoodRepository
import model.Food
import model.IngredientQuestion
import model.NoMealsFoundException

class GuessIngredientUseCase(
    private val foodRepository: FoodRepository
) {
    fun guessIngredient(): List<IngredientQuestion> {
        return getRandomMeals().map { meal ->
            IngredientQuestion(
                mealName = meal.name.toString(),
                correctAnswer = meal.ingredients.random(),
                answers = getIngredients().filter { ingredient ->
                    ingredient !in meal.ingredients
                }.take(MAXIMUM_INCORRECT_ANSWERS)
            )
        }
    }

    private fun getRandomMeals(): List<Food> {
        return foodRepository
            .getFoods()
            .getOrThrow()
            .filter { meal ->
                meal.name.toString().isNotEmpty()
                        && meal.ingredients.isNotEmpty()
            }
            .shuffled()
            .takeIf { meals ->
                meals.isNotEmpty()
            }
            ?.take(MAXIMUM_QUESTIONS)
            ?: throw NoMealsFoundException()
    }

    private fun getIngredients(): Set<String> {
        return getRandomMeals().map { meals ->
            meals.ingredients
        }.flatten().toSet()
    }

    companion object {
        private const val MAXIMUM_QUESTIONS = 15
        private const val MAXIMUM_INCORRECT_ANSWERS = 2
    }
}