package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.NoEasyMealsFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EasyFoodSuggestionGameUseCaseTest {

    private lateinit var foodRepository: FoodRepository
    private lateinit var easyFoodSuggestionGameUseCase: EasyFoodSuggestionGameUseCase

    @BeforeEach
    fun setup(){
        foodRepository = mockk()
        easyFoodSuggestionGameUseCase = EasyFoodSuggestionGameUseCase(foodRepository)
    }

    @Test
    fun `should return random easy meals when food repository return success with non empty food list`(){
        //given
        val appleFood =createMeal(
            name = "Apple Pie",
            minutes = 45,
            steps = listOf("Slice apples", "Bake the pie"),
            description = "A tasty apple pie.",
            ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
        )

        val chickenFood =   createMeal(
            name = "Grilled Chicken",
            minutes = 30,
            steps = listOf("Season chicken", "Grill until done"),
            description = "Healthy grilled chicken.",
            ingredients = listOf("Chicken breast", "Spices")
        )

        val saladFood = createMeal(
            name = "Greek Salad",
            minutes = 15,
            steps = listOf("Chop veggies", "Mix ingredients"),
            description = "A refreshing Greek salad.",
            ingredients = listOf("Tomatoes", "Cucumber", "Feta cheese", "Olives")
        )

        val foods =  listOf(appleFood,chickenFood,saladFood)

        every { foodRepository.getFoods() } returns Result.success(foods)

        //when
        val meals = easyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        //then
        assertThat(meals).containsExactly(chickenFood,saladFood)



    }
    @Test
    fun `should throw NoEasyMealsFoundException when no easy meals are available`() {
        // given
        val hardMeal = createMeal(
            name = "Complex Dish",
            minutes = 60,
            steps = List(10) { "Step $it" },
            description = "Complicated meal",
            ingredients = List(6) { "Ingredient $it" }
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(hardMeal))

        // when + then
        assertThrows(NoEasyMealsFoundException::class.java) {
            easyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }
    }
    @Test
    fun `should throw NoEasyMealsFoundException when repository fails`() {
        //when
        every { foodRepository.getFoods() } returns Result.failure(NoEasyMealsFoundException())

        //then
        assertThrows(NoEasyMealsFoundException::class.java) {
            easyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }
    }
    @Test
    fun `should throw NoEasyMealsFoundException when repository returns empty list`() {
        every { foodRepository.getFoods() } returns Result.success(emptyList())

        assertThrows(NoEasyMealsFoundException::class.java) {
            easyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }
    }
    @Test
    fun `should ignore meals with null description or null names `() {
        // given
        val mealWithNullName = createMeal(
            name = null,
            minutes = 15,
            steps = List(3) { "Step $it" },
            description = "easy meal",
            ingredients = List(2) { "Ingredient $it" }
        )
        val mealWithNullDescription = createMeal(
            name = null,
            minutes = 15,
            steps = List(3) { "Step $it" },
            description = null,
            ingredients = List(2) { "Ingredient $it" }
        )
        val validEasyMeal =   createMeal(
            name = "Grilled Chicken",
            minutes = 30,
            steps = listOf("Season chicken", "Grill until done"),
            description = "Healthy grilled chicken.",
            ingredients = listOf("Chicken breast", "Spices")
        )

        every { foodRepository.getFoods() } returns Result.success(
            listOf(mealWithNullName,mealWithNullDescription,validEasyMeal)
        )

        //when
        val result = easyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        //then
        assertThat(result).containsExactly(validEasyMeal)

    }
    @Test
    fun `should ignore meals with null or empty steps or ingredients`() {
        // given
        val mealWithEmptySteps = createMeal(
            name = "Meal With Empty Steps",
            minutes = 20,
            steps = emptyList(),
            description = "Empty steps",
            ingredients = listOf("Ingredient 1", "Ingredient 2")
        )

        val mealWithNullIngredients = createMeal(
            name = "Meal With Null Ingredients",
            minutes = 20,
            steps = listOf("Step 1", "Step 2"),
            description = "Missing ingredients",
            ingredients = emptyList()
        )

        val validMeal = createMeal(
            name = "Greek Salad",
            minutes = 15,
            steps = listOf("Chop veggies", "Mix with dressing"),
            description = "A light and refreshing salad.",
            ingredients = listOf("Tomato", "Cucumber", "Feta")
        )

        every { foodRepository.getFoods() } returns Result.success(
            listOf(mealWithEmptySteps, mealWithNullIngredients, validMeal)
        )

        // when
        val result = easyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        // then
        assertThat(result).containsExactly(validMeal)
    }
    @Test
    fun `should include meals with exactly max limits`() {
        val validMeal = createMeal(
            name = "Exact Limit Meal",
            minutes = 30,
            steps = List(6) { "Step $it" },
            description = "Valid meal at max case",
            ingredients = List(5) { "Ingredient $it" }
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(validMeal))

        val result = easyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        assertThat(result).containsExactly(validMeal)
    }
    @Test
    fun `should ignore meals just over the easy meal limits`() {
        val mealTooLong = createMeal(
            name = "Too Long",
            minutes = 31,
            steps = List(3) { "Step $it" },
            description = "Too long to cook",
            ingredients = List(3) { "Ingredient $it" }
        )

        val mealTooManyIngredients = createMeal(
            name = "Too Many Ingredients",
            minutes = 20,
            steps = List(3) { "Step $it" },
            description = "Too many ingredients",
            ingredients = List(6) { "Ingredient $it" }
        )

        val mealTooManySteps = createMeal(
            name = "Too Many Steps",
            minutes = 20,
            steps = List(7) { "Step $it" },
            description = "Too complex",
            ingredients = List(3) { "Ingredient $it" }
        )

        every { foodRepository.getFoods() } returns Result.success(
            listOf(mealTooLong, mealTooManyIngredients, mealTooManySteps)
        )

        assertThrows(NoEasyMealsFoundException::class.java) {
            easyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }
    }

}