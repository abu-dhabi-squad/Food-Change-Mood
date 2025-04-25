package presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GuessIngredientUseCase
import model.IngredientQuestion
import model.WrongInputException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GuessIngredientConsoleUITest {

    private lateinit var guessIngredientUseCase: GuessIngredientUseCase
    private val reader: InputReader = mockk(relaxed = true)
    private val consolePrinter: Printer = mockk(relaxed = true)
    private lateinit var guessIngredientConsoleUI: GuessIngredientConsoleUI

    @BeforeEach
    fun setUp() {
        guessIngredientUseCase = mockk(relaxed = true)
        guessIngredientConsoleUI = GuessIngredientConsoleUI(guessIngredientUseCase, reader, consolePrinter)
    }

    @Test
    fun `guessIngredient should complete all question successfully when answering all question right`() {
        // Give
        val answeredQuestions = MAXIMUM_QUESTIONS
        val question = mockk<IngredientQuestion>(relaxed = true)
        every { guessIngredientUseCase.guessIngredient(MAXIMUM_INCORRECT_ANSWERS, any()) } returns question
        every { question.correctAnswer } returns "correct answer"
        every { question.mealId } returnsMany (1..MAXIMUM_QUESTIONS).toList()
        every { question.getAnswers() } returns listOf("correct answer", "wrong answer 1", "wrong answer 2")
        every { reader.readInt() } returns 1

        // When
        guessIngredientConsoleUI.launchUI()

        // Then
        verify(exactly = MAXIMUM_QUESTIONS) { guessIngredientUseCase.guessIngredient(MAXIMUM_INCORRECT_ANSWERS, any()) }
        verify { consolePrinter.displayLn(match { it.toString().contains(calculateScore(answeredQuestions).toString()) }) }
    }

    @Test
    fun `guessIngredient should end game when any incorrect answer entered`() {
        // Give
        val answeredQuestions = 1
        val question = mockk<IngredientQuestion>(relaxed = true)
        every { guessIngredientUseCase.guessIngredient(MAXIMUM_INCORRECT_ANSWERS, any()) } returns question
        every { question.correctAnswer } returns "correct answer"
        every { question.mealId } returnsMany (1..MAXIMUM_QUESTIONS).toList()
        every { question.getAnswers() } returns listOf("correct answer", "wrong answer 1", "wrong answer 2")
        every { reader.readInt() } returns 1 andThen 2

        // When
        guessIngredientConsoleUI.launchUI()

        // Then
        verify(exactly = answeredQuestions + 1) { guessIngredientUseCase.guessIngredient(MAXIMUM_INCORRECT_ANSWERS, any()) }
        verify { consolePrinter.displayLn(
            match { it.toString().contains("Hard Luck!") && it.toString().contains(calculateScore(answeredQuestions).toString()) })
        }
    }

    private fun calculateScore(answered: Int): Int {
        return INITIAL_SCORE + answered * POINTS_PER_QUESTION
    }

    @Test
    fun `guessIngredient should throw when wrong input entered`() {
        // Give
        val question = mockk<IngredientQuestion>(relaxed = true)
        every { guessIngredientUseCase.guessIngredient(MAXIMUM_INCORRECT_ANSWERS, any()) } returns question
        every { question.getAnswers() } returns listOf("correct answer", "wrong answer 1", "wrong answer 2")
        every { reader.readInt() } returns MAXIMUM_INCORRECT_ANSWERS + 2

        // When
        guessIngredientConsoleUI.launchUI()

        // Then
        verify(exactly = 1) { guessIngredientUseCase.guessIngredient(MAXIMUM_INCORRECT_ANSWERS, any()) }
        verify { consolePrinter.displayLn(match { it.toString().contains(WrongInputException().message ?: "") }) }
    }

    @Test
    fun `guessIngredient should throw when null input entered`() {
        // Give
        val question = mockk<IngredientQuestion>(relaxed = true)
        every { guessIngredientUseCase.guessIngredient(MAXIMUM_INCORRECT_ANSWERS, any()) } returns question
        every { question.getAnswers() } returns listOf("correct answer", "wrong answer 1", "wrong answer 2")
        every { reader.readInt() } returns null

        // When
        guessIngredientConsoleUI.launchUI()

        // Then
        verify(exactly = 1) { guessIngredientUseCase.guessIngredient(MAXIMUM_INCORRECT_ANSWERS, any()) }
        verify { consolePrinter.displayLn(match { it.toString().contains(WrongInputException().message ?: "") }) }
    }
    
    companion object {
        private const val INITIAL_SCORE = 0
        private const val POINTS_PER_QUESTION = 1000
        private const val MAXIMUM_QUESTIONS = 15
        private const val MAXIMUM_INCORRECT_ANSWERS = 2
    }
}