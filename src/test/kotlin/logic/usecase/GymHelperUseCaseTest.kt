package logic.usecase

import com.google.common.truth.Truth
import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException
import model.Nutrition
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate
import java.util.stream.Stream

class GymHelperUseCaseTest {
    private lateinit var foodParser: FoodCsvParser
    private lateinit var foodRepository: FoodRepository
    private lateinit var gymHelperUseCase: GymHelperUseCase

    @BeforeEach
    fun setup() {
        foodParser = mockk(relaxed = true)
        foodRepository = CsvFoodRepositoryImp(foodParser)
        gymHelperUseCase = GymHelperUseCase(foodRepository)
    }

    @ParameterizedTest
    @MethodSource("provideCaloriesAndProteins")
    fun `getListOfMealsForGym should return list of meals when enter valid calories and proteins within 10 percent difference`(
        calories: Float,
        proteins: Float,
        expectedResult: List<Food>,
    ) {
        // Input
        every { foodParser.parse() } returns mutableListOf(
            createMealForGymHelper(name = "Meal 1", calories = 50.0F, proteins = 7.0F),
            createMealForGymHelper(name = "Meal 2", calories = 93.0F, proteins = 8.0F),
            createMealForGymHelper(name = "Meal 3", calories = 112.0F, proteins = 14.0F),
            createMealForGymHelper(name = "Meal 4", calories = 148.0F, proteins = 17.0F),
            createMealForGymHelper(name = "Meal 5", calories = 150.0F, proteins = 18.0F),
            createMealForGymHelper(name = "Meal 6", calories = 308.0F, proteins = 35.0F),
            createMealForGymHelper(name = "Meal 7", calories = 890.0F, proteins = 70.0F),
        )

        // When
        val result = gymHelperUseCase.getListOfMealsForGym(calories = calories, protein = proteins)

        // Then
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `getListOfMealsForGym should throw NoMealsFoundException when no meals contains calories and proteins within 10 percent difference`() {
        // Input
        every { foodParser.parse() } returns mutableListOf(
            createMealForGymHelper(name = "Meal 1", calories = 50.0F, proteins = 7.0F),
            createMealForGymHelper(name = "Meal 2", calories = 93.0F, proteins = 8.0F),
            createMealForGymHelper(name = "Meal 3", calories = 112.0F, proteins = 14.0F),
            createMealForGymHelper(name = "Meal 4", calories = 148.0F, proteins = 17.0F),
            createMealForGymHelper(name = "Meal 5", calories = 150.0F, proteins = 18.0F),
            createMealForGymHelper(name = "Meal 6", calories = 308.0F, proteins = 35.0F),
            createMealForGymHelper(name = "Meal 7", calories = 890.0F, proteins = 70.0F),
        )

        // When && then
        assertThrows<NoMealsFoundException> { gymHelperUseCase.getListOfMealsForGym(calories = 5000F, protein = 150F) }
    }

    companion object {
        @JvmStatic
        fun provideCaloriesAndProteins(): Stream<Arguments> = Stream.of(
            Arguments.of(
                149.0F, 17.0F,
                listOf(
                    createMealForGymHelper(name = "Meal 4", calories = 148.0F, proteins = 17.0F),
                    createMealForGymHelper(name = "Meal 5", calories = 150.0F, proteins = 18.0F),
                )
            ),
            Arguments.of(
                105.0F, 13.5F,
                listOf(
                    createMealForGymHelper(name = "Meal 3", calories = 112.0F, proteins = 14.0F),
                )
            )
        )
    }
}