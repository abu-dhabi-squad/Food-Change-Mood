package logic.usecase

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.EmptyHealthFoodListListException
import model.NoMealsFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
            createMeal(
                id = 1, minutes = 10,
                calories = 350f,
                totalFat = 6f,
                sugar = 3f,
                sodium = 400f,
                protein = 20f,
                saturated = 1.2f,
                carbohydrates = 30f
            ),
            createMeal(minutes = 25)
        )

        every { foodRepository.getFoods() } returns Result.success(mockFoods)
        // given
        val result = useCase.fetchHealthyFastFoods()
        // then
        assertThat(result).isEqualTo(listOf( mockFoods[0]))
    }

    @Test
    fun ` should throws exception if no fast meals found when food repository return success`() {
        // when
        val mockFoods = listOf(
            createMeal(minutes = 25)
        )

        every { foodRepository.getFoods() } returns Result.success(mockFoods)

        // when && then
         assertThrows<EmptyHealthFoodListListException> {
            useCase.fetchHealthyFastFoods()
        }
    }

    @Test
    fun ` should throw exception when food repository return failure`() {
        every { foodRepository.getFoods() } returns Result.failure(Exception())

        assertThrows<Exception> {
            useCase.fetchHealthyFastFoods()
        }
    }


    @Test
    fun `should throw exception when all fast meals are above thresholds`() {
        val mockFoods = listOf(
            createMeal(minutes = 10, totalFat = 30f, saturated = 15f, carbohydrates = 80f),
            createMeal(minutes = 10, totalFat = 40f, saturated = 12f, carbohydrates = 90f)
        )
        every { foodRepository.getFoods() } returns Result.success(mockFoods)

        assertThrows<EmptyHealthFoodListListException> {
            useCase.fetchHealthyFastFoods()
        }
    }

    @Test
    fun `should return meal if nutrition is exactly equal to threshold values`() {
        val meal = createMeal(
            minutes = 10,
            totalFat = 10f,
            saturated = 3f,
            carbohydrates = 50f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(meal))

        val result = useCase.fetchHealthyFastFoods()

        assertThat(result).containsExactly(meal)
    }

}