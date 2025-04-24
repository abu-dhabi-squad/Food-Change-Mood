package presentation

import io.mockk.*
import logic.usecase.GetMealBySearchNameUseCase
import logic.usecase.createFood
import model.NoMealsFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GetMealByNameUITest{
    private lateinit var useCase: GetMealBySearchNameUseCase
    private lateinit var reader: InputReader
    private lateinit var printer: Printer
    private lateinit var ui: GetMealByNameUI

    @BeforeEach
    fun setup() {
        useCase = mockk()
        reader = mockk()
        printer = mockk(relaxed = true)
        ui = GetMealByNameUI(useCase, reader,printer)
    }

    @Test
    fun `should display invalid input message when input is null`() {
        // given
        val input = null
        every { reader.readString() } returns input

        // when
        ui.launchUI()

        // then
        verify {
            printer.displayLn("Invalid input. Please enter a non-empty keyword.") }
    }

    @Test
    fun `should display invalid input message when input is blank`() {
        // given
        val input = "  "
        every { reader.readString() } returns input

        // when
        ui.launchUI()

        // then
        verify {
            printer.displayLn("Invalid input. Please enter a non-empty keyword.") }
    }

    @Test
    fun `should call useCase when input is correct`() {
        // given
        val input = "Salad"
        every { reader.readString() } returns input
        every { useCase.findMealsByName(input.trim()) } returns listOf()

        // when
        ui.launchUI()

        // then
        verify {
            useCase.findMealsByName(input.trim()) }
    }

    @Test
    fun `should display results header with trimmed input`() {
        val input = "Salad  "
        every { reader.readString() } returns input
        every { useCase.findMealsByName(input.trim()) } returns listOf()

        ui.launchUI()

        verify {
            printer.displayLn("Search results for '${input.trim()}':")
        }
    }

    @Test
    fun `should display meal names returned by use case`() {
        // given
        val input = "Unnamed"
        val foods = listOf(
            createFood(id = 1,name = "Unnamed Food 1"),
            createFood(id = 2, name = "unnamed Food 2"),
            createFood(id = 3, name = null),
        )
        every { reader.readString() } returns input
        every { useCase.findMealsByName(input) } returns foods

        // when
        ui.launchUI()

        // then
        verify {
            printer.displayLn("Search results for '$input':")
            printer.displayLn("Unnamed Food 1")
            printer.displayLn("unnamed Food 2")
            printer.displayLn("none")}
    }

    @Test
    fun `should display NoMealsFoundException message if exception is thrown`() {
        // given
        val input = "Pizza"
        val exception = NoMealsFoundException()
        every { reader.readString() } returns input
        every { useCase.findMealsByName(input) } throws exception

        // when
        ui.launchUI()

        // then
        verify {
            printer.displayLn(exception.message)
        }
    }

    @Test
    fun `should display NoMealsFoundException message if all meal names are null`() {
        // given
        val searchInput = "invalid meal"
        val exception = NoMealsFoundException()

        every { reader.readString() } returns searchInput
        every { useCase.findMealsByName(searchInput) } throws exception

        // when
        ui.launchUI()

        // then
        verify {
            printer.displayLn(exception.message)
        }
        verify(exactly = 0) {
            printer.displayLn("Search results for '$searchInput':")
        }
    }
}