package presentation

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import logic.usecase.GetEasyFoodSuggestionGameUseCase
import logic.usecase.createMeal
import model.NoEasyMealsFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.ConsolePrinter
import presentation.ui_io.Printer
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class GetEasyFoodSuggestionUITest {
    private lateinit var useCase: GetEasyFoodSuggestionGameUseCase
    private lateinit var ui: GetEasyFoodSuggestionUI
    private lateinit var printer: Printer
    private val outContent = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        useCase = mockk()
        printer = ConsolePrinter()
        ui = GetEasyFoodSuggestionUI(useCase, printer)
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun `should display meals when use case returns meals`() {
        // given
        val meals = listOf(
            createMeal(
                name = "Apple Pie",
                minutes = 25,
                description = "A classic dessert.",
                ingredients = listOf("Apples", "Flour", "Sugar"),
                steps = listOf("Step 1", "Step 2")
            )
        )

        // when
        every { useCase.suggestRandomEasyMeals() } returns meals
        ui.launchUI()
        val output = outContent.toString()

        // then
        val expected = listOf(
            "1. Apple Pie",
            "Prepared Time: 25 minutes",
            "Description: A classic dessert.",
            "Ingredients: Apples, Flour, Sugar",
            "Steps: 2 steps"
        )

        expected.forEach { expected ->
            assertTrue(output.contains(expected))
        }

    }

    @Test
    fun `should show message when no meals are returned`() {
        // when
        every { useCase.suggestRandomEasyMeals() } returns emptyList()
        ui.launchUI()
        val output = outContent.toString()

        // then
        assertTrue(output.contains(GetEasyFoodSuggestionUI.EMPTY_INPUT_MESSAGE))
    }

    @Test
    fun `should show error message when NoEasyMealsFoundException is thrown`() {
        // when
        every { useCase.suggestRandomEasyMeals() } throws NoEasyMealsFoundException()
        ui.launchUI()
        val output = outContent.toString()

        // then
        assertTrue(output.contains("No easy meals found matching the criteria."))
    }

}