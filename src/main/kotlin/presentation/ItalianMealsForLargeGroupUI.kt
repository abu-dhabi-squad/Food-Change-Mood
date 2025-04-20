package presentation

import logic.usecase.GetItalianMealsForLargeGroupUseCase

class ItalianMealsForLargeGroupUI(
    private val getItalianMealsForLargeGroup: GetItalianMealsForLargeGroupUseCase
) {
    fun start() {
            italianMealsForLargeGroup()
    }

    private fun italianMealsForLargeGroup(){
        return getItalianMealsForLargeGroup.getItalianMealForLargeGroup().forEach {
            println("\nName: ${it.first}")
            println("Description: ${it.second}")
        }
    }

}