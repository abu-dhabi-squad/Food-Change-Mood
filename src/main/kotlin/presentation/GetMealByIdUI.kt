package presentation

import jdk.jfr.Name
import logic.usecase.GetMealByIdUseCase
import presentation.ui_io.InputReader

class GetMealByIdUI(
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val reader: InputReader
) : ChangeFoodMoodLauncher {
    override fun launchUI() {
        print("enter id of the meal : ")
        reader.readInt()?.let { enteredID ->
            try {
                println(getMealByIdUseCase.getMealById(enteredID).getFullDetails())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }
    }
}