package logic.usecase

import model.Food
import model.Nutrition
import java.time.LocalDate

fun createMealHelper(
    id: Int = 0,
    name: String? = "Unnamed Food",
    description: String? = "No description",
    minutes: Int = 10,
    submittedDate: LocalDate? = LocalDate.now(),
    tags: List<String> = listOf("misc"),
    steps: List<String> = listOf("No steps provided"),
    ingredients: List<String> = listOf("Unknown ingredient"),
    calories: Float = 100f,
    totalFat: Float = 5f,
    sugar: Float = 5f,
    sodium: Float = 50f,
    protein: Float = 2f,
    saturated: Float = 1f,
    carbohydrates: Float = 10f
): Food {
    return Food(
        id = id,
        name = name,
        minutes = minutes,
        submittedDate = submittedDate,
        tags = tags,
        steps = steps,
        description = description,
        ingredients = ingredients,
        nutrition = Nutrition(
            calories = calories,
            totalFat = totalFat,
            sugar = sugar,
            sodium = sodium,
            protein = protein,
            saturated = saturated,
            carbohydrates = carbohydrates
        )
    )
}


fun createMeal(
    id: Int,
    name: String? = null,
    description: String? = null,
    minutes:Int = 0,
    submittedDate: LocalDate = LocalDate.now(),
    tags: List<String> = listOf(),
    calories: Float = 0f,
    totalFat: Float = 0f,
    sugar: Float = 0f,
    sodium: Float = 0f,
    protein: Float = 0f,
    saturated: Float = 0f,
    carbohydrates: Float = 0f,
    steps:List<String> = listOf(),
    ingredients:List<String> = listOf()
): Food {
    return Food(
        id,
        name,
        minutes = minutes,
        submittedDate = submittedDate,
        tags = tags,
        nutrition = Nutrition(calories = calories, totalFat = totalFat, sugar, sodium = sodium, protein = protein, saturated = saturated, carbohydrates = carbohydrates),
        steps = steps,
        description = description,
        ingredients = ingredients
    )
}


