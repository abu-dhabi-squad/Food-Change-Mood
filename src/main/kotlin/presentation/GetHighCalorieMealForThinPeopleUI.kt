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
                true -> showMealDetails(suggestMeal)
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

fun showMealDetails(food: Food){
    println("preparation time : " + food.minutes)
    food.submittedDate?.also {
        println("submittedDate : " +food.submittedDate.dayOfMonth+"-"+food.submittedDate.month.value+"-"+food.submittedDate.year+"\n")
    }
    println("\n"+"nutrition : ")
    food.nutrition.also {
        println("- calories = "+ it.calories)
        println("- sodium = "+ it.sodium)
        println("- sugar = "+ it.sugar)
        println("- protein = "+ it.protein)
        println("- totalFat = "+ it.totalFat)
        println("- carbohydrates = "+ it.carbohydrates)
        println("- saturated = "+ it.saturated)
    }
    println("\n"+"ingredients : ")
    food.ingredients.forEach { println("- " +it) }
    println("\n"+"steps : ")
    food.steps.forEach { println("- " + it) }
}