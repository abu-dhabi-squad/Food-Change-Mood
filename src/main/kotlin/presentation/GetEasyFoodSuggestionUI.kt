package presentation

import logic.usecase.GetEasyFoodSuggestionGameUseCase
import model.Food
import model.NoEasyMealsFoundException
import presentation.ui_io.Printer

class GetEasyFoodSuggestionUI(
    private val getEasyFoodSuggestionGameUseCase: GetEasyFoodSuggestionGameUseCase,
    private val printer: Printer
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        try {
            val meals = getEasyFoodSuggestionGameUseCase.suggestRandomEasyMeals()
            if (meals.isEmpty()) {
                printer.displayLn(EMPTY_INPUT_MESSAGE)
                return
            }
            printer.displayLn(START_MESSAGE)
            printMealDetails(meals)
            printer.displayLn(MEAL_SEPARATOR)
        } catch (exception: NoEasyMealsFoundException) {
            printer.displayLn(exception.message)
        }
    }

    private fun printMealDetails(meals: List<Food>) {
        meals.forEachIndexed { index, meal ->
            printer.displayLn(MEAL_SEPARATOR)
            printer.displayLn("${index + 1}. ${meal.name}")
            printer.displayLn("$PREP_TIME_LABEL: ${meal.minutes} $MINUTES")
            printer.displayLn("$DESCRIPTION_LABEL: ${meal.description}")
            printer.displayLn("$INGREDIENTS_LABEL: ${meal.ingredients.joinToString(", ")}")
            printer.displayLn("$STEPS_LABEL: ${meal.steps.size} $STEPS")
        }
    }

    companion object{

        const val EMPTY_INPUT_MESSAGE = "No easy meals found. Please try again."
        const val START_MESSAGE = "\n Here are 10 easy meals for you: "
        const val MEAL_SEPARATOR = "--------------------------------------------------\n"
        const val MINUTES = "minutes"
        const val STEPS = "steps"
        const val PREP_TIME_LABEL = "Prepared Time"
        const val DESCRIPTION_LABEL = "Description"
        const val INGREDIENTS_LABEL = "Ingredients"
        const val STEPS_LABEL = "Steps"

    }
}