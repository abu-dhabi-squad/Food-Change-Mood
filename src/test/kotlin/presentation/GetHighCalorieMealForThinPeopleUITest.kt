package presentation

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GetMealForThinPeopleUseCase
import model.EmptyHighCalorieListException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.Printer
import presentation.ui_io.StringReader

class GetHighCalorieMealForThinPeopleUITest{

    private val printer: Printer = mockk(relaxed = true)
    private val inputReader: StringReader = mockk(relaxed = true)
    private val getMealForThinPeopleUseCase: GetMealForThinPeopleUseCase = mockk(relaxed = true)
    private lateinit var getHighCalorieMealForThinPeopleUI : GetHighCalorieMealForThinPeopleUI

    @BeforeEach
    fun setUp(){
        getHighCalorieMealForThinPeopleUI = GetHighCalorieMealForThinPeopleUI(getMealForThinPeopleUseCase, printer, inputReader)
    }

    @Test
    fun `launchUI should display "there is no high calories meals in list" when getMeal throw EmptyHighCalorieListException`(){
        // Given
        every { getMealForThinPeopleUseCase.getMeal(any()) } throws EmptyHighCalorieListException()
        // When
        getHighCalorieMealForThinPeopleUI.launchUI()
        //then
        verify { printer.displayLn("there is no high calories meals in list") }
    }

    @Test
    fun `launchUI should display the name and the discription of the meal when getMeal returns the meal`(){
        // Given
        val meal = createMeal(id = 2000, name = "name1", description = "description1")
        every { getMealForThinPeopleUseCase.getMeal(any()) } returns meal
        // When
        getHighCalorieMealForThinPeopleUI.launchUI()
        //then
        verify { printer.displayLn(meal.getNameAndDescription()) }
    }

    @Test
    fun `launchUI should display full details of the meal when the meal is liked`(){
        // Given
        val meal = createMeal(id = 2000, name = "name1", description = "description1")
        every { getMealForThinPeopleUseCase.getMeal(any()) } returns meal
        every { inputReader.read() } returns "y"
        // When
        getHighCalorieMealForThinPeopleUI.launchUI()
        //then
        verify { printer.displayLn(meal.getFullDetails()) }
    }


    @Test
    fun `launchUI should display the name and the discription of other meal when the meal is unliked`(){
        // Given
        val meal1 = createMeal(id = 2000, name = "name1", description = "description1")
        val meal2 = createMeal(id = 2100, name = "name2", description = "description2")
        every { getMealForThinPeopleUseCase.getMeal(any()) } returns meal1 andThen meal2
        every { inputReader.read() } returns "n" andThen "y"
        // When
        getHighCalorieMealForThinPeopleUI.launchUI()
        //then
        verify { printer.displayLn(meal1.getNameAndDescription()) }
        verify { printer.displayLn(meal2.getNameAndDescription()) }
        verify { printer.displayLn(meal2.getFullDetails()) }
    }

}