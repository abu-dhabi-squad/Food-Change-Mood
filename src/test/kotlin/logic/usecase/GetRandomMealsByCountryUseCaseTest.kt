package logic.usecase

import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.NoMealsFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertFailsWith

class GetRandomMealsByCountryUseCaseTest {

    private val foodRepository = mockk<FoodRepository>()
    private lateinit var useCase: GetRandomMealsByCountryUseCase

    @BeforeEach
    fun setup() {
        useCase = GetRandomMealsByCountryUseCase(foodRepository)
    }

    @Test
    fun `getRandomMeals should return sorted distinct names matching country`() {

        //given
        val foods = listOf(
            createFood(name = "Sushi", description = "Traditional Japanese dish", tags = listOf("Japanese")),
            createFood(name = "Ramen", description = "Hot noodle soup", tags = listOf("Japanese", "Noodles")),
            createFood(name = "Sushi", description = "Duplicate", tags = listOf("Japanese")),
            createFood(name = "Burger", description = "American style", tags = listOf("FastFood")),
        )
        // when
        every { foodRepository.getFoods() } returns Result.success(foods)

        val result = useCase.getRandomMeals("japanese")

        // then
        assertEquals(listOf("Ramen", "Sushi"), result)
    }

    @Test
    fun `getRandomMeals should throw exception when no match`() {

        // given
        val foods = listOf(
            createFood(name = "Sushi", description = "Traditional Japanese dish", tags = listOf("Japanese")),
            createFood(name = "Ramen", description = "Hot noodle soup", tags = listOf("Japanese", "Noodles")),
            createFood(name = "Sushi", description = "Duplicate", tags = listOf("Japanese")),
            createFood(name = "Burger", description = "American style", tags = listOf("FastFood")),
        )

        // when
        every { foodRepository.getFoods() } returns Result.success(foods)

        // then
        assertFailsWith<NoMealsFoundException> {
            useCase.getRandomMeals("korean")
        }
    }

    @Test
    fun `getRandomMeals should skip meals with null name`() {

        // give
        val foods = listOf(
            createFood(name = null, description = "From Japanese culture", tags = listOf("Japanese")),
            createFood(name = "Tempura", description = "Fried food", tags = listOf("Japanese"))
        )

        // when
        every { foodRepository.getFoods() } returns Result.success(foods)

        val result = useCase.getRandomMeals("japanese")

        // then
        assertEquals(listOf("Tempura"), result)
    }


    @Test
    fun `getRandomMeals should treat names with different casing as same`() {

        // given
        val foods = listOf(
            createFood(name = "SUSHI", description = "Japanese", tags = listOf("Japanese")),
            createFood(name = "sushi", description = "Also Japanese", tags = listOf("Japanese")),
            createFood(name = "Ramen", description = "Japanese", tags = listOf("Japanese"))
        )

        // when
        every { foodRepository.getFoods() } returns Result.success(foods)

        val result = useCase.getRandomMeals("japanese")

        // then
        assertEquals(listOf("Ramen", "SUSHI"), result)
    }

    @Test
    fun `getRandomMeals should throw when repository fails`() {

        // when
        every { foodRepository.getFoods() } returns Result.failure(NoMealsFoundException())

        // then
        assertFailsWith<NoMealsFoundException> {
            useCase.getRandomMeals("japanese")
        }
    }

    @Test
    fun `getRandomMeals should return distinct names only`() {

        // given
        val foods = listOf(
            createFood(name = "sushi", description = "Japanese", tags = listOf("Japanese")),
            createFood(name = "SUSHI", description = "Also Japanese", tags = listOf("Japanese")),
        )

        // when
        every { foodRepository.getFoods() } returns Result.success(foods)

        val result = useCase.getRandomMeals("japanese")

        // then
        assertEquals(listOf("sushi"), result)
    }

    @Test
    fun `getRandomMeals should skip null names and descriptions`() {

        // given
        val foods = listOf(
            createFood(name = "sushi", description = null, tags = listOf("Japanese")),
            createFood(name = null, description = null, tags = listOf("Japanese")),
        )

        // when
        every { foodRepository.getFoods() } returns Result.success(foods)

        val result = useCase.getRandomMeals("japanese")

        // then
        assertEquals(listOf("sushi"), result)
    }
}
