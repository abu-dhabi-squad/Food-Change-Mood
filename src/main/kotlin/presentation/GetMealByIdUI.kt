package presentation

import logic.usecase.GetMealByIdUseCase

class GetMealByIdUI(
    private val getMealByIdUseCase: GetMealByIdUseCase
)
{
    fun getDetailsById() {
        print("enter id of the meal : ")
        readLine()?.toIntOrNull()?.let { enteredID ->
            try {
                getMealByIdUseCase.getMealById(enteredID).showDetails()
            }catch (exception:Exception){
                println(exception.message)
            }
        }
    }

}