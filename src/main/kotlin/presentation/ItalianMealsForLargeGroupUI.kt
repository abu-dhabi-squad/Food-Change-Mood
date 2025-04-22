package presentation

import logic.usecase.GetItalianMealsForLargeGroupUseCase

class ItalianMealsForLargeGroupUI(
    private val getItalianMealsForLargeGroup: GetItalianMealsForLargeGroupUseCase
):ChangeFoodMoodLauncher {

    override fun launchUI() {
        getItalianMealsForLargeGroup.getItalianMealForLargeGroup().forEach {
            println("\nName: ${it.first}")
            println("Description: ${it.second}")
        }
    }

}