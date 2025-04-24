package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.EmptyHealthFoodListListException
import model.Food
import model.Nutrition
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GetHealthyMealsUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var useCase: GetHealthyMealsUseCase

    @BeforeEach
    fun setUp() {
        foodRepository = mockk()
        useCase = GetHealthyMealsUseCase(foodRepository)
    }

    @Test
    fun `fetchHealthyFastFoods should returns filtered healthy meals when food repository return success`() {
        // when
        val mockFoods = listOf(
            Food(
                id = 1,
                name = "Chicken Wrap",
                minutes = 10,
                submittedDate = LocalDate.now(),
                tags = listOf("fast", "healthy"),
                nutrition = Nutrition(
                    calories = 350f,
                    totalFat = 6f,
                    sugar = 3f,
                    sodium = 400f,
                    protein = 20f,
                    saturated = 1.2f,
                    carbohydrates = 30f
                ),
                steps = listOf("Grill chicken", "Wrap", "Serve"),
                description = "Tasty chicken wrap.",
                ingredients = listOf("Chicken", "Wrap", "Lettuce")
            ),
            Food(
                id = 2,
                name = "Veggie Bowl",
                minutes = 12,
                submittedDate = LocalDate.now(),
                tags = listOf("vegetarian"),
                nutrition = Nutrition(
                    calories = 280f,
                    totalFat = 3f,
                    sugar = 2f,
                    sodium = 300f,
                    protein = 10f,
                    saturated = 0.7f,
                    carbohydrates = 20f
                ),
                steps = listOf("Chop veggies", "Mix", "Serve"),
                description = "Healthy veggie bowl.",
                ingredients = listOf("Carrot", "Cucumber", "Lettuce")
            ),
            Food(
                id = 3,
                name = "Burger",
                minutes = 25, // This one should be filtered out due to time
                submittedDate = LocalDate.now(),
                tags = listOf("junk"),
                nutrition = Nutrition(
                    calories = 500f,
                    totalFat = 20f,
                    sugar = 5f,
                    sodium = 600f,
                    protein = 25f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Grill patty", "Assemble", "Serve"),
                description = "Juicy burger.",
                ingredients = listOf("Beef", "Bun", "Cheese")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(mockFoods)

        // given
        val result = useCase.fetchHealthyFastFoods()

        // then
        assertThat(result).isNotEmpty()
        result.forEach { meal ->
            assertThat(meal.minutes).isAtMost(15)
        }
        assertThat(result).doesNotContain(mockFoods[2])
    }
    @Test
    fun `fetchHealthyFastFoods should throws exception if no fast meals found when food repository return success`() {
        // when
        val mockFoods = listOf(
            Food(
                id = 3,
                name = "Burger",
                minutes = 25, // This one should be filtered out due to time
                submittedDate = LocalDate.now(),
                tags = listOf("junk"),
                nutrition = Nutrition(
                    calories = 500f,
                    totalFat = 20f,
                    sugar = 5f,
                    sodium = 600f,
                    protein = 25f,
                    saturated = 5f,
                    carbohydrates = 40f
                ),
                steps = listOf("Grill patty", "Assemble", "Serve"),
                description = "Juicy burger.",
                ingredients = listOf("Beef", "Bun", "Cheese")
            )
        )

        every { foodRepository.getFoods() } returns Result.success(mockFoods)


        // then
        val exception = org.junit.jupiter.api.assertThrows<EmptyHealthFoodListListException> {
            useCase.fetchHealthyFastFoods()
        }
        assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun `fetchHealthyFastFoods should throw exception when food repository return failure`(){
        every { foodRepository.getFoods() } returns  Result.failure(Exception())

        org.junit.jupiter.api.assertThrows<Exception> {
            useCase.fetchHealthyFastFoods()
        }
    }
}