package presentation

import logic.usecase.GetMealForThinPeopleUseCase
import model.Food


fun getHighCalorieMealUI(getMealForThinPeopleUseCase: GetMealForThinPeopleUseCase){
    try {
        getMealForThinPeopleUseCase.getMeal().let { suggestMeal->
            println("Meal Name: "+ suggestMeal.name+"\n")
            println("Meal Description: "+ suggestMeal.description+"\n")

            when(isTheMealLikable(getMealForThinPeopleUseCase)){
                true -> showMealDetails(suggestMeal)
                false -> getHighCalorieMealUI(getMealForThinPeopleUseCase)
            }
        }
    }
    catch (e: Exception){
        println(e.message)
    }
}

private fun isTheMealLikable(getMealForThinPeopleUseCase : GetMealForThinPeopleUseCase):Boolean{
    println("Do you like it? {y/n}")
    readLine().let {
        when{
            it == "Y" || it == "y" -> { return true }
            it == "N" || it == "n" -> {
                getMealForThinPeopleUseCase.dislikeTheCurrentMeal()
                return false
            }
            else -> {
                throw Exception("wrong Input")
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
