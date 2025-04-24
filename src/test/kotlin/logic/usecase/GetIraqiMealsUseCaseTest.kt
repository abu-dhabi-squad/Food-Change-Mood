package logic.usecase

import io.mockk.every
import io.mockk.mockk
import logic.repository.FoodRepository
import model.NoIraqiMealsFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetIraqiMealsUseCaseTest {

    private lateinit var foodRepository: FoodRepository
    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase

    @BeforeEach
    fun setUp() {
        foodRepository = mockk()
        getIraqiMealsUseCase = GetIraqiMealsUseCase(foodRepository)
    }

    @Test
    fun `should return food when any field contains Iraq`() {
        // Given
        val byName = createIraqiMealHelper(name = "Iraqi Kebab", description = "Yummy", tags = listOf("Grill"))
        val byDesc = createIraqiMealHelper(name = "Kebab", description = "Famous in Iraq", tags = listOf("Grill"))
        val byTag = createIraqiMealHelper(name = "Kebab", description = "Tasty", tags = listOf("iraq"))
        every { foodRepository.getFoods() } returns Result.success(listOf(byName, byDesc, byTag))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        Assertions.assertEquals(3, result.size)
    }

    @Test
    fun `should normalize null name and description`() {
        // Given
        val food = createIraqiMealHelper(name = null, description = null, tags = listOf("Iraqi"))
        every { foodRepository.getFoods() } returns Result.success(listOf(food))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = createIraqiMealHelper(name = "unnamed", description = "no description", tags = listOf("Iraqi"))
        Assertions.assertEquals(expected.name + expected.description, result[0].name + result[0].description)
    }

    @Test
    fun `should return only iraqi meals from list`() {
        // Given
        val iraqiFood = createIraqiMealHelper(name = "Dolma", description = "Traditional Iraqi dish", tags = listOf("Iraqi"))
        val otherFood = createIraqiMealHelper(name = "Pizza", description = "Italian", tags = listOf("Italian"))
        every { foodRepository.getFoods() } returns Result.success(listOf(iraqiFood, otherFood))

        // When
        val result = getIraqiMealsUseCase.getAllIraqiMeals()

        // Then
        val expected = listOf(iraqiFood)
        Assertions.assertEquals(expected.map { it.name }, result.map { it.name })
    }


    @Test
    fun `should throw exception when no iraqi food exists`() {
        // Given
        val food = createIraqiMealHelper(name = "Pizza", description = "Delicious Italian food", tags = listOf("Italian"))
        every { foodRepository.getFoods() } returns Result.success(listOf(food))

        // Then
        Assertions.assertThrows(NoIraqiMealsFoundException::class.java) {
            // When
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
    }

    @Test
    fun `should throw exception when repository fails`() {
        // Given
        val exception = RuntimeException("Network error")
        every { foodRepository.getFoods() } returns Result.failure(exception)

        // When & Then
        val thrown = Assertions.assertThrows(RuntimeException::class.java) {
            getIraqiMealsUseCase.getAllIraqiMeals()
        }
        Assertions.assertEquals("Network error", thrown.message)
    }
}
