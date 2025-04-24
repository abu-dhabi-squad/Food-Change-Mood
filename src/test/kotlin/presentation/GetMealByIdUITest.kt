package presentation

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GetMealByIdUseCase
import model.InvalidIdException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.InputReader
import presentation.ui_io.IntReader
import presentation.ui_io.Printer

class GetMealByIdUITest {
    private val printer: Printer = mockk(relaxed = true)
    private val inputReader: InputReader<Int> = mockk(relaxed = true)
    private val getMealByIdUseCase: GetMealByIdUseCase = mockk(relaxed = true)
    private lateinit var getMealByIdUI: GetMealByIdUI

    @BeforeEach
    fun setUp() {
        getMealByIdUI = GetMealByIdUI(getMealByIdUseCase, inputReader, printer)
    }

    @Test
    fun `launchUI should display enter id of the meal when called`(){
        // Given
        // When
        getMealByIdUI.launchUI()
        //then
        verify { printer.display("enter id of the meal : ") }
    }

    @Test
    fun `launchUI should display Invalid ID Input when getMealById throw InvalidIdException`(){
        // Given
        every { getMealByIdUseCase.getMealById(any()) } throws InvalidIdException()
        // When
        getMealByIdUI.launchUI()
        //then
        verify { printer.display("enter id of the meal : ") }
        verify (exactly = 1) { getMealByIdUseCase.getMealById(any()) }
        verify { printer.displayLn("Invalid ID Input") }
    }

    @Test
    fun `launchUI should display the full details of the meal when getMealById return the meal`(){
        // Given
        val meal = createMeal(id = 2000, name = "name1", description = "description1")
        every { getMealByIdUseCase.getMealById(any()) } returns meal
        // When
        getMealByIdUI.launchUI()
        //then
        verify { printer.display("enter id of the meal : ") }
        verify (exactly = 1) { getMealByIdUseCase.getMealById(any()) }
        verify { printer.displayLn(meal.getFullDetails()) }
    }

}