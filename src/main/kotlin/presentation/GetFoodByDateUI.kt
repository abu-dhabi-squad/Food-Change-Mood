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
                        getFoodByDateUseCase.getMealById(it).also { meal->
                            meal.showDetails()
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
