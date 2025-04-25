package presentation

import logic.usecase.GetRandomKetoDietMealsUseCase
import model.Food
import presentation.ui_io.InputReader
import presentation.ui_io.Printer

class RandomKetoMealUI(
    private val getRandomKetoDietMealsUseCase: GetRandomKetoDietMealsUseCase,
    private val getUserTaste : GetUserTaste,
    private val printer : Printer
) : ChangeFoodMoodLauncher {
    override val id: Int
        get() = 7

    override val name: String
        get() = "Keto Diet Meal Helper"

    private lateinit var ketoMeal: Food
    private val showedKetoMeals = mutableListOf<Int>()

    override fun launchUI() {
        try {
            ketoMeal = getRandomKetoDietMealsUseCase(showedKetoMeals)
            showedKetoMeals.add(ketoMeal.id)
            printer.displayLn( "${ketoMeal.name}")
            if (getUserTaste.run()) {
                printer.displayLn(ketoMeal.getFullDetails())
            } else {
                launchUI()
            }
        } catch (ex: Exception) {
            printer.displayLn(ex.message)
        }
    }
}
