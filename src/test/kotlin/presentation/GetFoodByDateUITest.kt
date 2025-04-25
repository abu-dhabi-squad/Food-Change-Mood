package presentation

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GetFoodByDateUseCase
import logic.usecase.GetMealByIdUseCase
import model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.InputReader
import presentation.ui_io.Printer
import util.DateParser
import util.DateValidation
import java.time.LocalDate

class GetFoodByDateUITest {
    private val dateParserInterface: DateParser = mockk(relaxed = true)
    private val getFoodByDateUseCase: GetFoodByDateUseCase = mockk(relaxed = true)
    private val getMealByIdUseCase: GetMealByIdUseCase = mockk(relaxed = true)
    private val dateValidationInterface: DateValidation = mockk(relaxed = true)
    private val inputReader: InputReader = mockk(relaxed = true)
    private val printer: Printer = mockk(relaxed = true)
    private lateinit var getFoodByDateUI: GetFoodByDateUI

    @BeforeEach
    fun setup() {
        getFoodByDateUI = GetFoodByDateUI(
            dateParserInterface,
            getFoodByDateUseCase,
            getMealByIdUseCase,
            dateValidationInterface,
            inputReader,
            printer
        )
    }

    @Test
    fun `launchUI should display wrong input when enter wrong input or not entering at all`(){
        // Given
        every { inputReader.readString() } returns null
        // When
        getFoodByDateUI.launchUI()
        //then
        verify { printer.displayLn(WrongInputException().message) }
    }

    @Test
    fun `launchUI should display Invalid Date Format when DateValidationInterface throw InvalidDateFormatException`(){
        // Given
        every { dateValidationInterface.isValidDate(any()) } throws InvalidDateFormatException()
        // When
        getFoodByDateUI.launchUI()
        //then
        verify { printer.displayLn(InvalidDateFormatException().message) }
    }

    @Test
    fun `launchUI should display Invalid Year Input when DateValidationInterface throw InvalidYearException`(){
        // Given
        every { dateValidationInterface.isValidDate(any()) } throws InvalidYearException()
        // When
        getFoodByDateUI.launchUI()
        //then
        verify { printer.displayLn(InvalidYearException().message) }
    }

    @Test
    fun `launchUI should display Invalid Date Input when DateValidationInterface throw InvalidDateException`(){
        // Given
        every { dateValidationInterface.isValidDate(any()) } throws InvalidDateException()
        // When
        getFoodByDateUI.launchUI()
        //then
        verify { printer.displayLn(InvalidDateException().message) }
    }

    @Test
    fun `launchUI should display wrong Input Input when DateValidationInterface returns false`(){
        // Given
        every { dateValidationInterface.isValidDate(any()) } returns false
        // When
        getFoodByDateUI.launchUI()
        //then
        verify { printer.displayLn(WrongInputException().message) }
    }

    @Test
    fun `launchUI should display there is no meals in this date list when getFoodByDateUseCase returns EmptySearchByDateListException`(){
        // Given
        every { dateValidationInterface.isValidDate(any()) } returns true
        every { getFoodByDateUseCase.getMealsByDate(any()) } throws EmptySearchByDateListException()
        // When
        getFoodByDateUI.launchUI()
        //then
        verify { printer.displayLn(EmptySearchByDateListException().message) }
    }

    @Test
    fun `launchUI should display nothing when getFoodByDateUseCase returns Exception`(){
        // Given
        every { dateValidationInterface.isValidDate(any()) } returns true
        every { getFoodByDateUseCase.getMealsByDate(any()) } throws Exception()
        // When
        getFoodByDateUI.launchUI()
        //then
        verify { printer.displayLn("error") }
    }

    @Test
    fun `launchUI should display the id and name when getFoodByDateUseCase returns result`(){
        // Given
        val res = listOf(
            2100 to "name1",
            2200 to "name2",
            2300 to "name3"
            )
        every { getFoodByDateUseCase.getMealsByDate(any()) } returns res
        every { dateValidationInterface.isValidDate(any()) } returns true
        every { inputReader.readString() } returns "6/16/2002" andThen "n"
        // When
        getFoodByDateUI.launchUI()
        //then
        verify {res.forEach { (id, name) -> printer.displayLn("id: $id - name: $name") } }
    }

    @Test
    fun `launchUI should display Please enter Y or N when entering wrong input`(){
        // Given
        val res = listOf(
            2100 to "name1",
            2200 to "name2",
            2300 to "name3"
        )
        every { getFoodByDateUseCase.getMealsByDate(any()) } returns res
        every { dateValidationInterface.isValidDate(any()) } returns true
        every { inputReader.readString() } returns "6/16/2002" andThen "r" andThen "n"
        // When
        getFoodByDateUI.launchUI()
        //then
        verify {res.forEach { (id, name) -> printer.displayLn("id: $id - name: $name") } }
        verify { printer.displayLn("Please enter Y or N.") }
    }

    @Test
    fun `launchUI should Invalid ID Input when getFoodByIdUseCase returns InvalidIdException`(){
        // Given
        val res = listOf(
            2100 to "name1",
            2200 to "name2",
            2300 to "name3"
        )
        every { getFoodByDateUseCase.getMealsByDate(any()) } returns res
        every { dateValidationInterface.isValidDate(any()) } returns true
        every { inputReader.readString() } returns "6/16/2002" andThen "y" andThen "n"
        every { inputReader.readInt() } returns 2100
        every { getMealByIdUseCase.getMealById(any()) } throws InvalidIdException()
        // When
        getFoodByDateUI.launchUI()
        //then
        verify {res.forEach { (id, name) -> printer.displayLn("id: $id - name: $name") } }
        verify { printer.displayLn(InvalidIdException().message) }
    }

    @Test
    fun `launchUI should display error when getFoodByIdUseCase returns Exception`(){
        // Given
        val res = listOf(
            2100 to "name1",
            2200 to "name2",
            2300 to "name3"
        )
        every { getFoodByDateUseCase.getMealsByDate(any()) } returns res
        every { dateValidationInterface.isValidDate(any()) } returns true
        every { inputReader.readString() } returns "6/16/2002" andThen "y" andThen "n"
        every { inputReader.readInt() } returns 2100
        every { getMealByIdUseCase.getMealById(any()) } throws Exception()
        // When
        getFoodByDateUI.launchUI()
        //then
        verify {res.forEach { (id, name) -> printer.displayLn("id: $id - name: $name") } }
        verify { printer.displayLn("error") }
    }

    @Test
    fun `launchUI should display wrong Input when user don't enter valid id returns WrongInputException`(){
        // Given
        val res = listOf(
            2100 to "name1",
            2200 to "name2",
            2300 to "name3"
        )
        every { getFoodByDateUseCase.getMealsByDate(any()) } returns res
        every { dateValidationInterface.isValidDate(any()) } returns true
        every { inputReader.readString() } returns "6/16/2002" andThen "y" andThen "n"
        every { inputReader.readInt() } returns null
        // When
        getFoodByDateUI.launchUI()
        //then
        verify {res.forEach { (id, name) -> printer.displayLn("id: $id - name: $name") } }
        verify { printer.displayLn(WrongInputException().message) }
    }

    @Test
    fun `launchUI should display  when user don't enter valid id returns WrongInputException`(){
        // Given
        val res = listOf(
            2100 to "name1",
            2200 to "name2",
            2300 to "name3"
        )
        val meal = createMeal(id= 2100, name = "name1")
        every { getFoodByDateUseCase.getMealsByDate(any()) } returns res
        every { dateValidationInterface.isValidDate(any()) } returns true
        every { inputReader.readString() } returns "6/16/2002" andThen "y" andThen "n"
        every { getMealByIdUseCase.getMealById(any()) } returns meal
        every { inputReader.readInt() } returns 2100
        // When
        getFoodByDateUI.launchUI()
        //then
        verify {res.forEach { (id, name) -> printer.displayLn("id: $id - name: $name") } }
        verify { printer.displayLn(meal.getFullDetails()) }
    }
}