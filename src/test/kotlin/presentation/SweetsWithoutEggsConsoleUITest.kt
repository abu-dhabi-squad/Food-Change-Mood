package presentation

import createMeal
import io.mockk.*
import logic.usecase.GetSweetsWithoutEggsUseCase
import presentation.ui_io.Printer
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SweetsWithoutEggsConsoleUITest {
    private val useCase: GetSweetsWithoutEggsUseCase = mockk()
    private val printer: Printer = mockk(relaxed = true)
    private val userTaste: GetUserTaste = mockk(relaxed = true)
    private lateinit var ui: SweetsWithoutEggsConsoleUI

    @BeforeEach
    fun setUp() {
        // Pass the isLikedMeal function as a lambda to control it in tests
        ui = SweetsWithoutEggsConsoleUI(
            useCase,
            printer,
            userTaste
        )
    }

    @Test
    fun `should display meal and print full details when user likes it`() {
        // Given
        val meal = createMeal(1, "Sweet Cake", "Eggless cake", tags = listOf("sweet"))
        every { useCase.getSweetsWithoutEggs(any()) } returns meal
        every { userTaste.run() } returns true

        // When
        ui.launchUI()

        // Then
        verify {
            printer.displayLn(match { it.toString().contains(meal.name ?: "") })
            printer.displayLn(match { it.toString().contains(meal.description ?: "") })
            printer.display(match { it.toString().contains(meal.getFullDetails()) })
        }
    }

    @Test
    fun `should suggest another meal when user dislikes it`() {
        // Given
        val shownMeals = mutableSetOf<Int>()
        val firstMeal = createMeal(1, "Eggless Donut", "Sweet donut", tags = listOf("sweet"))
        val secondMeal = createMeal(2, "Vegan Brownie", "Choco delight", tags = listOf("sweet"))
        every { useCase.getSweetsWithoutEggs(mutableSetOf()) } returns firstMeal
        every { useCase.getSweetsWithoutEggs(mutableSetOf(1)) } returns secondMeal
        every { userTaste.run() } returns false andThen true

        // When
        ui.launchUI()

        // Then
        verify {
            printer.displayLn(match { it.toString().contains(firstMeal.name ?: "") })
            printer.displayLn(match { it.toString().contains(firstMeal.description ?: "") })
            shownMeals.add(firstMeal.id)
            printer.displayLn(match { it.toString().contains(secondMeal.name ?: "") })
            printer.displayLn(match { it.toString().contains(secondMeal.description ?: "") })
            printer.display(match { it.toString().contains(secondMeal.getFullDetails()) })
        }
    }

    @Test
    fun `should print error message when use case throws exception`() {
        // Given
        val exceptionMessage = "No sweets available"
        every { useCase.getSweetsWithoutEggs(any()) } throws Exception(exceptionMessage)

        // When
        ui.launchUI()

        // Then
        verify { printer.displayLn(exceptionMessage) }
    }
}

