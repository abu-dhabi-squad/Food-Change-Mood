package logic.usecase

import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.NoMealsFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class GetMealBySearchNameUseCaseTest {

    private lateinit var repository: FoodRepository
    private lateinit var getMealBySearchNameUseCase: GetMealBySearchNameUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getMealBySearchNameUseCase = GetMealBySearchNameUseCase(repository)
    }

    @Test
    fun `should return meals that match the input partially`() {
        //given
        val foods = listOf(
            createFood(name = "Apple Pie"),
            createFood(name = "Banana Bread"),
            createFood(name = "Grilled Cheese")
        )
        every { repository.getFoods() } returns Result.success(foods)

        //when
        val result = getMealBySearchNameUseCase.findMealsByName("Pie")

        //then
        assertEquals(1, result.size)
        assertEquals("Apple Pie", result.first().name)
    }

    @Test
    fun `should return multiple matching meals`() {

        //given
        val foods = listOf(
            createFood(name = "Apple Pie"),
            createFood(name = "Apple Crumble"),
            createFood(name = "Grilled Chicken")
        )
        every { repository.getFoods() } returns Result.success(foods)

        //when
        val result = getMealBySearchNameUseCase.findMealsByName("Apple")

        //then
        assertEquals(2, result.size)
        assertEquals("Apple Pie", result.first().name)
        assertEquals("Apple Crumble", result.last().name)
    }

    @Test
    fun `should be case-insensitive when matching names`() {

        //given
        val foods = listOf(
            createFood(name = "Apple Pie"),
            createFood(name = "apple crumble")
        )
        every { repository.getFoods() } returns Result.success(foods)

        //when
        val result = getMealBySearchNameUseCase.findMealsByName("aPpLe")

        //then
        assertEquals(2, result.size)
        assertEquals("Apple Pie", result.first().name)
        assertEquals("apple crumble", result.last().name)
    }

    @Test
    fun `should return empty when no name matches input`() {

        //given
        val foods = listOf(
            createFood(name = "Banana Bread"),
            createFood(name = "Grilled Chicken")
        )
        every { repository.getFoods() } returns Result.success(foods)

        //when and then
        assertFailsWith<NoMealsFoundException> {
            getMealBySearchNameUseCase.findMealsByName("Pie")
        }
    }

    @Test
    fun `should ignore foods with null names`() {

        //given
        val foods = listOf(
            createFood(name = null),
            createFood(name = "Apple Pie")
        )
        every { repository.getFoods() } returns Result.success(foods)

        //when and then
        val result = getMealBySearchNameUseCase.findMealsByName("Pie")

        //then
        assertEquals(1, result.size)
        assertEquals("Apple Pie", result.first().name)
    }

    @Test
    fun `should return all meals when input is empty`() {

        //given
        val foods = listOf(
            createFood(name = "Pizza"),
            createFood(name = "Pasta")
        )
        every { repository.getFoods() } returns Result.success(foods)

        //when
        val result = getMealBySearchNameUseCase.findMealsByName("")

        //then
        assertEquals(2, result.size)
    }

    @Test
    fun `should throw NoMealsFoundException when repository fails`() {
        // given
        every { repository.getFoods() } returns Result.failure(NoMealsFoundException())

        // then
        assertFailsWith<NoMealsFoundException> {
            getMealBySearchNameUseCase.findMealsByName("Pizza")
        }
    }

    @Test
    fun `should throw NoMealsFoundException when repository returns empty list`() {
        // given
        every { repository.getFoods() } returns Result.success(emptyList())

        // then
        assertFailsWith<NoMealsFoundException> {
            getMealBySearchNameUseCase.findMealsByName("Pizza")
        }
    }

}