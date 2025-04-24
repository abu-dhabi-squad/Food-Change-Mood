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
        nutrition = Nutrition(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), // default empty nutrition
        steps = emptyList(),
        description = description,
        ingredients = ingredients
    )
}


fun createIraqiMealHelper(
    name: String? = " Name",
    description: String? = " Description",
    tags: List<String> = listOf("tag")
): Food {
    return Food(
        id = 0,
        name = name,
        minutes = 0,
        submittedDate = null,
        tags = tags,
        nutrition = Nutrition(0.0f, 0.0f, 0.0f, 0.0f, 0.0f,0.0f,0.0f),
        steps = emptyList(),
        description = description,
        ingredients =   listOf(),
    )
}


