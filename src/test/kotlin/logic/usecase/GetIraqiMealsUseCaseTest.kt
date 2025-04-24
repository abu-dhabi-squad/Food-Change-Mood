package logic.usecase

import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.NoIraqiMealsFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class GetIraqiMealsUseCaseTest {

    private lateinit var foodRepository: FoodRepository
    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase

    @BeforeEach
    fun setUp() {
        foodRepository = mockk()
        getIraqiMealsUseCase = GetIraqiMealsUseCase(foodRepository)
    }

    @Test
    fun `should return only meals containing Iraq in name, description or tags`() {
        // Given
        val foods = listOf(
            createFood(name = "Iraqi Kebab", description = "Yummy", tags = listOf("Iraq")),
            createFood(name = "Kebab", description = "Famous in Iraq", tags = listOf("MiddleEast")),
            createFood(name = "Kebab", description = "Popular in the MiddleEast", tags = listOf("iraqi")),
            createFood(name = "Pizza", description = "Italian", tags = listOf("Italian")),
            createFood(name = "Sushi", description = "Traditional Japanese dish", tags = listOf("Japanese")),
            createFood(name = "Ramen", description = "Hot noodle soup", tags = listOf("Japanese", "Noodles"))
        )

        every { foodRepository.getFoods() } returns Result.success(foods)

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = listOf(
            createFood(name = "Iraqi Kebab", description = "Yummy", tags = listOf("Iraq")),
            createFood(name = "Kebab", description = "Famous in Iraq", tags = listOf("MiddleEast")),
            createFood(name = "Kebab", description = "Popular in the MiddleEast", tags = listOf("iraqi"))
        )

        // Assert that the result matches the expected list of Iraqi meals
        assertEquals(expected, result)
    }



    @Test
    fun `should exclude meals with null or empty name or description`() {
        // Given
        val invalidFood1 = createMealHelper(name = null, description = "Desc")
        val invalidFood2 = createMealHelper(name = "", description = "Desc")
        val invalidFood3 = createMealHelper(name = "Name", description = null)
        val validFood = createMealHelper(name = "Iraqi Dolma", description = "Traditional")

        every { foodRepository.getFoods() } returns Result.success(listOf(
            invalidFood1, invalidFood2, invalidFood3, validFood
        ))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = listOf(validFood)
        Assertions.assertEquals(expected, result)
    }


    @Test
    fun `should throw NoIraqiMealsFoundException when no Iraqi meals exist`() {
        // Given
        val foods = listOf(
            createFood(name = "Pizza", description = "Italian", tags = listOf("Italian")),
            createFood(name = "Sushi", description = "Japanese", tags = listOf("Japanese"))
        )

        every { foodRepository.getFoods() } returns Result.success(foods)

        // Then
        assertThrows<NoIraqiMealsFoundException> {
            // When
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
    }


    @Test
    fun `should propagate repository failure`() {
        // Given
        val error = RuntimeException("Database error")
        every { foodRepository.getFoods() } returns Result.failure(error)

        // Then
        val exception = assertThrows<RuntimeException> {
            // When
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
        assertEquals("Database error", exception.message)
    }

    @Test
    fun `should handle case insensitive Iraq matching`() {
        // Given
        val foods = listOf(
            createMealHelper(name = "IRAQI KEBAB"),
            createMealHelper(description = "traditional iraqi dish"),
            createMealHelper(tags = listOf("IRAQ"))
        )

        every { foodRepository.getFoods() } returns Result.success(foods)

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = foods
        Assertions.assertEquals(expected, result)
    }


    @Test
    fun `should handle meals with multiple tags`() {
        // Given
        val food = createMealHelper(name = "Kebab", tags = listOf("MiddleEast", "Iraq", "Grill"))

        every { foodRepository.getFoods() } returns Result.success(listOf(food))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = listOf(food)
        Assertions.assertEquals(expected, result)
    }


    @Test
    fun `should reject meal when name or description is null`() {
        // Given
        val nullNameMeal = createMealHelper(name = null, description = "Desc")
        val nullDescMeal = createMealHelper(name = "Name", description = null)

        every { foodRepository.getFoods() } returns Result.success(listOf(nullNameMeal, nullDescMeal))

        // Then
        assertThrows<NoIraqiMealsFoundException> {
            // When
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
    }


    @Test
    fun `should reject meal when both name and description are null or empty`() {
        // Given
        val invalidMeals = listOf(
            createMealHelper(name = null, description = null),
            createMealHelper(name = "", description = ""),
            createMealHelper(name = null, description = ""),
            createMealHelper(name = "", description = null)
        )

        every { foodRepository.getFoods() } returns Result.success(invalidMeals)

        // Then
        assertThrows<NoIraqiMealsFoundException> {
            // When
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
    }

    @Test
    fun `should accept meal when either name or description is not null or empty`() {
        // Given
        val validMeals = listOf(
            createMealHelper(name = "Iraqi Dish", description = null),
            createMealHelper(name = null, description = "Iraqi Dish"),
            createMealHelper(name = "Iraqi Dish", description = ""),
            createMealHelper(name = "", description = "Iraqi Dish")
        )

        every { foodRepository.getFoods() } returns Result.success(validMeals)

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = validMeals
        Assertions.assertEquals(expected, result)
    }



}
