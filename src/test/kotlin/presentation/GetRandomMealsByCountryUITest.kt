package presentation

import io.mockk.every
import io.mockk.mockk
import logic.usecase.GetRandomMealsByCountryUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.StringReader
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertTrue

class GetRandomMealsByCountryUITest {

    private lateinit var useCase: GetRandomMealsByCountryUseCase
    private lateinit var stringReader: StringReader
    private lateinit var ui: GetRandomMealsByCountryUI
    private val outContent = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        useCase = mockk<GetRandomMealsByCountryUseCase>()
        stringReader = mockk<StringReader>()
        ui = GetRandomMealsByCountryUI(useCase, stringReader)
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun `should display meals when country has results`() {

        // given
        val country = "Mexico"
        val meals = listOf("Pizza", "Pasta")

        // when
        every { stringReader.read() } returns country
        every { useCase.getRandomMeals(country = country) } returns meals
        ui.launchUI()
        val output = outContent.toString()

        // then
        assertTrue(output.contains("1. Pizza"))
        assertTrue(output.contains("2. Pasta"))
    }

    @Test
    fun `should display error message when user input is empty or null`() {
        // given
        val country = ""
        // when
        every { stringReader.read() } returns country
        ui.launchUI()
        val output = outContent.toString()
        // then
        assertTrue(output.contains(GetRandomMealsByCountryUI.EMPTY_INPUT_MESSAGE))
    }

    @Test
    fun `should display error message when use case throws exception`() {
        // given
        val country = "UnknownLand"

        // when
        every { stringReader.read() } returns country
        every { useCase.getRandomMeals(country) } throws RuntimeException()

        ui.launchUI()
        val output = outContent.toString()

        // then
        assertTrue(output.contains(GetRandomMealsByCountryUI.NO_MEALS_MATCHED_YOUR_INPUT_MESSAGE))
    }
}

