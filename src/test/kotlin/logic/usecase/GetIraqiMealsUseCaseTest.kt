package logic.usecase

import createMeal
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
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
            createMeal(id = 1, name = "Iraqi Kebab", description = "Yummy", tags = listOf("Iraq")),
            createMeal(id = 2, name = "Kebab", description = "Famous in Iraq", tags = listOf("MiddleEast")),
            createMeal(id = 3, name = "Kebab", description = "Popular in the MiddleEast", tags = listOf("iraqi")),
            createMeal(id = 4, name = "Pizza", description = "Italian", tags = listOf("Italian")),
            createMeal(id = 5, name = "Sushi", description = "Traditional Japanese dish", tags = listOf("Japanese")),
            createMeal(id = 6, name = "Ramen", description = "Hot noodle soup", tags = listOf("Japanese", "Noodles"))
        )

        every { foodRepository.getFoods() } returns Result.success(foods)

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = foods.subList(0, 3)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `should exclude meals with null or empty name or description`() {
        // Given
        val foods = listOf(
            createMeal(id = 1, name = null, description = "Desc"),
            createMeal(id = 2, name = "", description = "Desc"),
            createMeal(id = 3, name = "Name", description = null),
            createMeal(id = 4, name = "Iraqi Dolma", description = "Traditional")
        )

        every { foodRepository.getFoods() } returns Result.success(foods)

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = listOf(foods[3])
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `should throw NoIraqiMealsFoundException when no Iraqi meals exist`() {
        // Given
        val foods = listOf(
            createMeal(id = 1, name = "Pizza", description = "Italian", tags = listOf("Italian")),
            createMeal(id = 2, name = "Sushi", description = "Japanese", tags = listOf("Japanese"))
        )

        every { foodRepository.getFoods() } returns Result.success(foods)

        // Then
        assertThrows<NoIraqiMealsFoundException> {
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
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
        TestCase.assertEquals("Database error", exception.message)
    }

    @Test
    fun `should handle case insensitive Iraq matching`() {
        // Given
        val foods = listOf(
            createMeal(id = 1, name = "IRAQI KEBAB"),
            createMeal(id = 2, description = "traditional iraqi dish"),
            createMeal(id = 3, name = "meal", description = "meal", tags = listOf("IRAQ"))
        )

        every { foodRepository.getFoods() } returns Result.success(foods)

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = foods
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `should handle meals with multiple tags`() {
        // Given
        val food = createMeal(id = 1, name = "Kebab", tags = listOf("MiddleEast", "Iraq", "Grill"))

        every { foodRepository.getFoods() } returns Result.success(listOf(food))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = listOf(food)
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun `should reject meal when name or description is null`() {
        // Given
        val nullNameMeal = createMeal(id = 1, name = null, description = "Desc")
        val nullDescMeal = createMeal(id = 2, name = "Name", description = null)

        every { foodRepository.getFoods() } returns Result.success(listOf(nullNameMeal, nullDescMeal))

        // Then
        assertThrows<NoIraqiMealsFoundException> {
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
    }

    @Test
    fun `should reject meal when both name and description are null or empty`() {
        // Given
        val invalidMeals = listOf(
            createMeal(id = 1, name = null, description = null),
            createMeal(id = 2, name = "", description = ""),
            createMeal(id = 3, name = null, description = ""),
            createMeal(id = 4, name = "", description = null)
        )

        every { foodRepository.getFoods() } returns Result.success(invalidMeals)

        // Then
        assertThrows<NoIraqiMealsFoundException> {
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
    }

    @Test
    fun `should accept meal when either name or description is not null or empty`() {
        // Given
        val validMeals = listOf(
            createMeal(id = 1, name = "Iraqi Dish", description = null),
            createMeal(id = 2, name = null, description = "Iraqi Dish"),
            createMeal(id = 3, name = "Iraqi Dish", description = ""),
            createMeal(id = 4, name = "", description = "Iraqi Dish")
        )

        every { foodRepository.getFoods() } returns Result.success(validMeals)

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = validMeals
        TestCase.assertEquals(expected, result)
    }



}
