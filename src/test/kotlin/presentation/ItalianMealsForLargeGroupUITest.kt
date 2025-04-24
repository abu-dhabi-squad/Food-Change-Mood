package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GetItalianMealsForLargeGroupUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.Printer

class ItalianMealsForLargeGroupUITest {

    private val useCase: GetItalianMealsForLargeGroupUseCase = mockk()
    private val printer: Printer = mockk(relaxed = true)
    private lateinit var ui: ItalianMealsForLargeGroupUI

    @BeforeEach
    fun setUp() {
        ui = ItalianMealsForLargeGroupUI(useCase, printer)
    }

    @Test
    fun `should display italian meals with their descriptions`() {
        // Given
        every { useCase.getItalianMealForLargeGroup() } returns listOf(
            "Pizza Margherita" to "Classic Neapolitan pizza with tomato, mozzarella, and basil",
            "Lasagna" to "Layered pasta with meat and cheese"
        )

        // When
        ui.launchUI()

        // Then
        verify { printer.displayLn("\nName: Pizza Margherita") }
        verify { printer.displayLn("Description: Classic Neapolitan pizza with tomato, mozzarella, and basil") }
        verify { printer.displayLn("\nName: Lasagna") }
        verify { printer.displayLn("Description: Layered pasta with meat and cheese") }
    }
}
