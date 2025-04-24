package logic.usecase

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.FoodRepository
import model.EmptyListException
import model.Food
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class GetItalianMealsForLargeGroupUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var getItalianMealsForLargeGroupUseCase: GetItalianMealsForLargeGroupUseCase

    @BeforeEach
    fun setup() {
        foodRepository = mockk(relaxed = true)
        getItalianMealsForLargeGroupUseCase = GetItalianMealsForLargeGroupUseCase(foodRepository)
    }

    @Test
    fun `should return name and description for meals when tags contain 'italian' and 'for-large-group'`() {
        // Given
        val meals = listOf(
            createMeal(
                id = 1,
                name = "Pizza",
                description = "Cheesy pizza",
                minutes = 30,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("italian", "for-large-groups")
            ),
            createMeal(
                id = 3,
                name = "Burger",
                description = "Tasty burger",
                minutes = 20,
                submittedDate = LocalDate.of(2023, 5, 1),
                tags = listOf("fast-food", "for-large-groups")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(meals)

        // When
        val result = getItalianMealsForLargeGroupUseCase.getItalianMealForLargeGroup()

        // Then
        assertThat(result).isEqualTo(listOf("Pizza" to "Cheesy pizza"))
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `should return name and description when description is not null`() {
        // Given
        val meals = listOf(
            createMeal(
                id = 1,
                name = "Pizza",
                description = "Cheesy pizza",
                tags = listOf("italian", "for-large-groups")
            ),
            createMeal(
                id = 2,
                name = "Pasta",
                description = null,
                tags = listOf("italian", "for-large-groups")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(meals)

        // When
        val result = getItalianMealsForLargeGroupUseCase.getItalianMealForLargeGroup()

        // Then
        assertThat(result).isEqualTo(listOf("Pizza" to "Cheesy pizza"))
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `should return name and description when name is not null`() {
        // Given
        val meals = listOf(
            createMeal(
                id = 1,
                name = "Pizza",
                description = "Cheesy pizza",
                tags = listOf("italian", "for-large-groups")
            ),
            createMeal(
                id = 2,
                name = null,
                description = "Classic pasta",
                tags = listOf("italian", "for-large-groups")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(meals)

        // When
        val result = getItalianMealsForLargeGroupUseCase.getItalianMealForLargeGroup()

        // Then
        assertThat(result).isEqualTo(listOf("Pizza" to "Cheesy pizza"))
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `should return empty list exception when list is empty`() {
        // Given
        val meals:List<Food> = listOf()

        every { foodRepository.getFoods() } returns Result.success(meals)

        // When and Then
        assertThrows<EmptyListException>{
            getItalianMealsForLargeGroupUseCase.getItalianMealForLargeGroup()
        }
    }

    @Test
    fun `should return empty list exception when all tags of meals not contain 'italian' and 'for-large-groups'`() {
        // Given
        val meals = listOf(
            createMeal(
                id = 1,
                name = "Pizza",
                description = "Cheesy pizza",
                tags = listOf("cairo", "large-groups")
            ),
            createMeal(
                id = 2,
                name = "pasta",
                description = "Classic pasta",
                tags = listOf("paris", "large-groups")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(meals)

        // When and Then
        assertThrows<EmptyListException>{
            getItalianMealsForLargeGroupUseCase.getItalianMealForLargeGroup()
        }
    }

    @Test
    fun `should throw exception when food repository returns failure`() {
        // Given
        every { foodRepository.getFoods() } returns Result.failure(Exception())

        // When and Then
        assertThrows<Exception>{
            getItalianMealsForLargeGroupUseCase.getItalianMealForLargeGroup()
        }
    }

}
