package logic.usecase

import model.Food
import model.Nutrition

fun createMeal(
    name: String?,
    description: String?,
    minutes: Int = 0,
    steps: List<String>,
    ingredients: List<String>
): Food {
    return Food(
        id = 0,
        name = name,
        minutes = minutes,
        submittedDate = null,
        tags = emptyList(),
        nutrition = Nutrition(
            calories = 0f,
            totalFat = 0f,
            sugar = 0f,
            sodium = 0f,
            protein = 0f,
            saturated = 0f,
            carbohydrates = 0f
        ),
        steps = steps,
        description = description,
        ingredients = ingredients
    )
}