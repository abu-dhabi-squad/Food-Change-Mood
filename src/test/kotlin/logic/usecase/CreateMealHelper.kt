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


fun getNonKetoMeal(
    id: Int = 2,
    name: String = "Spaghetti",
    calories: Float = 600f,
    totalFat: Float = 10f,
    protein: Float = 15f,
    carbohydrates: Float = 80f,
    sugar: Float = 15f,
    saturated: Float = 5f
): Food {
    return Food(
        id = id,
        name = name,
        minutes = 40,
        submittedDate = LocalDate.now(),
        tags = listOf("italian"),
        nutrition = Nutrition(
            sugar = sugar,
            totalFat = totalFat,
            protein = protein,
            carbohydrates = carbohydrates,
            saturated = saturated,
            calories = calories,
            sodium = 0.0F,
        ),
        steps = listOf("Boil pasta", "Add sauce"),
        description = "Tasty pasta meal",
        ingredients = listOf("Pasta", "Tomato", "Cheese")
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



