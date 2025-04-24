package presentation

import io.mockk.mockk
import logic.usecase.EasyFoodSuggestionGameUseCase
import org.junit.jupiter.api.BeforeEach
import presentation.ui_io.ConsolePrinter
import presentation.ui_io.Printer
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class GetEasyFoodSuggestionUITest {
    private lateinit var useCase: EasyFoodSuggestionGameUseCase
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




}