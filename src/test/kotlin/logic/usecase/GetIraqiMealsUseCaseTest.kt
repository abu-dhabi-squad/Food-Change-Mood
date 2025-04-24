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
        val iraqiFood1 = createMealHelper(name = "Iraqi Kebab", description = "Yummy")
        val iraqiFood2 = createMealHelper(name = "Kebab", description = "Famous in Iraq")
        val iraqiFood3 = createMealHelper(name = "Kebab", tags = listOf("iraqi"))
        val nonIraqiFood = createMealHelper(name = "Pizza", description = "Italian")

        every { foodRepository.getFoods() } returns Result.success(listOf(
            iraqiFood1, iraqiFood2, iraqiFood3, nonIraqiFood
        ))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        assertEquals(3, result.size)
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
        assertEquals(1, result.size)
    }


    @Test
    fun `should throw NoIraqiMealsFoundException when no Iraqi meals exist`() {
        // Given
        val nonIraqiFood1 = createMealHelper(name = "Pizza", description = "Italian")
        val nonIraqiFood2 = createMealHelper(name = "Sushi", description = "Japanese")

        every { foodRepository.getFoods() } returns Result.success(listOf(nonIraqiFood1, nonIraqiFood2))

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
    fun `should throw when all meals are invalid`() {
        // Given
        val invalidFood1 = createMealHelper(name = null, description = "Desc")
        val invalidFood2 = createMealHelper(name = "Name", description = null)

        every { foodRepository.getFoods() } returns Result.success(listOf(invalidFood1, invalidFood2))

        // Then
        assertThrows<NoIraqiMealsFoundException> {
            // When
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
    }

    @Test
    fun `should handle case insensitive Iraq matching`() {
        // Given
        val food1 = createMealHelper(name = "IRAQI KEBAB")
        val food2 = createMealHelper(description = "traditional iraqi dish")
        val food3 = createMealHelper(tags = listOf("IRAQ"))

        every { foodRepository.getFoods() } returns Result.success(listOf(food1, food2, food3))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        assertEquals(3, result.size)
    }


    @Test
    fun `should handle meals with multiple tags`() {
        // Given
        val food = createMealHelper(name = "Kebab", tags = listOf("MiddleEast", "Iraq", "Grill"))

        every { foodRepository.getFoods() } returns Result.success(listOf(food))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        assertEquals(1, result.size)
    }

    @Test
    fun `should set default name when name is empty string`() {
        // Given
         val emptyNameMeal = createMealHelper(name = "", description = "Iraqi dish")
       every { foodRepository.getFoods() } returns Result.success(listOf(emptyNameMeal))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        assertEquals("unnamed", result[0].name)
    }

    @Test
    fun `should set default description when description is empty string`() {
        // Given
        val emptyDescMeal = createMealHelper(name = "Iraqi Kebab", description = "")
        every { foodRepository.getFoods() } returns Result.success(listOf(emptyDescMeal))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        assertEquals("no description", result[0].description)
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
        assertEquals(4, result.size)
    }



}
