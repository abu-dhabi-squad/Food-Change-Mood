package presentation

import logic.usecase.GetMealForThinPeopleUseCase

class GetHighCalorieMealForThinPeopleUI(
    private val getMealForThinPeopleUseCase: GetMealForThinPeopleUseCase,
) : ChangeFoodMoodLauncher {
    override fun launchUI() {
        val shownSet = mutableSetOf<Int>()
        getRandomHighCalorieMeal(shownSet)
    }

    private fun getRandomHighCalorieMeal(shownSet: MutableSet<Int>) {
        try {
            getMealForThinPeopleUseCase.getMeal(shownSet).also { suggestMeal ->
                shownSet.add(suggestMeal.id)
                println("Meal Name: " + suggestMeal.name + "\n")
                println("Meal Description: " + suggestMeal.description + "\n")

                when (isLikedMeal()) {
                    true -> suggestMeal.showDetails()
                    false -> getRandomHighCalorieMeal(shownSet)
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }


}