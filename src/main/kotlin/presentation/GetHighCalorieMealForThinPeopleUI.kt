package presentation

import logic.usecase.GetMealForThinPeopleUseCase
import presentation.ui_io.Printer
import util.showDetails

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
                printer.println("Meal Name: " + suggestMeal.name + "\n")
                printer.println("Meal Description: " + suggestMeal.description + "\n")
                when (isLikedMeal()) {
                    true -> suggestMeal.showDetails()
                    false -> getRandomHighCalorieMeal(shownSet)
                }
            }
        } catch (e: Exception) {
            e.message?.let { printer.println(it) }
        }
    }


}