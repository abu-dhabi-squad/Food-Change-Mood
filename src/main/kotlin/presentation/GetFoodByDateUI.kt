package presentation

import logic.usecase.GetFoodByDateUseCase
import model.Food
import java.time.LocalDate

fun getRandomHighCalorieMeal(getFoodByDateUseCase: GetFoodByDateUseCase){
    try {
        getFoodByDateUseCase.getMealsByDate(LocalDate.of(2005,11,11))
            .apply {
                this.forEach{ it -> println("id : " + it.first+ " - name: " + it.second)}
                print("enter id: ")
                readLine()?.toIntOrNull()?.let { it->
                    if(this.any{item -> item.first == it}){
                        getFoodByDateUseCase.getMealById(it).also {
                            showMealDetails(it)
                        }
                    }

                }
            }

    }
    catch (e: Exception){
        println(e.message)
    }
}

private fun getInputDate(){
    print("Enter year: ")
}


private fun showMealDetails(food: Food){
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
