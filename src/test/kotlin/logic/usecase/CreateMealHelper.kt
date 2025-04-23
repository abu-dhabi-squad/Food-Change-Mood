package logic.usecase

import model.Food
import model.Nutrition
import java.time.LocalDate

fun createMealForGymHelper(name: String, calories: Float, proteins: Float): Food {
    return Food(
        id = 1,
        name = name,
        minutes = 15,
        submittedDate = null,
        nutrition = Nutrition(
            calories = calories,
            protein = proteins,
            sugar = 0.0F,
            sodium = 0.0F,
            totalFat = 0.0F,
            saturated = 0.0F,
            carbohydrates = 0.0F,
        ),
        description = "This is description for $name",
        tags = listOf(),
        steps = listOf(),
        ingredients = listOf(),
    )
}

fun createPotatoMeal(
    name: String?,
    description: String? = null,
    tags: List<String> = emptyList<String>(),
    ingredients: List<String> = emptyList<String>()
): Food {
    return Food(
        id = 0,
        name = name,
        minutes = 0,
        submittedDate = null,
        tags = tags,
        nutrition = Nutrition(0.0f, 0.0f, 0.0f, 0.0f, 0.0f,0.0f,0.0f), // default empty nutrition
        steps = emptyList(),
        description = description,
        ingredients = ingredients
    )
}