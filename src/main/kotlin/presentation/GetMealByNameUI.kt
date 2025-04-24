package presentation

import logic.usecase.GetMealBySearchForNameUseCase
import presentation.ui_io.InputReader

class GetMealByNameUI(
    private val getMealBySearchForNameUseCase: GetMealBySearchForNameUseCase,
    private val reader: InputReader
) : ChangeFoodMoodLauncher {

    override fun launchUI() {
        print("Enter the meal name to search: ")
        reader.readString()
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.let { input ->
                try {
                    printResultFromSearch(input)
                } catch (exception: Exception) {
                    println(exception)
                }
            }
            ?: println("Invalid input. Please enter a non-empty keyword.")
    }

    private fun printResultFromSearch(input: String) {
        getMealBySearchForNameUseCase.findMealsByName(input)
            .also { results ->
                println("Search results for '$input':")
                results.forEach { println(it.name ?: "none") }
            }
    }

}