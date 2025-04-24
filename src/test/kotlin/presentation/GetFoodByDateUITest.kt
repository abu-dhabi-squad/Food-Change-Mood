package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealByIdUseCase
import model.WrongInputException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.InputReader
import presentation.ui_io.Printer
import util.DateParser
import util.DateValidation
import java.time.LocalDate

class GetFoodByDateUITest{
    private lateinit var dateParser: DateParser
    private lateinit var dateValidation: DateValidation
    private lateinit var getFoodByDateUseCase: GetFoodByDateUseCase
    private lateinit var getMealByIdUseCase: GetMealByIdUseCase
    private lateinit var reader: InputReader
    private lateinit var printer: Printer
    private lateinit var ui: GetFoodByDateUI

    @BeforeEach
    fun setUp() {
        dateParser = mockk()
        dateValidation = mockk()
        getFoodByDateUseCase = mockk()
        getMealByIdUseCase = mockk()
        reader = mockk()
        printer = mockk(relaxed = true)

        ui = GetFoodByDateUI(
            dateParser, getFoodByDateUseCase, getMealByIdUseCase,
            dateValidation, reader, printer
        )
    }

    @Test
    fun `should display error message when date input is invalid`() {
        val input="invalid"
        every { reader.readString() } returns input
        every { dateValidation.isValidDate(input) } returns false

        ui.launchUI()

        verify { printer.display("Enter the Date (M/d/yyyy) : ") }
        verify { printer.displayLn(WrongInputException().message!!) }
    }

    @Test
    fun `should call getMealsByDate with parsed date`() {
        val inputDate = "4/17/2025"
        val parsedDate = LocalDate.of(2025, 4, 17)

        every { reader.readString() } returnsMany listOf(inputDate, "n")
        every { dateValidation.isValidDate(inputDate) } returns true
        every { dateParser.parseDateFromString(inputDate) } returns parsedDate
        every { getFoodByDateUseCase.getMealsByDate(parsedDate) } returns emptyList()

        ui.launchUI()

        verify { getFoodByDateUseCase.getMealsByDate(parsedDate) }
    }

    @Test
    fun `should display meals returned by date`() {
        val inputDate = "4/17/2025"
        val parsedDate = LocalDate.of(2025, 4, 17)
        val meals = listOf(1 to "Salad", 2 to "Soup")

        every { reader.readString() } returnsMany listOf(inputDate, "n")
        every { dateValidation.isValidDate(inputDate) } returns true
        every { dateParser.parseDateFromString(inputDate) } returns parsedDate
        every { getFoodByDateUseCase.getMealsByDate(parsedDate) } returns meals

        ui.launchUI()

        verify { printer.displayLn("id: 1 - name: Salad") }
        verify { printer.displayLn("id: 2 - name: Soup") }
    }


}