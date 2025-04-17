package presentation

import logic.usecase.GetMealForThinPeopleUseCase
import model.Food
import model.WrongInputException


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

            when(isTheMealLikable()){
                true -> suggestMeal.showDetails()
                false -> getRandomHighCalorieMeal(getMealForThinPeopleUseCase, shownSet)
            }
        }
    }
    catch (e: Exception){
        println(e.message)
    }
}

private fun isTheMealLikable():Boolean{
    println("Do you like it? {y/n}")
    readLine().let {
        when{
            it.equals("y",true) -> { return true }
            it.equals("n",true) -> { return false }
            else -> {
                throw WrongInputException()
            }
        }
    }
}