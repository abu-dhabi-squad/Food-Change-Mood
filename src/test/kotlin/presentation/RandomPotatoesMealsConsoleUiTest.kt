package presentation

import io.mockk.verify
import io.mockk.every
import io.mockk.mockk
import logic.usecase.GetRandomPotatoesMealsUseCase
import org.junit.jupiter.api.BeforeEach
import presentation.ui_io.Printer
import presentation.ui_io.InputReader
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
        every { useCase.getRandomPotatoesMeals() } returns listOf("Mashed Potatoes", "Potato Salad")
        // Given
        every { reader.readString() } returns "n"

        // When
        ui.launchUI()

        // Then
        verify { printer.displayLn("\nHere are some meals that include potatoes:\n") }
        verify { printer.displayLn("1) Mashed Potatoes") }
        verify { printer.displayLn("2) Potato Salad") }
        verify { printer.displayLn("\nWould you like to see more potato meals? (Y/N)") }
        verify { printer.displayLn("Thanks! , Enjoy your meals \n") }
    }

    @Test
    fun `should show meals again when user inputs Y`() {
        // Given
        every { useCase.getRandomPotatoesMeals() } returns listOf("Fries")
        every { reader.readString() } returnsMany listOf("y", "n")

        // When
        ui.launchUI()

        // Then
        verify(exactly = 2) { printer.displayLn("1) Fries") }
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
        verify { printer.displayLn("Invalid input. Please enter 'Y' or 'N'.") }
        verify { printer.displayLn("Thanks! , Enjoy your meals \n") }
    }

    @Test
    fun `should handle exception when use case failed`() {
        // Given
        val exceptionMessage = "Failed to fetch meals"
        every { useCase.getRandomPotatoesMeals() } throws Exception(exceptionMessage)

        // When
        ui.launchUI()

        // Then
        verify { printer.displayLn("\n$exceptionMessage") }
    }

}