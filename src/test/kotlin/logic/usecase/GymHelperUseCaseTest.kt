package logic.usecase

import com.google.common.truth.Truth
import data.CsvFoodRepositoryImp
import data.FoodCsvParser
import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.Food
import model.NoMealsFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class GymHelperUseCaseTest {
    private lateinit var foodRepository: FoodRepository
    private lateinit var gymHelperUseCase: GymHelperUseCase

    @BeforeEach
    fun setup() {
        foodRepository = mockk(relaxed = true)
        gymHelperUseCase = GymHelperUseCase(foodRepository)
    }

    @ParameterizedTest
    @MethodSource("provideValidCaloriesAndProteins")
    fun `getListOfMealsForGym should return list of meals when enter valid calories and proteins within 10 percent difference`(
        calories: Float,
        proteins: Float,
        expectedResult: List<Food>,
    ) {
        // Input
        every { foodRepository.getFoods() } returns Result.success(mutableListOf(
            createMealForGymHelper(name = "Meal 1", calories = 50.0F, proteins = 7.0F),
            createMealForGymHelper(name = "Meal 2", calories = 93.0F, proteins = 8.0F),
            createMealForGymHelper(name = "Meal 3", calories = 112.0F, proteins = 14.0F),
            createMealForGymHelper(name = "Meal 4", calories = 148.0F, proteins = 17.0F),
            createMealForGymHelper(name = "Meal 5", calories = 150.0F, proteins = 18.0F),
            createMealForGymHelper(name = "Meal 6", calories = 308.0F, proteins = 35.0F),
            createMealForGymHelper(name = "Meal 7", calories = 890.0F, proteins = 70.0F),
        ))

        // When
        val result = gymHelperUseCase.getListOfMealsForGym(calories = calories, protein = proteins)

        // Then
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @CsvSource(
        "1149.0, 17.0",
        "105.0, 130.5"
    )
    fun `getListOfMealsForGym should throw NoMealsFoundException when enter invalid calories and proteins within 10 percent difference`(
        calories: Float,
        proteins: Float,
    ) {
        // Input
        every { foodRepository.getFoods() } returns Result.success(mutableListOf(
            createMealForGymHelper(name = "Meal 1", calories = 50.0F, proteins = 7.0F),
            createMealForGymHelper(name = "Meal 2", calories = 93.0F, proteins = 8.0F),
            createMealForGymHelper(name = "Meal 3", calories = 112.0F, proteins = 14.0F),
            createMealForGymHelper(name = "Meal 4", calories = 148.0F, proteins = 17.0F),
            createMealForGymHelper(name = "Meal 5", calories = 150.0F, proteins = 18.0F),
            createMealForGymHelper(name = "Meal 6", calories = 308.0F, proteins = 35.0F),
            createMealForGymHelper(name = "Meal 7", calories = 890.0F, proteins = 70.0F),
        ))

        // When && then
        assertThrows<NoMealsFoundException> { gymHelperUseCase.getListOfMealsForGym(calories = calories, protein = proteins) }
    }

    @Test
    fun `getListOfMealsForGym should throw NoMealsFoundException when no meals in repository`() {
        // Input
        every { foodRepository.getFoods() } returns Result.success(mutableListOf())

        // When && then
        assertThrows<NoMealsFoundException> { gymHelperUseCase.getListOfMealsForGym(calories = 1500F, protein = 15F) }
    }

    @Test
    fun `getListOfMealsForGym should throw Exception when getFoods throws Exception`() {
        // Input
        every { foodRepository.getFoods() } returns Result.failure(Exception())

        // When && then
        assertThrows<Exception> { gymHelperUseCase.getListOfMealsForGym(calories = 1500F, protein = 15F) }
    }

    companion object {
        @JvmStatic
        fun provideValidCaloriesAndProteins(): Stream<Arguments> = Stream.of(
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