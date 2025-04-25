package logic.usecase

import com.google.common.truth.Truth
import createMeal
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.EmptySearchByDateListException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetSeaFoodMealsSortedByProteinUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var useCase: GetSeaFoodMealsSortedByProteinUseCase

    @BeforeEach
    fun setup() {
        foodRepository = mockk(relaxed = true)
        useCase = GetSeaFoodMealsSortedByProteinUseCase(foodRepository)
    }

    @Test
    fun `invoke should return seafood meals sorted by protein descending`() {
        // Given
        val meals = listOf(
            createMeal(id = 1, tags = listOf("Seafood"), protein = 20.0f),
            createMeal(id = 2, tags = listOf("Seafood"), protein = 15.0f),
            createMeal(id = 3, tags = listOf("Vegetarian"), protein = 25.0f),
            createMeal(id = 4, tags = listOf("seaFOOD"), protein = 18.0f),
        )
        every { foodRepository.getFoods() } returns Result.success(meals)

        // When
        val result = useCase()

        // Then
        Truth.assertThat(result).hasSize(3)
        Truth.assertThat(result.map { it.id }).containsExactly(1, 4, 2).inOrder()
    }

    @Test
    fun `invoke should throw when no seafood meals are found`() {
        // Given
        val meals = listOf(
            createMeal(id = 1, tags = listOf("Vegetarian"), protein = 10.0f),
            createMeal(id = 2, tags = listOf("Vegan"), protein = 5.0f)
        )
        every { foodRepository.getFoods() } returns Result.success(meals)

        // When & Then
        assertThrows<EmptySearchByDateListException> {
            useCase()
        }
    }

    @Test
    fun `invoke should throw when getFoods throws exception`() {
        // Given
        every { foodRepository.getFoods() } returns Result.failure(RuntimeException("Database error"))

        // When & Then
        assertThrows<RuntimeException> {
            useCase()
        }
    }
}
