package presentation

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GetRandomFoodUseCase
import logic.usecase.GuessFoodPreparationTimeUseCase
import model.GuessPreparationTimeState
import model.RichMaxAttemptException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GuessFoodPreparationTimeGameUITest {
    private lateinit var guessFoodPreparationTimeGameUI: GuessFoodPreparationTimeGameUI
    private lateinit var getRandomFoodUseCase: GetRandomFoodUseCase
    private lateinit var guessFoodPreparationTimeUseCase: GuessFoodPreparationTimeUseCase
    private var reader: InputReader = mockk(relaxed = true)
    private var printer: Printer = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getRandomFoodUseCase = mockk()
        guessFoodPreparationTimeUseCase = mockk()
        guessFoodPreparationTimeGameUI = GuessFoodPreparationTimeGameUI(
            getRandomFoodUseCase,
            guessFoodPreparationTimeUseCase,
            reader, printer
        )
    }

    @Test
    fun `launchUI should show food name when get random food use case return food object`() {
        // given
        val food = createMeal(name = "chicken", minutes = 5)
        every { getRandomFoodUseCase.invoke() } returns food
        every { reader.readInt() } returns 5
        every { guessFoodPreparationTimeUseCase.invoke(5, 5, 1) } returns GuessPreparationTimeState.CORRECT

        // when
        guessFoodPreparationTimeGameUI.launchUI()

        // then
        verify { printer.displayLn("The Food is ${food.name}") }
    }

    @Test
    fun `launchUI should show prompt message to allow the user enter preparation time when game starts`() {
        // given
        val food = createMeal(name = "chicken", minutes = 5)
        every { getRandomFoodUseCase.invoke() } returns food
        every { reader.readInt() } returns 5
        every { guessFoodPreparationTimeUseCase.invoke(5, 5, 1) } returns GuessPreparationTimeState.CORRECT

        // when
        guessFoodPreparationTimeGameUI.launchUI()

        // then
        verify { printer.display("Guess the preparation time in minutes:") }
    }

    @ParameterizedTest
    @CsvSource("5,5,1", "5,5,3")
    fun `launchUI should show congratulation message when user guess the correct preparation time`(
        userGuess: Int,
        preparationTime: Int,
        attempts: Int
    ) {
        // given
        val food = createMeal(name = "chicken", minutes = preparationTime)
        every { getRandomFoodUseCase.invoke() } returns food
        every { reader.readInt() } returns userGuess
        every { guessFoodPreparationTimeUseCase.invoke(userGuess, preparationTime, attempts) } returns GuessPreparationTimeState.CORRECT

        // when
        guessFoodPreparationTimeGameUI.launchUI()

        // then
        verify { printer.displayLn(match { it.toString().contains(food.name!!) }) }
    }

    @Test
    fun `launchUI should show too low message when user guess is less than the preparation time`() {
        // given
        val food = createMeal(name = "chicken", minutes = 5)
        every { getRandomFoodUseCase.invoke() } returns food
        every { reader.readInt() } returns 3
        every { guessFoodPreparationTimeUseCase.invoke(3, 5, 1) } returns GuessPreparationTimeState.TOO_LOW

        // when
        guessFoodPreparationTimeGameUI.launchUI()

        // then
        verify { printer.displayLn(match { it.toString().contains("too low") }) }
    }

    @Test
    fun `launchUI should show rich max attempt message when user guess wrong after 2 attempts`() {
        // given
        val food = createMeal(name = "chicken", minutes = 5)
        every { getRandomFoodUseCase.invoke() } returns food
        every { reader.readInt() } returns 3
        every { guessFoodPreparationTimeUseCase.invoke(3, 5, any()) } throws RichMaxAttemptException(preparationTime = food.minutes)

        // when
        guessFoodPreparationTimeGameUI.launchUI()

        // then
        verify {
            printer.displayLn("You have reached the maximum number of attempts.The correct preparation time is ${food.minutes} minutes")
        }
    }

    @Test
    fun `launchUI should show too high message when user guess is higher than the preparation time`() {
        // given
        val food = createMeal(name = "chicken", minutes = 5)
        every { getRandomFoodUseCase.invoke() } returns food
        every { reader.readInt() } returns 7
        every { guessFoodPreparationTimeUseCase.invoke(7, 5, 1) } returns GuessPreparationTimeState.TOO_HIGH

        // when
        guessFoodPreparationTimeGameUI.launchUI()

        // then
        verify { printer.displayLn(match { it.toString().contains("too high") }) }
    }

    @Test
    fun `launchUI should show exception when get random food use case fail`() {
        // given
        val exceptionMessage = "can not get food"
        every { getRandomFoodUseCase.invoke() } throws Exception(exceptionMessage)

        // when
        guessFoodPreparationTimeGameUI.launchUI()

        // then
        verify { printer.displayLn(match { it.toString().contains(exceptionMessage) }) }
    }

    @Test
    fun `launchUI should show invalid input message when intReader return null`() {
        // given
        val food = createMeal(name = "chicken", minutes = 5)
        every { getRandomFoodUseCase.invoke() } returns food
        every { reader.readInt() } returns null andThen 5
        every { guessFoodPreparationTimeUseCase.invoke(5, 5, 1) } returns GuessPreparationTimeState.CORRECT

        // when
        guessFoodPreparationTimeGameUI.launchUI()

        // then
        verify { printer.displayLn(match { it.toString().contains("Invalid input.") }) }
    }
}
