package presentation

import logic.usecase.GetMealBySearchForNameUseCase

class GetMealByNameUI(private val getMealBySearchForNameUseCase: GetMealBySearchForNameUseCase) {

    fun start() {
        print("Enter the meal name to search: ")
        readlnOrNull()
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.let { input ->
                try {
                    getMealBySearchForNameUseCase.findMealsByName(input)
                        .also { results ->
                            println("Search results for '$input':")
                            results.forEach { println(it.name ?: "none") }
                        }
                }catch (exception:Exception) {
                    println(exception)
                }
            }
            ?: println("Invalid input. Please enter a non-empty keyword.")
    }
}