package presentation

import io.mockk.verify
import io.mockk.every
import io.mockk.mockk
import logic.usecase.GetRandomPotatoesMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import presentation.ui_io.Printer
import presentation.ui_io.InputReader
import javax.management.Query.match
import kotlin.test.Test

class RandomPotatoesMealsConsoleUiTest {
    private val useCase: GetRandomPotatoesMealsUseCase = mockk()
    private val reader: InputReader = mockk(relaxed = true)
    private val printer: Printer = mockk(relaxed = true)
    private lateinit var ui: RandomPotatoesMealsConsoleUi

    @BeforeEach
    fun setUp() {
        ui = RandomPotatoesMealsConsoleUi(useCase, reader, printer)
    }

    @Test
    fun `should display meals and exit when user inputs N`() {
        // Given
        val potatoMeals = listOf("Baked Potatoes", "Potato Wedges")
        every { useCase.getRandomPotatoesMeals() } returns potatoMeals
        every { reader.readString() } returns "n"

        // When
        ui.launchUI()

        // Then
        verify(exactly = 1) { useCase.getRandomPotatoesMeals() }
        verify {
            printer.displayLn(match { it.toString().contains(potatoMeals[0]) })
            printer.displayLn(match { it.toString().contains(potatoMeals[1]) })
        }
    }

    @Test
    fun `should show meals again when user inputs Y`() {
        // Given
        val meals = listOf("Fries")
        every { useCase.getRandomPotatoesMeals() } returns meals
        every { reader.readString() } returnsMany listOf("y", "n")

        // When
        ui.launchUI()

        // Then
        verify { printer.displayLn(match { it.toString().contains(meals[0]) }) }
        verify(exactly = 2) { printer.displayLn("\nWould you like to see more potato meals? (Y/N)") }
    }

    @Test
    fun `should show invalid message then exit on valid input`() {
        // Given
        every { useCase.getRandomPotatoesMeals() } returns listOf("Hash Browns")
        every { reader.readString() } returnsMany listOf("x", "n")

        // When
        ui.launchUI()

        // Then
        verify { printer.displayLn(match { it.toString().contains("Invalid", true) }) }
        verify(exactly = 1) { useCase.getRandomPotatoesMeals() }
    }

    @Test
    fun `should handle exception when use case failed`() {
        // Given
        val exceptionMessage = "Failed to fetch meals"
        every { useCase.getRandomPotatoesMeals() } throws Exception(exceptionMessage)

        // When
        ui.launchUI()

        // Then
        verify { printer.displayLn(match { it.toString().contains(exceptionMessage) }) }
    }


}