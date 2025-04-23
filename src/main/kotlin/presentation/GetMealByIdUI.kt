package presentation

import logic.usecase.GetMealByIdUseCase
import presentation.ui_io.InputReader

class GetMealByIdUI(
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val intReader: InputReader<Int>
) : ChangeFoodMoodLauncher {
    override fun launchUI() {
        print("enter id of the meal : ")
        intReader.read()?.let { enteredID ->
            try {
                println(getMealByIdUseCase.getMealById(enteredID).getFullDetails())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }
    }

}