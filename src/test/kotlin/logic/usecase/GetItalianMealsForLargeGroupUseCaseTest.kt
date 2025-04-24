package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.repository.FoodRepository
import model.Food
import model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
            Food(
                1,
                "Pizza",
                30,
                LocalDate.of(2023, 5, 1),
                listOf("italian", "for-large-groups"),
                Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                listOf("Slice apples", "Bake the pie"),
                "Cheesy pizza",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
            ),
            Food(
                3,
                "Burger",
                20,
                LocalDate.of(2023, 5, 1),
                listOf("fast-food", "for-large-groups"),
                Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                listOf("Slice apples", "Bake the pie"),
                "Tasty burger",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(meals)

        // When
        val result = getItalianMealsForLargeGroupUseCase.getItalianMealForLargeGroup()

        // Then
        assertThat(result).isEqualTo(
            listOf(
                "Pizza" to "Cheesy pizza",
            )
        )
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `should return name and description for meals when tags contain 'italian' and 'for-large-group' and description not equal null`() {
        // Given
        val meals = listOf(
            Food(
                1,
                "Pizza",
                30,
                LocalDate.of(2023, 5, 1),
                listOf("italian", "for-large-groups"),
                Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                listOf("Slice apples", "Bake the pie"),
                "Cheesy pizza",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
            ),
            Food(
                2,
                "Pasta",
                25,
                LocalDate.of(2023, 5, 1),
                listOf("italian", "for-large-groups"),
                Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                listOf("Slice apples", "Bake the pie"),
                null,
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(meals)

        // When
        val result = getItalianMealsForLargeGroupUseCase.getItalianMealForLargeGroup()

        // Then
        assertThat(result).isEqualTo(
            listOf(
                "Pizza" to "Cheesy pizza",
            )
        )
        verify(exactly = 1) { foodRepository.getFoods() }
    }

    @Test
    fun `should return name and description for meals when tags contain 'italian' and 'for-large-group' and name not equal null`() {
        // Given
        val meals = listOf(
            Food(
                1,
                "Pizza",
                30,
                LocalDate.of(2023, 5, 1),
                listOf("italian", "for-large-groups"),
                Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                listOf("Slice apples", "Bake the pie"),
                "Cheesy pizza",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
            ),
            Food(
                2,
                null,
                25,
                LocalDate.of(2023, 5, 1),
                listOf("italian", "for-large-groups"),
                Nutrition(
                    calories = 300f,
                    totalFat = 15f,
                    sugar = 25f,
                    sodium = 100f,
                    protein = 3f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                listOf("Slice apples", "Bake the pie"),
                "Classic pasta",
                ingredients = listOf("Apples", "Flour", "Sugar", "Butter")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(meals)

        // When
        val result = getItalianMealsForLargeGroupUseCase.getItalianMealForLargeGroup()

        // Then
        assertThat(result).isEqualTo(
            listOf(
                "Pizza" to "Cheesy pizza",
            )
        )
        verify(exactly = 1) { foodRepository.getFoods() }
    }

}