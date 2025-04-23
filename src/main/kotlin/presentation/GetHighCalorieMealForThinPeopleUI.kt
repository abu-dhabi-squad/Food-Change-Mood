package presentation

import logic.usecase.GetMealForThinPeopleUseCase
import presentation.ui_io.Printer

class GetHighCalorieMealForThinPeopleUI(
    private val getMealForThinPeopleUseCase: GetMealForThinPeopleUseCase,
    private val printer: Printer
) : ChangeFoodMoodLauncher {
    override fun launchUI() {
        val shownSet = mutableSetOf<Int>()
        getRandomHighCalorieMeal(shownSet)
    }

    private fun getRandomHighCalorieMeal(shownSet: MutableSet<Int>) {
        try {
            getMealForThinPeopleUseCase.getMeal(shownSet).also { suggestMeal ->
                shownSet.add(suggestMeal.id)
                printer.displayLn(suggestMeal.getNameAndDescription())
                when (isLikedMeal()) {
                    true -> printer.displayLn(suggestMeal.getFullDetails())
                    false -> getRandomHighCalorieMeal(shownSet)
                }
            }
        } catch (e: Exception) {
            e.message?.let { printer.displayLn(it) }
        }
    }
}