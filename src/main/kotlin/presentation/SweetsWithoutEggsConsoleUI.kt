package presentation

import logic.usecase.GetSweetsWithoutEggsUseCase
import presentation.ui_io.Printer

class SweetsWithoutEggsConsoleUI(
    private val getSweetsWithoutEggsUseCase: GetSweetsWithoutEggsUseCase,
    private val printer: Printer,
    private val userTaste: GetUserTaste
) : ChangeFoodMoodLauncher {
    override val id: Int
        get() = 6

    override val name: String
        get() = "Egg-Free Sweets"

    override fun launchUI() {
        val shownMeals = mutableSetOf<Int>()
        try {
            do {
                val meal = getSweetsWithoutEggsUseCase.getSweetsWithoutEggs(shownMeals)
                printer.displayLn("We suggest: ${meal.name}")
                printer.displayLn("Description: ${meal.description}")
                when (userTaste.run()) {
                    true -> {
                        printer.display(meal.getFullDetails()); break
                    }

                    false -> shownMeals.add(meal.id)
                }
            } while (true)
        } catch (exception: Exception) {
            printer.displayLn(exception.message)
        }
    }
}