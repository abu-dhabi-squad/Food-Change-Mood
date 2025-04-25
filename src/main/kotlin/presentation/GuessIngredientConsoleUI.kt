package presentation

import logic.usecase.GuessIngredientUseCase
import model.WrongInputException
import presentation.ui_io.ConsolePrinter
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class GuessIngredientConsoleUI(
    private val guessIngredientUseCase: GuessIngredientUseCase,
    private val reader: InputReader,
    private val consolePrinter: Printer,
) : ChangeFoodMoodLauncher {
    override val id: Int = 11

    override val name: String = "Ingredient Guess Game"

    override fun launchUI() {
        try {
            val askedQuestions = mutableSetOf<Int>()
            var score = INITIAL_SCORE
            do {
                val question = guessIngredientUseCase.guessIngredient(MAXIMUM_INCORRECT_ANSWERS, askedQuestions)
                consolePrinter.displayLn("\nGuess The Correct Ingredient To This Meal: ${question.mealName}")
                val answers = question.getAnswers()
                answers.forEachIndexed { i, answer ->
                    consolePrinter.displayLn("\t${i + 1}. $answer")
                }
                consolePrinter.display("\nEnter Your Choice : ")
                val input = reader.readInt()
                if (input == null || input !in 1..MAXIMUM_CHOICES) throw WrongInputException()
                if (answers[input - 1] == question.correctAnswer) {
                    score += POINTS_PER_QUESTION
                    askedQuestions.add(question.mealId)
                    consolePrinter.displayLn("Great! Your Score Now: $score")
                } else {
                    consolePrinter.displayLn("Hard Luck! Your Score is: $score")
                    return
                }
            } while (askedQuestions.size < MAXIMUM_QUESTIONS)
            consolePrinter.displayLn("GAME IS COMPLETED WELL DONE!")
        } catch (e: Exception) {
            consolePrinter.displayLn(e.message)
        }
    }

    companion object {
        private const val INITIAL_SCORE = 0
        private const val MAXIMUM_CHOICES = 3
        private const val POINTS_PER_QUESTION = 1000
        private const val MAXIMUM_QUESTIONS = 15
        private const val MAXIMUM_INCORRECT_ANSWERS = 2
    }
}