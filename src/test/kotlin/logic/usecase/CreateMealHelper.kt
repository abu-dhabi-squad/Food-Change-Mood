package logic.usecase

import model.Food
import model.Nutrition
import java.time.LocalDate

fun createFood(
    id: Int = 0,
    name: String? = "Unnamed Food",
    minutes: Int = 10,
    submittedDate: LocalDate? = LocalDate.now(),
    tags: List<String> = listOf("misc"),
    nutrition: Nutrition = Nutrition(
        calories = 100f,
        totalFat = 5f,
        sugar = 5f,
        sodium = 50f,
        protein = 2f,
        saturated = 1f,
        carbohydrates = 10f
    ),
    steps: List<String> = listOf("No steps provided"),
    description: String? = "No description",
    ingredients: List<String> = listOf("Unknown ingredient")
): Food {
    return Food(
        id = id,
        name = name,
        minutes = minutes,
        submittedDate = submittedDate,
        tags = tags,
        nutrition = nutrition,
        steps = steps,
        description = description,
        ingredients = ingredients
    )
}








