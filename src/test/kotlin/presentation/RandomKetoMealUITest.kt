package presentation

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GetRandomKetoDietMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.Printer

class RandomKetoMealUITest {
    private lateinit var randomKetoMealUI: RandomKetoMealUI
    private lateinit var getRandomKetoDietMealsUseCase: GetRandomKetoDietMealsUseCase
    private lateinit var printer: Printer
    private lateinit var getUserTaste: GetUserTaste

    @BeforeEach
    fun setup() {
        getRandomKetoDietMealsUseCase = mockk()
        printer = mockk(relaxed = true)
        getUserTaste = mockk()
        randomKetoMealUI = RandomKetoMealUI(
            getRandomKetoDietMealsUseCase = getRandomKetoDietMealsUseCase,
            getUserTaste = getUserTaste,
            printer = printer
        )
    }

    @Test
    fun `launchUI should show keto meal name when get random keto meal use case return keto meal object`() {
        //given
        val ketoMeal = createMeal(name = "keto meal")
        every { getRandomKetoDietMealsUseCase.invoke(any()) } returns ketoMeal
        every { getUserTaste.run() } returns true
        //when
        randomKetoMealUI.launchUI()
        //then
        verify { printer.displayLn(match { it.toString().contains(ketoMeal.name!!) }) }
    }

    @Test
    fun `launchUI should show different keto meal name when user does not like current keto meal`() {
        //given
        val firstKetoMeal = createMeal(id = 1, name = "first keto meal")
        val secondKetoMeal = createMeal(name = "second keto meal")
        every { getRandomKetoDietMealsUseCase(mutableListOf()) } returns firstKetoMeal
        every { getRandomKetoDietMealsUseCase(mutableListOf(1)) } returns secondKetoMeal
        every { getUserTaste.run() } returns false andThen true
        //when
        randomKetoMealUI.launchUI()
        //then
        verify { printer.displayLn(match { it.toString().contains(firstKetoMeal.name!!) }) }
        verify { printer.displayLn(match { it.toString().contains(secondKetoMeal.name!!) }) }
    }

    @Test
    fun `launchUI should show keto meal name and its details when user likes current keto meal`() {
        //given
        val ketoMeal = createMeal(name = "keto meal", description = "this is keto meal description")
        every { getRandomKetoDietMealsUseCase.invoke(any()) } returns ketoMeal
        every { getUserTaste.run() } returns true
        //when
        randomKetoMealUI.launchUI()
        //then
        verify { printer.displayLn(match { it.toString().contains(ketoMeal.name!!) }) }
        verify { printer.displayLn(match { it.toString().contains(ketoMeal.getFullDetails()) }) }
    }

    @Test
    fun `launchUI should show error message when user case throws exception`() {
        //given
        val exception = Exception("there are error")
        every { getRandomKetoDietMealsUseCase.invoke(any()) } throws exception
        every { getUserTaste.run() } returns true
        //when
        randomKetoMealUI.launchUI()
        //then
        verify { printer.displayLn(match { it.toString().contains(exception.message ?: "") }) }
    }

    @Test
    fun `launchUI should show error when retrying after user rejection causes exception`() {
        //given
        val firstKetoMeal = createMeal(id = 1, name = "first")
        every { getRandomKetoDietMealsUseCase(mutableListOf()) } returns firstKetoMeal
        every { getRandomKetoDietMealsUseCase(mutableListOf(1)) } throws Exception("retry error")
        every { getUserTaste.run() } returns false
        //when
        randomKetoMealUI.launchUI()
        //then
        verify { printer.displayLn(match { it.toString().contains("retry error") }) }
    }
}
