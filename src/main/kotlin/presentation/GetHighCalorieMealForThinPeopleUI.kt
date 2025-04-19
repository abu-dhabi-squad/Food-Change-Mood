package presentation

import logic.usecase.GetMealForThinPeopleUseCase


fun getHighCalorieMealUI(getMealForThinPeopleUseCase: GetMealForThinPeopleUseCase){
    val shownSet = mutableSetOf<Int>()
    getRandomHighCalorieMeal(getMealForThinPeopleUseCase,shownSet)
}

private fun getRandomHighCalorieMeal(getMealForThinPeopleUseCase: GetMealForThinPeopleUseCase, shownSet: MutableSet<Int>){
    try {
        getMealForThinPeopleUseCase.getMeal(shownSet).also { suggestMeal->
            shownSet.add(suggestMeal.id)
            println("Meal Name: "+ suggestMeal.name+"\n")
            println("Meal Description: "+ suggestMeal.description+"\n")

            when(isLikedMeal()){
                true -> suggestMeal.showDetails()
                false -> getRandomHighCalorieMeal(getMealForThinPeopleUseCase, shownSet)
            }
        }
    }
    catch (e: Exception){
        println(e.message)
    }
}

