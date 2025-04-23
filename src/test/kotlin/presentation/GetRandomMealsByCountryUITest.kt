package presentation

import io.mockk.every
import io.mockk.mockk
import logic.usecase.GetRandomMealsByCountryUseCase
import model.NoMealsFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.ConsolePrinter
import presentation.ui_io.Printer
import presentation.ui_io.StringReader
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertTrue

class GetRandomMealsByCountryUITest {

    private lateinit var useCase: GetRandomMealsByCountryUseCase
    private lateinit var stringReader: StringReader
    private lateinit var ui: GetRandomMealsByCountryUI
    private val outContent = ByteArrayOutputStream()
    private lateinit var printer: Printer

    @BeforeEach
    fun setUp() {
        useCase = mockk<GetRandomMealsByCountryUseCase>()
        stringReader = mockk<StringReader>()
        printer = ConsolePrinter()
        ui = GetRandomMealsByCountryUI(useCase, stringReader, printer)
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
    fun `should display error message when user input is empty`() {
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
        every { useCase.getRandomMeals(country) } throws NoMealsFoundException()

        ui.launchUI()
        val output = outContent.toString()

        // then
        assertTrue(output.contains(GetRandomMealsByCountryUI.NO_MEALS_FOUND))
    }

    @Test
    fun `should display error message when user input is null`() {
        // given
        val country = null
        // when
        every { stringReader.read() } returns country
        ui.launchUI()
        val output = outContent.toString()
        // then
        assertTrue(output.contains(GetRandomMealsByCountryUI.EMPTY_INPUT_MESSAGE))
    }
}

