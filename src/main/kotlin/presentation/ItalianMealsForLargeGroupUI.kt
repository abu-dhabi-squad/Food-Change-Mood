package presentation

import logic.GetItalianMealsForLargeGroupUsesCase

class ItalianMealsForLargeGroupUI(
    private val getItalianMealsForLargeGroup: GetItalianMealsForLargeGroupUsesCase
) {
    fun start() {
            italianMealsForLargeGroup()
    }

    private fun italianMealsForLargeGroup(){
        return getItalianMealsForLargeGroup.getItalianMealForLargeGroup().forEach {
            println(it.first)
            println(it.second)
        }
    }

}