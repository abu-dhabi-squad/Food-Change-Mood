package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.NoSuchElementException

class GetRandomKetoDietMealsUseCaseTest {

    private lateinit var foodRepository: FoodRepository
    private lateinit var useCase: GetRandomKetoDietMealsUseCase

    @BeforeEach
    fun setup() {
        foodRepository = mockk()
        useCase = GetRandomKetoDietMealsUseCase(foodRepository)
    }

    @Test
    fun `should return valid keto meal when available`() {
        // Given
        val validKetoMeal = createMeal(
            id = 1,
            sugar = 4.9f,
            totalFat = 33.34f,
            protein = 31.25f,
            carbohydrates = 12.5f,
            saturated = 8f,
            calories = 500f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(validKetoMeal))

        // When
        val result = useCase(mutableListOf())

        // Then
        assertThat(result).isEqualTo(validKetoMeal)
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
    fun `should throw when no valid keto meals`() {
        // Given
        val invalidMeal = createMeal(
            id = 1,
            sugar = 10f,
            totalFat = 10f,
            protein = 40f,
            carbohydrates = 40f,
            saturated = 1f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(invalidMeal))

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
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

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf(1))
        }
    }

    @Test
    fun `should throw when repository returns empty list`() {
        // Given
        every { foodRepository.getFoods() } returns Result.success(emptyList())

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }

    @Test
    fun `should throw when repository fails`() {
        // Given
        val exception = Exception("Database error")
        every { foodRepository.getFoods() } returns Result.failure(exception)

        // When&Then
        val thrown = assertThrows<Exception> {
            useCase(mutableListOf())
        }
        assertThat(thrown.message).isEqualTo("Database error")
    }


    @Test
    fun `should reject meal with too much sugar`() {
        // Given
        val highSugarMeal = createMeal(
            id = 1,
            sugar = 5.1f,
            totalFat = 40f,
            protein = 20f,
            carbohydrates = 10f,
            saturated = 8f,
            calories = 500f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(highSugarMeal))

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }

    @Test
    fun `should reject meal with insufficient fat`() {
        // Given
        val lowFatMeal = createMeal(
            id = 1,
            sugar = 2f,
            totalFat = 33.33f,
            protein = 20f,
            carbohydrates = 10f,
            saturated = 8f,
            calories = 500f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(lowFatMeal))

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }

    @Test
    fun `should reject meal with too much protein`() {
        // Given
        val highProteinMeal = createMeal(
            id = 1,
            sugar = 2f,
            totalFat = 40f,
            protein = 31.26f,
            carbohydrates = 10f,
            saturated = 8f,
            calories = 500f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(highProteinMeal))

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }

    @Test
    fun `should reject meal with too many carbs`() {
        // Given
        val highCarbMeal = createMeal(
            id = 1,
            sugar = 2f,
            totalFat = 40f,
            protein = 20f,
            carbohydrates = 12.6f,
            saturated = 8f,
            calories = 500f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(highCarbMeal))

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }

    @Test
    fun `should reject meal with insufficient saturated fat`() {
        // Given
        val lowSaturatedMeal = createMeal(
            id = 1,
            sugar = 2f,
            totalFat = 40f,
            protein = 20f,
            carbohydrates = 10f,
            saturated = 7.99f,
            calories = 500f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(lowSaturatedMeal))

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }

    @Test
    fun `should reject meal with excessive saturated fat`() {
        // Given
        val highSaturatedMeal = createMeal(
            id = 1,
            sugar = 2f,
            totalFat = 40f,
            protein = 20f,
            carbohydrates = 10f,
            saturated = 10.01f,
            calories = 500f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(highSaturatedMeal))

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }

    @Test
    fun `should handle zero calories edge case`() {
        // Given
        val zeroCalorieMeal = createMeal(
            id = 1,
            sugar = 0f,
            totalFat = 0f,
            protein = 0f,
            carbohydrates = 0f,
            saturated = 0f,
            calories = 0f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(zeroCalorieMeal))

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }

    @Test
    fun `should handle zero total fat edge case`() {
        // Given
        val zeroFatMeal = createMeal(
            id = 1,
            sugar = 2f,
            totalFat = 0f,
            protein = 20f,
            carbohydrates = 10f,
            saturated = 0f,
            calories = 500f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(zeroFatMeal))

        // When&Then
        assertThrows<NoSuchElementException> {
            useCase(mutableListOf())
        }
    }



}