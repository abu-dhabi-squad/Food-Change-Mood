import io.mockk.*
import logic.repository.FoodRepository
import logic.usecase.GetIraqiMealsUseCase
import logic.usecase.createMeal
import model.NoIraqiMealsFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.GetIraqiMealsUI
import presentation.ui_io.Printer

class GetIraqiMealsUITest {

    private lateinit var foodRepository: FoodRepository
    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase
    private lateinit var getIraqiMealsUI: GetIraqiMealsUI
    private val consolePrinter: Printer = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        foodRepository = mockk()
        getIraqiMealsUseCase = GetIraqiMealsUseCase(foodRepository)
        getIraqiMealsUI = GetIraqiMealsUI(getIraqiMealsUseCase, consolePrinter)
    }

    @Test
    fun `should print Iraqi meals list when meals are found`() {
        // Given
        val meal = createMeal(
            id = 1,
            name = "Dolma",
            description = "Traditional Iraqi stuffed vegetables",
            tags = listOf("Iraq", "Traditional")
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(meal))

        // When
        getIraqiMealsUI.launchUI()

        // Then
        verify {
            consolePrinter.displayLn("Meal Name: Dolma")
            consolePrinter.displayLn("Description: Traditional Iraqi stuffed vegetables")
        }
    }

    @Test
    fun `should print No Iraqi meals found when filtered list is empty`() {
        // Given
        val nonIraqiMeal = createMeal(
            id = 2,
            name = "Pizza",
            description = "Italian dish",
            tags = listOf("Italian")
        )
        every { foodRepository.getFoods() } returns Result.success(listOf(nonIraqiMeal))

        // When
        getIraqiMealsUI.launchUI()

        // Then
        verify {
            consolePrinter.displayLn(NoIraqiMealsFoundException().message)
        }
    }

    @Test
    fun `should print error message when an exception occurs`() {
        // Given
        every { foodRepository.getFoods() } returns Result.failure(Exception("Something went wrong"))

        // When
        getIraqiMealsUI.launchUI()

        // Then
        verify {
            consolePrinter.displayLn("An error occurred: Something went wrong")
        }
    }
}
