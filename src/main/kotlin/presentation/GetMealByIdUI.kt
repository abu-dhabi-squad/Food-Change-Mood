package presentation

import logic.usecase.GetMealByIdUseCase
import presentation.ui_io.IntReader
import util.showDetails

class GetMealByIdUI(
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val intReader: IntReader
) : ChangeFoodMoodLauncher {
    override fun launchUI() {
        print("enter id of the meal : ")
        intReader.read()?.let { enteredID ->
            try {
                getMealByIdUseCase.getMealById(enteredID).showDetails()
            } catch (exception: Exception) {
                println(exception.message)
            }
        }
    }

}