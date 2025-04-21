package presentation

import logic.usecase.GuessIngredientUseCase
import model.WrongInputException
import presentation.input_reader.IntReader

class GuessIngredientConsoleUI(
    private val guessIngredientUseCase: GuessIngredientUseCase,
    private val intReader: IntReader
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            var score = INITIAL_SCORE
            val questions = guessIngredientUseCase.guessIngredient()
            questions.forEach { question ->
                println("\nGuess The Correct Ingredient To This Meal: ${question.mealName}")
                val answers = question.getAnswers()
                answers.forEachIndexed { i, answer ->
                    println("\t${i + 1}. $answer")
                }
                print("\nEnter Your Choice : ")
                val input = intReader.read()
                if (input == null || input !in 1..MAXIMUM_CHOICES) throw WrongInputException()
                if (answers[input - 1] == question.correctAnswer) {
                    score += POINTS_PER_QUESTION
                    println("Great! Your Score Now: $score")
                } else {
                    println("Hard Luck! Your Score is: $score")
                    return
                }
            }
            println("GAME IS COMPLETED WELL DONE!")
        } catch (e: Exception) {
            println(e.message)
        }
    }

    companion object {
        private const val INITIAL_SCORE = 0
        private const val MAXIMUM_CHOICES = 3
        private const val POINTS_PER_QUESTION = 1000
    }
}