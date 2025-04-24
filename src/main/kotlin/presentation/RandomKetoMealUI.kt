package presentation

import logic.usecase.GetRandomKetoDietMealsUseCase
import model.Food
import presentation.ui_io.Printer

class RandomKetoMealUI(
    private val getRandomKetoDietMealsUseCase: GetRandomKetoDietMealsUseCase,
    private val getUserTaste: GetUserTaste,
    private val printer: Printer
) : ChangeFoodMoodLauncher {
    private lateinit var ketoMeal: Food
    private val showedKetoMeals = mutableListOf<Int>()

    override fun launchUI() {
        try {
            ketoMeal = getRandomKetoDietMealsUseCase(showedKetoMeals)
            showedKetoMeals.add(ketoMeal.id)
            printer.displayLn(ketoMeal.name)
            if (getUserTaste.run()) {
                printer.displayLn(ketoMeal.getFullDetails())
            } else {
                launchUI()
            }

        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}
