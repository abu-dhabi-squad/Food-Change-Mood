package presentation

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import logic.usecase.GetSeaFoodMealsSortedByProteinUseCase
import model.EmptySearchByDateListException
import org.junit.jupiter.api.BeforeEach
import presentation.ui_io.Printer
import kotlin.test.Test

class SeaFoodMealsSortedByProteinUITest {

    private lateinit var useCase: GetSeaFoodMealsSortedByProteinUseCase
    private val consolePrinter: Printer = mockk(relaxed = true)
    private lateinit var ui: SeaFoodMealsSortedByProteinUI

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        ui = SeaFoodMealsSortedByProteinUI(useCase, consolePrinter)
    }

    @Test
    fun `launchUI should display meals sorted by protein`() {
        // Given
        val meals = listOf(
            createMeal(id = 1, name = "Crispy Crab Cakes with Tomato Butter", tags = listOf("Seafood"), protein = 20.0f),
            createMeal(id = 4, name = "Seared Tuna with Soy and Red Chiles", tags = listOf("seaFOOD"), protein = 18.0f),
            createMeal(id = 2, name = "Mussels with Harissa and Basil", tags = listOf("Seafood"), protein = 15.0f)
        )
        every { useCase() } returns meals

        // When
        ui.launchUI()

        // Then
        verify { useCase() }
        verifySequence {
            consolePrinter.displayLn("🍤 All Seafood Meals Sorted by Protein Content:")
            consolePrinter.displayLn("1. Crispy Crab Cakes with Tomato Butter - \u001B[32m${"Protein: 20.0g"}\u001B[0m")
            consolePrinter.displayLn("2. Seared Tuna with Soy and Red Chiles - \u001B[32m${"Protein: 18.0g"}\u001B[0m")
            consolePrinter.displayLn("3. Mussels with Harissa and Basil - \u001B[32m${"Protein: 15.0g"}\u001B[0m")
        }
    }


    @Test
    fun `launchUI should display error when use case throws exception`() {
        // Given
        every { useCase() } throws EmptySearchByDateListException()

        // When
        ui.launchUI()

        // Then
        verify { consolePrinter.displayLn(match { it.toString().contains("no meals") }) }
    }
}
