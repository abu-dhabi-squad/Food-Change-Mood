package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.FoodRepository
import model.NoEasyMealsFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class GetEasyFoodSuggestionGameUseCaseTest {

    private val foodRepository: FoodRepository = mockk()
    private lateinit var getEasyFoodSuggestionGameUseCase: GetEasyFoodSuggestionGameUseCase

    @BeforeEach
    fun setup(){
        getEasyFoodSuggestionGameUseCase = GetEasyFoodSuggestionGameUseCase(foodRepository)
    }

    @Test
    fun `should return random easy meals when valid meals exist`(){
        //given
        val appleFood = createFood(
            name = "Apple Pie",
            minutes = 45,
            steps = listOf("Slice apples", "Bake the pie"),
            description = "A tasty apple pie.",
            ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
        )

        val chickenFood = createFood(
            name = "Grilled Chicken",
            minutes = 30,
            steps = listOf("Season chicken", "Grill until done"),
            description = "Healthy grilled chicken.",
            ingredients = listOf("Chicken breast", "Spices")
        )

        val saladFood = createFood(
            name = "Greek Salad",
            minutes = 15,
            steps = listOf("Chop veggies", "Mix ingredients"),
            description = "A refreshing Greek salad.",
            ingredients = listOf("Tomatoes", "Cucumber", "Feta cheese", "Olives")
        )

        val foods =  listOf(appleFood,chickenFood,saladFood)

        every { foodRepository.getFoods() } returns Result.success(foods)

        //when
        val meals = getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        //then
        assertThat(meals).containsExactly(chickenFood,saladFood)

    }

    @Test
    fun `should throw NoEasyMealsFoundException when no easy meals are available`() {
        // given
        val hardMeal = createFood(
            name = "Complex Dish",
            minutes = 60,
            steps = List(10) { "Step $it" },
            description = "Complicated meal",
            ingredients = List(6) { "Ingredient $it" }
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(hardMeal))

        // when + then
        assertThrows(NoEasyMealsFoundException::class.java) {
            getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }
    }

    @Test
    fun `should throw NoEasyMealsFoundException when repository fails`() {
        //when
        every { foodRepository.getFoods() } returns Result.failure(NoEasyMealsFoundException())

        //then
        assertThrows(NoEasyMealsFoundException::class.java) {
            getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }
    }

    @Test
    fun `should throw NoEasyMealsFoundException when repository returns empty list`() {
        every { foodRepository.getFoods() } returns Result.success(emptyList())

        assertThrows(NoEasyMealsFoundException::class.java) {
            getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }
    }

    @Test
    fun `should ignore meals with null description or null names `() {
        // given
        val mealWithNullName = createFood(
            name = null,
            minutes = 15,
            steps = List(3) { "Step $it" },
            description = "easy meal",
            ingredients = List(2) { "Ingredient $it" }
        )
        val mealWithNullDescription = createFood(
            name = null,
            minutes = 15,
            steps = List(3) { "Step $it" },
            description = null,
            ingredients = List(2) { "Ingredient $it" }
        )
        val validEasyMeal =   createFood(
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
        val result = getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        //then
        assertThat(result).containsExactly(validEasyMeal)

    }

    @Test
    fun `should ignore meals with null or empty steps or ingredients`() {
        // given
        val mealWithEmptySteps = createFood(
            name = "Meal With Empty Steps",
            minutes = 20,
            steps = emptyList(),
            description = "Empty steps",
            ingredients = listOf("Ingredient 1", "Ingredient 2")
        )

        val mealWithNullIngredients = createFood(
            name = "Meal With Null Ingredients",
            minutes = 20,
            steps = listOf("Step 1", "Step 2"),
            description = "Missing ingredients",
            ingredients = emptyList()
        )

        val validMeal = createFood(
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
        val result = getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        // then
        assertThat(result).containsExactly(validMeal)
    }

    @Test
    fun `should include meals with exactly max limits`() {
        val validMeal = createFood(
            name = "Exact Limit Meal",
            minutes = 30,
            steps = List(6) { "Step $it" },
            description = "Valid meal at max case",
            ingredients = List(5) { "Ingredient $it" }
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(validMeal))

        val result = getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        assertThat(result).containsExactly(validMeal)
    }

    @Test
    fun `should ignore meals just over the easy meal limits`() {
        val mealTooLong = createFood(
            name = "Too Long",
            minutes = 31,
            steps = List(3) { "Step $it" },
            description = "Too long to cook",
            ingredients = List(3) { "Ingredient $it" }
        )

        val mealTooManyIngredients = createFood(
            name = "Too Many Ingredients",
            minutes = 20,
            steps = List(3) { "Step $it" },
            description = "Too many ingredients",
            ingredients = List(6) { "Ingredient $it" }
        )

        val mealTooManySteps = createFood(
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
            getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }
    }

    @Test
    fun `should throw NoEasyMealsFoundException when meal has invalid description`() {
        val invalidMeal = createFood(
            name = "Apple Pie",
            description = null, // Invalid description
            minutes = 25,
            ingredients = listOf("Apples", "Flour", "Sugar"),
            steps = listOf("Step 1", "Step 2")
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(invalidMeal))

        assertFailsWith<NoEasyMealsFoundException> {
            getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }

        verify { foodRepository.getFoods() }
    }

    @Test
    fun `should throw NoEasyMealsFoundException when meal has invalid time`() {
        val invalidMeal = createFood(
            name = "Apple Pie",
            description = "A classic dessert.",
            minutes = 40, // Invalid time
            ingredients = listOf("Apples", "Flour", "Sugar"),
            steps = listOf("Step 1", "Step 2")
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(invalidMeal))

        assertFailsWith<NoEasyMealsFoundException> {
            getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }

        verify { foodRepository.getFoods() }
    }

    @Test
    fun `should throw NoEasyMealsFoundException when meal has too many ingredients`() {
        val invalidMeal = createFood(
            name = "Apple Pie",
            description = "A classic dessert.",
            minutes = 25,
            ingredients = listOf("Apples", "Flour", "Sugar", "Butter", "Eggs", "Vanilla"), // Too many ingredients
            steps = listOf("Step 1", "Step 2")
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(invalidMeal))

        assertFailsWith<NoEasyMealsFoundException> {
            getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }

        verify { foodRepository.getFoods() }
    }

    @Test
    fun `should throw NoEasyMealsFoundException when meal has too few steps`() {
        val invalidMeal = createFood(
            name = "Apple Pie",
            description = "A classic dessert.",
            minutes = 25,
            ingredients = listOf("Apples", "Flour", "Sugar"),
            steps = listOf() // Invalid steps (empty)
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(invalidMeal))

        assertFailsWith<NoEasyMealsFoundException> {
            getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
        }

        verify { foodRepository.getFoods() }
    }

    @Test
    fun `should return valid meals when meal has valid steps`() {
        val validMeal = createFood(
            name = "Apple Pie",
            description = "A classic dessert.",
            minutes = 25,
            ingredients = listOf("Apples", "Flour", "Sugar"),
            steps = listOf("Step 1", "Step 2")
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(validMeal))

        val result = getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        assertTrue(result.contains(validMeal))
        verify { foodRepository.getFoods() }
    }

    @Test
    fun `should return valid meals when meal has valid ingredients`() {
        val validMeal = createFood(
            name = "Apple Pie",
            description = "A classic dessert.",
            minutes = 25,
            ingredients = listOf("Apples", "Flour", "Sugar"),
            steps = listOf("Step 1", "Step 2")
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(validMeal))

        val result = getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        assertTrue(result.contains(validMeal))
        verify { foodRepository.getFoods() }
    }

    @Test
    fun `should return valid meals when meal has valid time, ingredients, and steps`() {
        val validMeal = createFood(
            name = "Apple Pie",
            description = "A classic dessert.",
            minutes = 25,
            ingredients = listOf("Apples", "Flour", "Sugar"),
            steps = listOf("Step 1", "Step 2")
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(validMeal))

        val result = getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()

        assertTrue(result.contains(validMeal))
        verify { foodRepository.getFoods() }
    }
  
}