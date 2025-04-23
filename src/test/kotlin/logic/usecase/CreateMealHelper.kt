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