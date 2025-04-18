package presentation

import logic.usecase.GetMealBySearchForNameUseCase

class GetMealBySearchForNameUI(private val getMealBySearchForNameUseCase: GetMealBySearchForNameUseCase) {

    fun executeMealSearchByName() {
        print("Enter the meal name to search: ")
        readlnOrNull()
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.let { input ->
                getMealBySearchForNameUseCase.findMealsByName(input)
                    .takeIf { it.isNotEmpty() }
                    ?.also { results ->
                        println("Search results for '$input':")
                        results.forEach { println(it.name ?: "none") }
                    }
                    ?: println("No meals found for '$input'")
            }
            ?: println("Invalid input. Please enter a non-empty keyword.")
    }
}