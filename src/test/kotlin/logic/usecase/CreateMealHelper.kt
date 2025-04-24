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


