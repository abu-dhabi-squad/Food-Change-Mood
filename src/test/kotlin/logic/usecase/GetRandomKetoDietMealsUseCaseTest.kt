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
import org.junit.jupiter.params.provider.CsvSource
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
    fun `should return meal when get meal from repo and its valid keto meal`() {
        // Given
        val meal = createMeal(id = 1, sugar = 4.9f, totalFat = 33.34f, protein = 31.25f, carbohydrates = 12.5f, saturated = 8.0f, calories = 500.0f)
        every { foodRepository.getFoods() } returns Result.success(listOf(meal))
        // When
        val result = getRandomKetoDietMealsUseCase(mutableListOf())
        // Then
        assertThat(result).isEqualTo(meal)
    }

    @ParameterizedTest
    @CsvSource(
        "5.1, 40.0, 20.0, 10.0, 8.0, 500.0",
        "2.0, 33.33, 20.0, 10.0, 8.0, 500.0",
        "2.0, 40.0, 31.26, 10.0, 8.0, 500.0",
        "2.0, 40.0, 20.0, 12.6, 8.0, 500.0",
        "2.0, 40.0, 20.0, 10.0, 7.99, 500.0",
        "2.0, 40.0, 20.0, 10.0, 10.01, 500.0",
        "0.0, 0.0, 0.0, 0.0, 0.0, 0.0",
        "2.0, 0.0, 20.0, 10.0, 0.0, 500.0"
    )
    fun `should throw exception when nutritiona values are invalid`(
        sugar: Float,
        totalFat: Float,
        protein: Float,
        carbs: Float,
        saturated: Float,
        calories: Float
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
        assertThrows<NoSuchElementException> {
            getRandomKetoDietMealsUseCase(mutableListOf())
        }
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