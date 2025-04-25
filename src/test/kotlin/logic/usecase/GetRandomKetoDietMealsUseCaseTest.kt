package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.NoSuchElementException

class GetRandomKetoDietMealsUseCaseTest {

    private lateinit var foodRepository: FoodRepository
    private lateinit var useCase: GetRandomKetoDietMealsUseCase

    @BeforeEach
    fun setup() {
        foodRepository = mockk()
        useCase = GetRandomKetoDietMealsUseCase(foodRepository)
    }
    @ParameterizedTest
    @CsvSource(

        "4.9, 33.34, 31.25, 12.5, 8.0, 500.0, VALID",
        "5.1, 40.0, 20.0, 10.0, 8.0, 500.0, INVALID",
        "2.0, 33.33, 20.0, 10.0, 8.0, 500.0, INVALID",
        "2.0, 40.0, 31.26, 10.0, 8.0, 500.0, INVALID",
        "2.0, 40.0, 20.0, 12.6, 8.0, 500.0, INVALID",
        "2.0, 40.0, 20.0, 10.0, 7.99, 500.0, INVALID",
        "2.0, 40.0, 20.0, 10.0, 10.01, 500.0, INVALID",
        "0.0, 0.0, 0.0, 0.0, 0.0, 0.0, INVALID",
        "2.0, 0.0, 20.0, 10.0, 0.0, 500.0, INVALID"
    )
    fun `test meal validation with parameters`(
        sugar: Float,
        totalFat: Float,
        protein: Float,
        carbs: Float,
        saturated: Float,
        calories: Float,
        expectedResult: String
    ) {
        // Given
        val meal = createMeal(
            id = 1,
            sugar = sugar,
            totalFat = totalFat,
            protein = protein,
            carbohydrates = carbs,
            saturated = saturated,
            calories = calories
        )

        every { foodRepository.getFoods() } returns Result.success(listOf(meal))

        // When & Then
        when (expectedResult) {
            "VALID" -> {
                val result = useCase(mutableListOf())
                assertThat(result).isEqualTo(meal)
            }
            "INVALID" -> {
                assertThrows<NoSuchElementException> {
                    useCase(mutableListOf())
                }
            }
        }
    }
    @Test
    fun `should return one keto meal from mixed list`() {
        // Given
        val validKetoMeal = createMeal(
            id = 1,
            sugar = 2f,
            totalFat = 40f,
            protein = 20f,
            carbohydrates = 10f,
            saturated = 8f,
            calories = 500f
        )
        val invalidMeal = createMeal(
            id = 2,
            sugar = 5.1f,
            totalFat = 40f,
            protein = 20f,
            carbohydrates = 10f,
            saturated = 8f,
            calories = 500f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(validKetoMeal, invalidMeal))

        // When
        val result = useCase(mutableListOf())

        // Then
        assertThat(result.id).isEqualTo(1)
    }

    @Test
    fun `should throw when all keto meals shown`() {
        // Given
        val validKetoMeal = createMeal(
            id = 1,
            sugar = 2f,
            totalFat = 40f,
            protein = 20f,
            carbohydrates = 10f,
            saturated = 8f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(validKetoMeal))

        // When & Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf(1))
        }
    }

    @Test
    fun `should throw when repository returns empty list`() {
        // Given
        every { foodRepository.getFoods() } returns Result.success(emptyList())

        // When & Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }

    @Test
    fun `should throw when repository fails`() {
        // Given
        val exception = Exception("Database error")
        every { foodRepository.getFoods() } returns Result.failure(exception)

        // When & Then
        val thrown = assertThrows<Exception> {
            useCase(mutableListOf())
        }
        assertThat(thrown.message).isEqualTo("Database error")
    }



}