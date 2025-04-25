package presentation

import com.google.common.base.Verify.verify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
    private lateinit var getEasyFoodSuggestionGameUseCase: GetEasyFoodSuggestionGameUseCase
    private lateinit var getEasyFoodSuggestionUI: GetEasyFoodSuggestionUI
    private val printer: Printer = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        getEasyFoodSuggestionGameUseCase = mockk()
        getEasyFoodSuggestionUI = GetEasyFoodSuggestionUI(getEasyFoodSuggestionGameUseCase, printer)
    }

    @Test
    fun `should display start message and meals when use case returns meals`() {
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

        every { getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals() } returns meals

        // when
        getEasyFoodSuggestionUI.launchUI()

        // then
        verify { printer.displayLn(GetEasyFoodSuggestionUI.START_MESSAGE) }
        verify { printer.displayLn("1. Apple Pie") }
        verify { printer.displayLn("Prepared Time: 25 minutes") }
        verify { printer.displayLn("Description: A classic dessert.") }
        verify { printer.displayLn("Ingredients: Apples, Flour, Sugar") }
        verify { printer.displayLn("Steps: 2 steps") }
        verify { printer.displayLn(GetEasyFoodSuggestionUI.MEAL_SEPARATOR) }

        }

    @Test
    fun `should display empty input message when meals list is empty`() {
        // Given
        every { getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals() } returns emptyList()

        // When
        getEasyFoodSuggestionUI.launchUI()

        // Then
        verify { printer.displayLn(GetEasyFoodSuggestionUI.EMPTY_INPUT_MESSAGE) }
    }

    @Test
    fun `should show error message when NoEasyMealsFoundException is thrown`() {
        // Given
        every { getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals() } throws NoEasyMealsFoundException()

        // When
        getEasyFoodSuggestionUI.launchUI()

        // Then
        verify { printer.displayLn("No easy meals found matching the criteria.") }
    }
}