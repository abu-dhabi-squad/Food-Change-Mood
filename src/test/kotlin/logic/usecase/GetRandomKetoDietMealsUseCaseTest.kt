package logic.usecase

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.Food
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class GetRandomKetoDietMealsUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var getRandomKetoDietMealsUseCase: GetRandomKetoDietMealsUseCase

    @BeforeEach
    fun setUp() {
        foodRepository = mockk()
        getRandomKetoDietMealsUseCase = GetRandomKetoDietMealsUseCase(foodRepository)
    }

    @Test
    fun `should return random keto meal when food list has keto meal`() {
        // given
        val ketoMeal = createMeal(
            id = 1, calories = 500f, totalFat = 35f,
            sugar = 2f, sodium = 400f, protein = 25f, saturated = 7f, carbohydrates = 8f
        )

        val mealWithHighSugar = createMeal(
            id = 2, calories = 500f, totalFat = 35f,
            sugar = 6f, sodium = 400f, protein = 25f, saturated = 7f, carbohydrates = 8f
        )

        val mealWithLowFat = createMeal(
            id = 3, calories = 500f, totalFat = 10f,
            sugar = 2f, sodium = 400f, protein = 25f, saturated = 2f, carbohydrates = 8f
        )

        val mealWithHighProtein = createMeal(
            id = 4, calories = 500f, totalFat = 35f,
            sugar = 2f, sodium = 400f, protein = 40f, saturated = 7f, carbohydrates = 8f
        )

        val mealWithHighCarbs = createMeal(
            id = 5, calories = 500f, totalFat = 35f,
            sugar = 2f, sodium = 400f, protein = 25f, saturated = 7f, carbohydrates = 20f
        )

        val mealWithInvalidSaturatedFat = createMeal(
            id = 6, calories = 500f, totalFat = 35f,
            sugar = 2f, sodium = 400f, protein = 25f, saturated = 12f, carbohydrates = 8f
        )

        every { foodRepository.getFoods() } returns Result.success(
            listOf(
                ketoMeal,
                mealWithHighSugar,
                mealWithLowFat,
                mealWithHighProtein,
                mealWithHighCarbs,
                mealWithInvalidSaturatedFat
            )
        )

        val shown = mutableListOf<Int>()

        // when
        val result = getRandomKetoDietMealsUseCase.invoke(shown)

        // then
        assertThat(result).isEqualTo(ketoMeal)
    }

    @ParameterizedTest(name = "should throw for food list size={0}, shown={1}")
    @MethodSource("provideInvalidFoodLists")
    fun `should throw NoSuchElementException when no valid keto meal available`(
        food: List<Food>,
        shown: MutableList<Int>
    ) {
        every { foodRepository.getFoods() } returns Result.success(food)

        assertThrows<NoSuchElementException> {
            getRandomKetoDietMealsUseCase.invoke(shown)
        }
    }

    @Test
    fun `should return random meal when food list contain two keto meal but there are one is shown`() {
        // Given
        val ketoMeal = createMeal(
            id = 1, calories = 500f, totalFat = 35f,
            sugar = 2f, sodium = 400f, protein = 25f, saturated = 7f, carbohydrates = 8f
        )
        val secondKetoMeal  =  createMeal(
            id = 2, calories = 500f, totalFat = 35f,
            sugar = 2f, sodium = 400f, protein = 25f, saturated = 7f, carbohydrates = 8f
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(ketoMeal, secondKetoMeal))
        val shown = mutableListOf<Int>(1)
        // When
        val result = getRandomKetoDietMealsUseCase.invoke(shown)
        // Then
        assertThat(result).isEqualTo(secondKetoMeal)
    }

    companion object {
        @JvmStatic
        fun provideInvalidFoodLists(): Stream<Arguments> {
            return Stream.of(
                // Case 1: Empty list
                Arguments.of(emptyList<Food>(), mutableListOf<Int>()),

                // Case 2: Only non-keto meals
                Arguments.of(
                    listOf(createMeal()),
                    mutableListOf<Int>()
                ),

                // Case 3: Keto meal already shown
                Arguments.of(
                    listOf(
                        createMeal(
                            id = 1,
                            calories = 500f,
                            totalFat = 35f,
                            sugar = 2f,
                            sodium = 400f,
                            protein = 25f,
                            saturated = 7f,
                            carbohydrates = 8f
                        ),
                        createMeal()
                    ),
                    mutableListOf(1)
                )
            )
        }
    }


}