package presentation

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GetHealthyMealsUseCase
import model.EmptyHealthFoodListListException

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import presentation.ui_io.Printer


class GetHealthyMealsUITest {
    private lateinit var getHealthyMealsUseCase: GetHealthyMealsUseCase
    private lateinit var ui: GetHealthyMealsUI
    private lateinit var printer: Printer

    @BeforeEach
    fun setUp() {
        getHealthyMealsUseCase = mockk(relaxed = true)
        printer = mockk(relaxed = true)
        ui = GetHealthyMealsUI(getHealthyMealsUseCase , printer)

    }

    @Test
    fun `should print healthy meals name with its description when get meal has name and description from use case`() {
        // given
        val meals = listOf(createMeal(name = "heal meal" , description = "health description"))

        every { getHealthyMealsUseCase.fetchHealthyFastFoods() } returns meals

        // when
        ui.launchUI()

        // then
        verify { printer.displayLn(match{it.toString().contains("Healthy meals:",true)}) }
        verify {
            printer.displayLn(match {
                it.toString().contains("\nName: heal meal\nDescription: health description")
            })
        }
    }


    @Test
    fun `should print healthy meals default name with default description when get meal has no name and description from use case`() {
        // given
        val meals = listOf(createMeal())

        every { getHealthyMealsUseCase.fetchHealthyFastFoods() } returns meals

        // when
        ui.launchUI()

        // then
        verify { printer.displayLn(match{it.toString().contains("Healthy meals:",true)}) }
        verify {
            printer.displayLn(match {
                it.toString().contains("\nName: Unnamed meal\nDescription: No description")
            })
        }
    }

    @Test
    fun `should print error message when exception occurs`() {
        //given
        every { getHealthyMealsUseCase.fetchHealthyFastFoods() } throws EmptyHealthFoodListListException()
        // when
        ui.launchUI()
        // then
        verify { printer.displayLn(match{it.toString().contains("No healthy meals found")})  }
    }
}