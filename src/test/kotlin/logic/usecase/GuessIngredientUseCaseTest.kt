package logic.usecase

import com.google.common.truth.Truth
import createMeal
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.NoMealsFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GuessIngredientUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var guessIngredientUseCase: GuessIngredientUseCase

    @BeforeEach
    fun setup() {
        foodRepository = mockk(relaxed = true)
        guessIngredientUseCase = GuessIngredientUseCase(foodRepository)
    }

    @Test
    fun `guessIngredient should return a question when call on valid conditions`() {
        // Give
        val meals = getMeals()
        val incorrectAnswersCount = 2
        val askedQuestions = mutableSetOf<Int>()
        every { foodRepository.getFoods() } returns Result.success(meals)

        // When
        val result = guessIngredientUseCase.guessIngredient(incorrectAnswersCount, askedQuestions)

        // Then
        Truth.assertThat(result.mealId).isIn(meals.map { it.id })
    }

    @Test
    fun `guessIngredient should return a question when call on valid conditions and avoid duplicate questions`() {
        // Give
        val meals = getMeals()
        val incorrectAnswersCount = 2
        val askedQuestions = mutableSetOf(meals[0].id, meals[1].id)
        every { foodRepository.getFoods() } returns Result.success(meals)

        // When
        val result = guessIngredientUseCase.guessIngredient(incorrectAnswersCount, askedQuestions)

        // Then
        Truth.assertThat(result.mealId).isIn(meals.map { it.id })
        Truth.assertThat(result.mealId).isNotIn(askedQuestions)
    }

    @Test
    fun `guessIngredient should throw when getFoods returns empty list`() {
        // Give
        val incorrectAnswersCount = 2
        val askedQuestions = mutableSetOf<Int>()
        every { foodRepository.getFoods() } returns Result.success(emptyList())

        // When & Then
        assertThrows<NoMealsFoundException> { guessIngredientUseCase.guessIngredient(incorrectAnswersCount, askedQuestions) }
    }

    @Test
    fun `guessIngredient should throw when getFoods returns list with invalid meals`() {
        // Give
        val meals = listOf(
            createMeal(id = 1, name = null, ingredients = listOf("Meat", "Salt")),
            createMeal(id = 2, name = "", ingredients = listOf("Meat", "Salt")),
            createMeal(id = 3, name = "Meal 3", ingredients = emptyList()),
        )
        val incorrectAnswersCount = 2
        val askedQuestions = mutableSetOf<Int>()
        every { foodRepository.getFoods() } returns Result.success(meals)

        // When & Then
        assertThrows<NoMealsFoundException> { guessIngredientUseCase.guessIngredient(incorrectAnswersCount, askedQuestions) }
    }

    private fun getMeals() = listOf(
        createMeal(id = 1, name = "Meal 1", ingredients = listOf("Chicken", "Salt", "Pepper", "Garlic", "Olive Oil")),
        createMeal(id = 2, name = "Meal 2", ingredients = listOf("Beef", "Onion", "Paprika", "Tomato", "Cumin")),
        createMeal(id = 3, name = "Meal 3", ingredients = listOf("Rice", "Turmeric", "Carrot", "Peas", "Butter")),
        createMeal(id = 4, name = "Meal 4", ingredients = listOf("Salmon", "Lemon", "Dill", "Salt", "Olive Oil")),
        createMeal(id = 5, name = "Meal 5", ingredients = listOf("Pasta", "Tomato Sauce", "Basil", "Parmesan", "Garlic")),
        createMeal(id = 6, name = "Meal 6", ingredients = listOf("Tofu", "Soy Sauce", "Ginger", "Scallion", "Sesame Oil")),
        createMeal(id = 7, name = "Meal 7", ingredients = listOf("Eggs", "Cheese", "Mushroom", "Spinach", "Onion")),
        createMeal(id = 8, name = "Meal 8", ingredients = listOf("Lamb", "Rosemary", "Garlic", "Salt", "Pepper")),
        createMeal(id = 9, name = "Meal 9", ingredients = listOf("Shrimp", "Garlic", "Butter", "Parsley", "Lemon")),
        createMeal(id = 10, name = "Meal 10", ingredients = listOf("Turkey", "Cranberry", "Sage", "Salt", "Pepper")),
        createMeal(id = 11, name = "Meal 11", ingredients = listOf("Bread", "Avocado", "Tomato", "Lime", "Salt")),
        createMeal(id = 12, name = "Meal 12", ingredients = listOf("Chickpeas", "Tahini", "Garlic", "Lemon", "Olive Oil")),
        createMeal(id = 13, name = "Meal 13", ingredients = listOf("Zucchini", "Parmesan", "Breadcrumbs", "Salt", "Pepper")),
        createMeal(id = 14, name = "Meal 14", ingredients = listOf("Quinoa", "Black Beans", "Corn", "Cumin", "Lime")),
        createMeal(id = 15, name = "Meal 15", ingredients = listOf("Potatoes", "Butter", "Milk", "Salt", "Pepper")),
        createMeal(id = 16, name = "Meal 16", ingredients = listOf("Cabbage", "Carrot", "Vinegar", "Sugar", "Salt")),
        createMeal(id = 17, name = "Meal 17", ingredients = listOf("Eggplant", "Tomato", "Olive Oil", "Garlic", "Basil")),
        createMeal(id = 18, name = "Meal 18", ingredients = listOf("Couscous", "Chickpeas", "Raisins", "Cinnamon", "Onion")),
        createMeal(id = 19, name = "Meal 19", ingredients = listOf("Cauliflower", "Curry Powder", "Coconut Milk", "Garlic", "Ginger")),
        createMeal(id = 20, name = "Meal 20", ingredients = listOf("Noodles", "Soy Sauce", "Carrot", "Cabbage", "Green Onion")),
        createMeal(id = 21, name = "Meal 21", ingredients = listOf("Bulgur", "Tomato", "Parsley", "Mint", "Lemon")),
        createMeal(id = 22, name = "Meal 22", ingredients = listOf("Sardines", "Lemon", "Olive Oil", "Chili Flakes", "Parsley")),
        createMeal(id = 23, name = "Meal 23", ingredients = listOf("Broccoli", "Cheddar", "Cream", "Onion", "Salt")),
        createMeal(id = 24, name = "Meal 24", ingredients = listOf("Pita", "Hummus", "Lettuce", "Cucumber", "Tomato")),
        createMeal(id = 25, name = "Meal 25", ingredients = listOf("Peas", "Mint", "Butter", "Salt", "Onion"))
    )
}